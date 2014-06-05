package com.thundermoose.hobo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
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
  @ManyToOne(cascade = CascadeType.ALL)
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Port port = (Port) o;

    if (containerPort != null ? !containerPort.equals(port.containerPort) : port.containerPort != null) return false;
    if (hostPort != null ? !hostPort.equals(port.hostPort) : port.hostPort != null) return false;
    if (scheme != null ? !scheme.equals(port.scheme) : port.scheme != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = scheme != null ? scheme.hashCode() : 0;
    result = 31 * result + (containerPort != null ? containerPort.hashCode() : 0);
    result = 31 * result + (hostPort != null ? hostPort.hashCode() : 0);
    return result;
  }
}
