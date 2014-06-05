package com.thundermoose.hobo.persistence;

import com.thundermoose.hobo.model.Container;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public interface ContainerRepository extends CrudRepository<Container, String> {
  public Container findById(String id);
  public Set<Container> findByExternal(boolean external);
}
