package com.thundermoose.hobo.persistence;

import com.thundermoose.hobo.model.Container;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public interface ContainerRepository extends CrudRepository<Container, String> {
  @EntityGraph(value = "Container.full", type = EntityGraph.EntityGraphType.LOAD)
  public Container findById(String id);

  @EntityGraph(value = "Container.full", type = EntityGraph.EntityGraphType.LOAD)
  public Set<Container> findByExternal(Boolean external);

  @EntityGraph(value = "Container.full", type = EntityGraph.EntityGraphType.LOAD)
  @Query("from Container")
  public Set<Container> getAllContainers();
}
