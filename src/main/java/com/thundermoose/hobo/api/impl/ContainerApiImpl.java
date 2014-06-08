package com.thundermoose.hobo.api.impl;

import com.thundermoose.hobo.api.ContainerApi;
import com.thundermoose.hobo.docker.DockerApi;
import com.thundermoose.hobo.model.exceptions.NotFoundException;
import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.Node;
import com.thundermoose.hobo.persistence.ContainerRepository;
import com.thundermoose.hobo.model.ModelValidator;
import com.thundermoose.hobo.scheduler.NodeScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class ContainerApiImpl implements ContainerApi {
  @Value("${docker.default.expiry}")
  long defaultExpiry;
  @Value("${docker.default.memory}")
  long defaultMemory;
  @Value("${docker.default.cpu}")
  int defaultCpu;

  @Autowired
  NodeScheduler scheduler;
  @Autowired
  ContainerRepository repo;
  @Autowired
  DockerApi dockerApi;
  @Autowired
  ModelValidator validator;

  @Override
  public Container getContainer(@PathVariable("id") String id) {
    Container c = repo.findById(id);
    if (c == null) {
      throw new NotFoundException("Container [" + id + "] not found");
    }
    return c;
  }

  @Override
  public Set<Container> getAllContainers() {
    return repo.getAllContainers();
  }

  @Override
  public Container putContainer(@RequestBody Container container) {
    //set defaults (if needed)
    container.setCpu(container.getCpu() == null ? defaultCpu : container.getCpu());
    container.setMemory(container.getMemory() == null ? defaultMemory : container.getMemory());
    container.setExpiry(container.getExpiry() == null ? defaultExpiry : container.getExpiry());
    container.setCreated(new Date());

    validator.validate(container);

    Node node = scheduler.leastLoadedNode();
    if(node == null){
      throw new NotFoundException("No nodes were available to accept this request.");
    }

    Container c = dockerApi.startContainer(node, container);
    repo.save(c);
    return c;
  }

  @Override
  public void deleteContainer(@PathVariable String id) {
    Container c = repo.findOne(id);
    if (c == null) {
      throw new NotFoundException("Container [" + id + "] not found");
    }
    repo.delete(c);
    dockerApi.stopContainer(c.getNode(), c);
  }
}
