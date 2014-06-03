package com.thundermoose.hobo.repositories;

import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.Node;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public interface ContainerRepository extends CrudRepository<Container, String>{
  public Container findById(String id);
}
