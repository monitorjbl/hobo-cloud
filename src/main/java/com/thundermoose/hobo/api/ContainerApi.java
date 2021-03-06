package com.thundermoose.hobo.api;

import com.thundermoose.hobo.model.Container;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Controller
public interface ContainerApi {

  @RequestMapping(value = "/container/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Container getContainer(@PathVariable("id") String id);

  @RequestMapping(value = "/container/all", method = RequestMethod.GET)
  @ResponseBody
  public Set<Container> getAllContainers();

  @RequestMapping(value = "/container", method = RequestMethod.PUT)
  @ResponseBody
  @Transactional
  public Container putContainer(@RequestBody Container container);

  @RequestMapping(value = "/container/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  @Transactional
  public void deleteContainer(@PathVariable("id") String id);
}
