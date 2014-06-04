package com.thundermoose.hobo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Entity
public class Node {
  @Id
  @GeneratedValue
  private String id;
  private String hostname;
  private int port;
  private int maxMemory;
  private double maxCpu;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "node")
  private Set<Container> containers = new HashSet<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getMaxMemory() {
    return maxMemory;
  }

  public void setMaxMemory(int maxMemory) {
    this.maxMemory = maxMemory;
  }

  public double getMaxCpu() {
    return maxCpu;
  }

  public void setMaxCpu(double maxCpu) {
    this.maxCpu = maxCpu;
  }

  public Set<Container> getContainers() {
    return containers;
  }

  public void setContainers(Set<Container> containers) {
    this.containers = containers;
  }

}
