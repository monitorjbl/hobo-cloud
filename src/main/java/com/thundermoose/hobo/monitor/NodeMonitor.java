package com.thundermoose.hobo.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class NodeMonitor implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(NodeMonitor.class);

  @Override
  public void run() {
    pollNodes();
  }

  void pollNodes(){
    log.info("Polling all nodes");
  }
}
