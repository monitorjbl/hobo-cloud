package com.thundermoose.hobo.monitor;

import com.google.common.collect.Iterables;
import com.thundermoose.hobo.docker.DockerApi;
import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.Node;
import com.thundermoose.hobo.model.Port;
import com.thundermoose.hobo.persistence.ContainerRepository;
import com.thundermoose.hobo.persistence.NodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class NodeMonitor implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(NodeMonitor.class);

  @Value("${docker.default.memory}")
  long defaultMemory;
  @Value("${docker.default.cpu}")
  int defaultCpu;

  @Autowired
  NodeRepository nodeRepo;
  @Autowired
  ContainerRepository containerRepo;
  @Autowired
  DockerApi api;

  @Override
  public void run() {
    try {
      pollNodes();
      cleanupNodes();
    } catch (Exception e) {
      log.error("Job threw exception", e);
    }
  }

  void pollNodes() {
    log.info("Polling all nodes");
    for (Node node : nodeRepo.findAll()) {
      Set<Container> updated = new HashSet<>();
      for (Container c : api.getRunningContainers(node)) {
        Container match = Iterables.find(node.getContainers(), input -> c.getDockerId().equals(input.getDockerId()), c);
        if (match.getId() != null) {
          for (Port p : match.getPorts()) {
            if (!match.getPorts().contains(p)) {
              match.getPorts().add(p);
            }
          }
        } else {
          //set defaults on nontracked nodes
          //TODO: probably need a better solution here. default containers have no limits
          if (match.getMemory() == 0) {
            match.setMemory(defaultMemory);
          }
          if (match.getCpu() == 0) {
            match.setCpu(defaultCpu);
          }
          match.setExternal(true);
        }
        match.setNode(node);
        updated.add(match);
      }
      node.setContainers(updated);
      nodeRepo.save(node);
    }
  }

  void cleanupNodes() {
    for (Container c : containerRepo.findByExternal(false)) {
      long diff = milliSinceDate(c.getCreated());
      if (diff > c.getExpiry()) {
        log.info("Container [" + c.getDockerId() + "] on Node [" + c.getNode().getHostname() + "] is overdue for deletion by " + diff + "ms");
        containerRepo.delete(c);
      }
    }
  }

  long milliSinceDate(Date date) {
    Duration d = Duration.between(Instant.ofEpochMilli(date.getTime()), Instant.now());
    return d.getNano() / 1000000;
  }
}
