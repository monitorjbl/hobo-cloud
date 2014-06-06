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
import com.thundermoose.hobo.model.exceptions.DockerApiException;
import com.thundermoose.hobo.model.exceptions.NotFoundException;
import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.Node;
import com.thundermoose.hobo.model.Port;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class DockerApiImpl implements DockerApi {
  private static final Logger log = LoggerFactory.getLogger(DockerApiImpl.class);

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
        File dir = Files.createTempDir();
        File f = new File(dir.getAbsolutePath() + "/Dockerfile");
        try (FileOutputStream fos = new FileOutputStream(f)) {
          IOUtils.write(container.getBuild(), fos);
          ClientResponse response = client.build(dir, container.getRepository());

          //have to wait for inputstream to read out
          log.debug(IOUtils.toString(response.getEntityInputStream()));
        } catch (IOException e) {
          throw new DockerApiException(e);
        } finally {
          FileUtils.deleteDirectory(dir);
        }
      } else if (!si.isPresent() && container.getBuild() == null) {
        throw new NotFoundException("Container [" + container.getRepository() + ":" + container.getTag() + "] not found, and no build script provided");
      }

      ContainerConfig config = new ContainerConfig();
      config.setImage(container.getRepository() + ":" + container.getTag());
      config.setCmd(container.getCommand());
      config.setMemoryLimit(container.getMemory());
      config.setCpuShares(container.getCpu());
      ContainerCreateResponse response = client.createContainer(config);
      container.setDockerId(response.getId());

      HostConfig hc = new HostConfig();
      hc.setPublishAllPorts(true);
      client.startContainer(container.getDockerId(), hc);

      //get port mappings
      ContainerInspectResponse inspect = client.inspectContainer(container.getDockerId());
      getPorts(inspect.getNetworkSettings().ports.getAllPorts(), container);

      container.setNode(node);
      return container;
    } catch (DockerException | IOException e) {
      throw new DockerApiException(e);
    }
  }

  @Override
  public void stopContainer(Node node, Container container) {
    try {
      DockerClient client = client(node);
      client.stopContainer(container.getDockerId());
      client.removeContainer(container.getDockerId());
      try {
        client.inspectContainer(container.getDockerId());
        throw new DockerApiException("Could not delete container [" + container.getDockerId() + "] from server");
      } catch (com.kpelykh.docker.client.NotFoundException e) {
        //do nothing, this exception means the container was deleted
      }
    } catch (DockerException e) {
      throw new DockerApiException(e);
    }
  }

  @Override
  public Set<Container> getRunningContainers(Node node) {
    DockerClient client = client(node);
    Set<Container> containers = new HashSet<>();
    try {
      for (com.kpelykh.docker.client.model.Container c : client.listContainers(false)) {
        ContainerInspectResponse inspect = client.inspectContainer(c.getId());
        String[] split = c.getImage().split(":");

        Container cnt = new Container(c.getId(), split[0], split[1]);
        cnt.setCpu(inspect.getConfig().getCpuShares());
        cnt.setMemory(inspect.getConfig().getMemoryLimit());


        getPorts(inspect.getNetworkSettings().ports.getAllPorts(), cnt);
        containers.add(cnt);
      }
      return containers;
    } catch (DockerException e) {
      throw new DockerApiException(e);
    }
  }

  void getPorts(Map<String, Ports.Port> ports, Container container) throws DockerException {
    for (String key : ports.keySet()) {
      Ports.Port p = ports.get(key);
      container.getPorts().add(new Port(p.getScheme(), p.getPort(), p.getHostPort(), container));
    }
  }

  DockerClient client(Node node) {
    DockerClient dockerClient = null;
    try {
      dockerClient = new DockerClient("http://" + node.getHostname() + ":" + node.getPort());
    } catch (DockerException e) {
      throw new DockerApiException(e);
    }
    return dockerClient;
  }
}
