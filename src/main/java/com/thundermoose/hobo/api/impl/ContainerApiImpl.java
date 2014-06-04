package com.thundermoose.hobo.api.impl;

import com.thundermoose.hobo.api.ContainerApi;
import com.thundermoose.hobo.docker.DockerApi;
import com.thundermoose.hobo.exceptions.NotFoundException;
import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.Node;
import com.thundermoose.hobo.repositories.ContainerRepository;
import com.thundermoose.hobo.scheduler.NodeScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class ContainerApiImpl implements ContainerApi {
  @Autowired
  NodeScheduler scheduler;
  @Autowired
  ContainerRepository repo;
  @Autowired
  DockerApi docker;


  @Override
  public Container getContainer(@PathVariable String id) {
    return repo.findById(id);
  }

  @Override
  public Set<Container> getAllContainers() {
    Set<Container> containers = new HashSet<>();
    for (Container c : repo.findAll()) {
      containers.add(c);
    }
    return containers;
  }

  @Override
  public Container putContainer(@RequestBody Container container) {
    Node node = scheduler.leastLoadedNode();
    Container c = docker.startContainer(node, container);
    repo.save(c);
    return c;
  }

  @Override
  public void deleteContainer(@PathVariable String id) {
    Container c = repo.findById(id);
    if (c == null) {
      throw new NotFoundException("Container [" + id + "] not found");
    }
    repo.delete(c);
  }
}
