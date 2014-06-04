package com.thundermoose.hobo.monitor;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.thundermoose.hobo.docker.DockerApi;
import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.Node;
import com.thundermoose.hobo.repositories.NodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class NodeMonitor implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(NodeMonitor.class);

  @Autowired
  NodeRepository repo;
  @Autowired
  DockerApi api;

  @Override
  public void run() {
    try {
      pollNodes();
    } catch (Exception e) {
      log.error("Job threw exception", e);
    }
  }

  void pollNodes() {
    log.info("Polling all nodes");
    for (Node node : repo.findAll()) {
      Set<Container> updated = new HashSet<>();
      for (Container c : api.getRunningContainers(node)) {
        Container match = Iterables.find(node.getContainers(), input -> c.getDockerId().equals(input.getDockerId()), null);
        if (match == null) {
          match = c;
          match.setNode(node);
        } else {
          c.setId(match.getId());
          match = c;
        }
        updated.add(match);
      }
      node.setContainers(updated);
      repo.save(node);
    }
  }
}
