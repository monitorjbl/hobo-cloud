package com.thundermoose.hobo.scheduler.impl;

import com.thundermoose.hobo.model.Node;
import com.thundermoose.hobo.scheduler.NodeScheduler;
import org.springframework.stereotype.Component;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class NodeSchedulerImpl implements NodeScheduler {
  @Override
  public Node leastLoadedNode() {
    return null;
  }
}
