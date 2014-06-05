package com.thundermoose.hobo.api;

import com.thundermoose.hobo.model.Node;
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
public interface NodeApi {

  @RequestMapping(value = "/node/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Node getNode(@PathVariable String id);

  @RequestMapping(value = "/node/all", method = RequestMethod.GET)
  @ResponseBody
  public Set<Node> getAllNodes();

  @RequestMapping(value = "/node", method = RequestMethod.PUT)
  @ResponseBody
  @Transactional
  public Node putNode(@RequestBody Node node);

  @RequestMapping(value = "/node/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  @Transactional
  public void deleteNode(@PathVariable String id);
}
