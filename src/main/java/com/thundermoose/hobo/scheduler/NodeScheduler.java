package com.thundermoose.hobo.scheduler;

import com.thundermoose.hobo.model.Node;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public interface NodeScheduler {
  public Node leastLoadedNode();
}
