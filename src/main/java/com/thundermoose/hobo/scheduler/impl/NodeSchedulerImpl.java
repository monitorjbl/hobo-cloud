package com.thundermoose.hobo.scheduler.impl;

import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.Node;
import com.thundermoose.hobo.persistence.NodeRepository;
import com.thundermoose.hobo.scheduler.NodeScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class NodeSchedulerImpl implements NodeScheduler {
  @Autowired
  NodeRepository repo;

  @Override
  public Node leastLoadedNode() {
    Node best = null;
    double bestWeight = -1;

    for (Node n : repo.findAll()) {
      double w = weight(n);
      if (w < bestWeight || bestWeight < 0) {
        best = n;
        bestWeight = w;
      }
    }

    return best;
  }

  double weight(Node n) {
    double weight = 0;
    for (Container c : n.getContainers()) {
      weight += (c.getMemory() / n.getMaxMemory()) * 0.5 + (c.getCpu() / n.getMaxCpu()) * 0.5;
    }
    return weight;
  }
}
