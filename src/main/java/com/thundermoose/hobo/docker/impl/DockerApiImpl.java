package com.thundermoose.hobo.docker.impl;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.ContainerConfig;
import com.kpelykh.docker.client.model.ContainerCreateResponse;
import com.kpelykh.docker.client.model.ContainerInspectResponse;
import com.kpelykh.docker.client.model.HostConfig;
import com.kpelykh.docker.client.model.Image;
import com.kpelykh.docker.client.model.Ports;
import com.sun.jersey.api.client.ClientResponse;
import com.thundermoose.hobo.docker.DockerApi;
import com.thundermoose.hobo.exceptions.NotFoundException;
import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.Node;
import com.thundermoose.hobo.model.Port;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class DockerApiImpl implements DockerApi {
  Logger log = LoggerFactory.getLogger(DockerApiImpl.class);

  @Override
  public Container startContainer(Node node, Container container) {
    DockerClient client = client(node);
    try {
      Optional<Image> si = Iterables.tryFind(client.getImages(), input -> {
        for (String s : input.getRepoTags()) {
          if (s.equals(container.getRepository() + ":" + container.getTag())) {
            return true;
          }
        }
        return false;
      });
      if (container.getBuild() != null) {
        //TODO: Implement builder
        File dir = Files.createTempDir();
        File f = new File(dir.getAbsolutePath() + "/Dockerfile");
        try (FileOutputStream fos = new FileOutputStream(f)) {
          IOUtils.write(container.getBuild(), fos);
          ClientResponse response = client.build(dir, container.getRepository());

          //have to wait for inputstream to read out
          log.info(IOUtils.toString(response.getEntityInputStream()));
        } catch (IOException e) {
          throw new RuntimeException(e);
        } finally {
          FileUtils.deleteDirectory(dir);
        }
      } else if (!si.isPresent() && container.getBuild() == null) {
        throw new NotFoundException("Container [" + container.getRepository() + ":" + container.getTag() + "] not found, and no build script provided");
      }

      ContainerConfig config = new ContainerConfig();
      config.setImage(container.getRepository() + ":" + container.getTag());
      config.setCmd(container.getCommand());
      ContainerCreateResponse response = client.createContainer(config);
      container.setId(response.getId());

      HostConfig hc = new HostConfig();
      hc.setPublishAllPorts(true);
      client.startContainer(container.getId(), hc);

      ContainerInspectResponse c = client.inspectContainer(container.getId());
      Map<String, Ports.Port> ports = c.getNetworkSettings().ports.getAllPorts();
      for (String key : ports.keySet()) {
        Ports.Port p = ports.get(key);
        container.getPorts().add(new Port(p.getScheme(), p.getPort(), p.getHostPort()));
      }

      return container;
    } catch (DockerException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void stopContainer(Node node, Container container) {
    try {
      client(node).stopContainer(container.getId());
    } catch (DockerException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Container> getRunningContainers(Node node) {
    DockerClient client = client(node);
    List<Container> containers = new ArrayList<>();
    for (com.kpelykh.docker.client.model.Container c : client.listContainers(true)) {
      containers.add(new Container(c.getId(), c.getNames()[0].split(":")[0], c.getNames()[0].split(":")[1]));
    }
    return containers;
  }

  DockerClient client(Node node) {
    DockerClient dockerClient = null;
    try {
      dockerClient = new DockerClient("http://" + node.getHostname() + ":" + node.getPort());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return dockerClient;
  }
}
