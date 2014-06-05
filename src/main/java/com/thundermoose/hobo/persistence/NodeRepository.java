package com.thundermoose.hobo.persistence;

import com.thundermoose.hobo.model.Node;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public interface NodeRepository extends CrudRepository<Node, String>{
  public Node findById(String id);
}
