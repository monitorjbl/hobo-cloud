package com.thundermoose.hobo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Entity
@JsonIgnoreProperties({"node"})
public class Container {
  @Id
  @GeneratedValue
  private String id;
  @Column(unique = true)
  private String dockerId;
  private Date created;
  private String repository;
  private String tag;
  private String[] command;
  private String build;
  @Min(524288)
  private Long memory;
  @Min(1)
  private Integer cpu;
  private Long expiry;
  private Boolean external;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "container")
  private Set<Port> ports = new HashSet<>();
  @ManyToOne
  private Node node;

  public Container() {
  }

  public Container(String dockerId, String repository, String tag) {
    this.dockerId = dockerId;
    this.repository = repository;
    this.tag = tag;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDockerId() {
    return dockerId;
  }

  public void setDockerId(String dockerId) {
    this.dockerId = dockerId;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public String getRepository() {
    return repository;
  }

  public void setRepository(String repository) {
    this.repository = repository;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String[] getCommand() {
    return command;
  }

  public void setCommand(String[] command) {
    this.command = command;
  }

  public String getBuild() {
    return build;
  }

  public void setBuild(String build) {
    this.build = build;
  }

  public Long getMemory() {
    return memory;
  }

  public void setMemory(Long memory) {
    this.memory = memory;
  }

  public Integer getCpu() {
    return cpu;
  }

  public void setCpu(Integer cpu) {
    this.cpu = cpu;
  }

  public Long getExpiry() {
    return expiry;
  }

  public void setExpiry(Long expiry) {
    this.expiry = expiry;
  }

  public Boolean getExternal() {
    return external;
  }

  public void setExternal(Boolean external) {
    this.external = external;
  }

  public Set<Port> getPorts() {
    return ports;
  }

  public void setPorts(Set<Port> ports) {
    this.ports = ports;
  }

  public Node getNode() {
    return node;
  }

  public void setNode(Node node) {
    this.node = node;
  }
}
