package com.thundermoose.hobo.persistence;

import com.thundermoose.hobo.model.Node;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public interface NodeRepository extends JpaRepository<Node, String>, JpaSpecificationExecutor {
  @EntityGraph(value = "Node.full", type = EntityGraph.EntityGraphType.LOAD)
  public Node findById(String id);

  @EntityGraph(value = "Node.full", type = EntityGraph.EntityGraphType.LOAD)
  @Query("from Node")
  public Set<Node> getAllNodes();
}
