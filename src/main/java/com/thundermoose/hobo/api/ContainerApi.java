package com.thundermoose.hobo.api;

import com.thundermoose.hobo.model.Container;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Controller
public interface ContainerApi {

  @RequestMapping(value = "/container/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Container getContainer(@PathVariable String id);

  @RequestMapping(value = "/container/all", method = RequestMethod.GET)
  @ResponseBody
  public Container getAllContainers();

  @RequestMapping(value = "/container}", method = RequestMethod.PUT)
  @ResponseBody
  public Container putNode(@RequestBody Container container);

  @RequestMapping(value = "/container/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public void deleteNode(@PathVariable String id);
}
