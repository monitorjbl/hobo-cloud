package com.thundermoose.hobo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Thundermoose on 6/3/2014.
 */
@Entity
public class Port {
  @Id
  @GeneratedValue
  private String id;
  private String scheme;
  private String containerPort;
  private String hostPort;

  public Port() {
  }

  public Port(String scheme, String containerPort, String hostPort) {
    this.scheme = scheme;
    this.containerPort = containerPort;
    this.hostPort = hostPort;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public String getContainerPort() {
    return containerPort;
  }

  public void setContainerPort(String containerPort) {
    this.containerPort = containerPort;
  }

  public String getHostPort() {
    return hostPort;
  }

  public void setHostPort(String hostPort) {
    this.hostPort = hostPort;
  }

  @Override
  public String toString() {
    return "Port{" +
            "id='" + id + '\'' +
            ", scheme='" + scheme + '\'' +
            ", containerPort='" + containerPort + '\'' +
            ", hostPort='" + hostPort + '\'' +
            '}';
  }
}
