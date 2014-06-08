package com.thundermoose.hobo.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Entity
@NamedEntityGraphs(@NamedEntityGraph(
        name = "Node.full",
        attributeNodes = @NamedAttributeNode(value="containers", subgraph = "ports"),
        subgraphs = {
                @NamedSubgraph(
                        name = "ports",
                        attributeNodes = {@NamedAttributeNode("ports")}
                )
        }
))
public class Node {
  @Id
  @GeneratedValue
  private String id;
  @Column(unique = true)
  @NotEmpty
  private String hostname;
  private Date created;
  private Integer port;
  private Long maxMemory;
  private Integer maxCpu;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "node", orphanRemoval = true)
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

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public Long getMaxMemory() {
    return maxMemory;
  }

  public void setMaxMemory(Long maxMemory) {
    this.maxMemory = maxMemory;
  }

  public Integer getMaxCpu() {
    return maxCpu;
  }

  public void setMaxCpu(Integer maxCpu) {
    this.maxCpu = maxCpu;
  }

  public Set<Container> getContainers() {
    return containers;
  }

  public void setContainers(Set<Container> containers) {
    this.containers = containers;
  }
}
