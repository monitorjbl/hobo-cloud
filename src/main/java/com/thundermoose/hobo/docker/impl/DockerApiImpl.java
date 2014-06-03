package com.thundermoose.hobo.docker.impl;

import com.thundermoose.hobo.docker.DockerApi;
import com.thundermoose.hobo.model.Container;
import org.springframework.stereotype.Component;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class DockerApiImpl implements DockerApi {
  @Override
  public Container startContainer(Container container) {
    return null;
  }

  @Override
  public void stopContainer(Container container) {

  }
}
