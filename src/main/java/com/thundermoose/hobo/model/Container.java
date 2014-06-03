package com.thundermoose.hobo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Entity
public class Container {
  @Id
  @GeneratedValue
  private String id;
  private String repository;
  private String tag;
  private String[] command;
  private String build;
  @OneToMany(fetch = FetchType.EAGER)
  private List<Port> ports = new ArrayList<>();
  @Transient
  private Node node;

  public Container() {
  }

  public Container(String id, String repository, String tag) {
    this.id = id;
    this.repository = repository;
    this.tag = tag;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public List<Port> getPorts() {
    return ports;
  }

  public void setPorts(List<Port> ports) {
    this.ports = ports;
  }

  public Node getNode() {
    return node;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  @Override
  public String toString() {
    return "Container{" +
            "id='" + id + '\'' +
            ", repository='" + repository + '\'' +
            ", tag='" + tag + '\'' +
            ", command=" + Arrays.toString(command) +
            ", ports=" + ports +
            ", node=" + node +
            ", build='" + build + '\'' +
            '}';
  }
}
