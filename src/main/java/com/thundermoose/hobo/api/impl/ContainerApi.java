package com.thundermoose.hobo.api.impl;

import com.thundermoose.hobo.docker.DockerApi;
import com.thundermoose.hobo.scheduler.NodeScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class ContainerApi {
  @Autowired
  NodeScheduler scheduler;
  @Autowired
  DockerApi docker;

}
