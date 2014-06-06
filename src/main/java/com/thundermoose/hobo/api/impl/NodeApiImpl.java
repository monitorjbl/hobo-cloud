package com.thundermoose.hobo.api.impl;

import com.thundermoose.hobo.api.NodeApi;
import com.thundermoose.hobo.model.exceptions.NotFoundException;
import com.thundermoose.hobo.model.Node;
import com.thundermoose.hobo.persistence.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Component
public class NodeApiImpl implements NodeApi {
  @Autowired
  NodeRepository repo;

  @Override
  public Node getNode(@PathVariable("id") String id) {
    Node n = repo.findById(id);
    if (n == null) {
      throw new NotFoundException("Node [" + id + "] not found");
    }
    return n;
  }

  @Override
  public Set<Node> getAllNodes() {
    return repo.getAllNodes();
  }

  @Override
  public Node putNode(@RequestBody Node node) {
    return repo.save(node);
  }

  @Override
  public void deleteNode(@PathVariable("id") String id) {
    Node n = repo.findById(id);
    if (n == null) {
      throw new NotFoundException("Node [" + id + "] not found");
    }
    repo.delete(n);
  }
}
