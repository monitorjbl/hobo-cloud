package com.thundermoose.hobo.docker;

import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.Node;

import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public interface DockerApi {
  public Container startContainer(Node node, Container container);
  public void stopContainer(Node node, Container container);
  public Set<Container> getRunningContainers(Node node);
}
