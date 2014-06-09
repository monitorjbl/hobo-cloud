package com.thundermoose.hobo.api.impl;

import com.thundermoose.hobo.api.ContainerApi;
import com.thundermoose.hobo.api.NodeApi;
import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.exceptions.NotFoundException;
import com.thundermoose.hobo.model.Node;
import com.thundermoose.hobo.persistence.NodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class NodeApiImpl implements NodeApi {
  private static final Logger log = LoggerFactory.getLogger(ContainerApiImpl.class);

  @Value("${node.default.maxMemory}")
  long defaultMemory;
  @Value("${node.default.maxCpu}")
  int defaultCpu;

  @Autowired
  NodeRepository repo;
  @Autowired
  ContainerApi containerApi;

  @Override
  public Node getNode(@PathVariable("id") String id) {
    Node n = repo.findById(id);
    if (n == null) {
      throw new NotFoundException("Node [" + id + "] not found");
    }
    return n;
  }

  @Override
  public Set<Node> getAllNodes() {
    return repo.getAllNodes();
  }

  @Override
  public Node putNode(@RequestBody Node node) {
    node.setMaxCpu(node.getMaxCpu() == null ? defaultCpu : node.getMaxCpu());
    node.setMaxMemory(node.getMaxMemory() == null ? defaultMemory : node.getMaxMemory());
    return repo.save(node);
  }

  @Override
  public void deleteNode(@PathVariable("id") String id) {
    Node n = repo.findById(id);
    if (n == null) {
      throw new NotFoundException("Node [" + id + "] not found");
    }

    // delete all containers on this node
    log.debug("Deleting node [" + id + "]");
    for (Container c : n.getContainers()) {
      try {
        containerApi.deleteContainer(c.getId());
      } catch (NotFoundException e) {
        // polling system may delete this before we get to it
      }
    }

    repo.delete(n);
  }
}
