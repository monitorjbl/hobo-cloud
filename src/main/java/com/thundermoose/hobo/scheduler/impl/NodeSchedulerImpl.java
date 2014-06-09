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
      if (w < 1 && w < bestWeight || bestWeight < 0) {
        best = n;
        bestWeight = w;
      }
    }

    return best;
  }

  /**
   * Returns a combined weight of cpu and mem utilization. Both are equally weighted, but
   * if either is > 100%, the returned value will be 1
   *
   * @param n
   * @return
   */
  double weight(Node n) {
    double weight = 0;
    for (Container c : n.getContainers()) {
      double mem = (c.getMemory() / n.getMaxMemory()) * 0.5;
      weight += mem > 0.5 ? 1 : mem;

      double cpu = (c.getCpu() / n.getMaxCpu()) * 0.5;
      weight += cpu > 0.5 ? 1 : cpu;
    }
    return weight;
  }
}
