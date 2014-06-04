package com.thundermoose.hobo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Thundermoose on 6/3/2014.
 */
@Entity
@JsonIgnoreProperties({"container"})
public class Port {
  @Id
  @GeneratedValue
  private String id;
  private String scheme;
  private String containerPort;
  private String hostPort;
  @ManyToOne
  private Container container;

  public Port() {
  }

  public Port(String scheme, String containerPort, String hostPort, Container container) {
    this.scheme = scheme;
    this.containerPort = containerPort;
    this.hostPort = hostPort;
    this.container = container;
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

  public Container getContainer() {
    return container;
  }

  public void setContainer(Container container) {
    this.container = container;
  }

}
