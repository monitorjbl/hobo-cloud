package com.thundermoose.hobo.docker;

import com.thundermoose.hobo.model.Container;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public interface DockerApi {
  public Container startContainer(Container container);
  public void stopContainer(Container container);
}
