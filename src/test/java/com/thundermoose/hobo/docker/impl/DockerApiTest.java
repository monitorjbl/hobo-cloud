package com.thundermoose.hobo.docker.impl;

import com.google.common.io.Resources;
import com.thundermoose.hobo.model.Container;
import com.thundermoose.hobo.model.Node;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by Thundermoose on 6/2/2014.
 */
public class DockerApiTest {
  DockerApiImpl sut = new DockerApiImpl();

  @Test
  public void test() throws InterruptedException, IOException {
    Node n = new Node();
    n.setHostname("192.168.1.75");
    n.setPort(3333);

    Container c = new Container();
    c.setRepository("chupa");
    c.setTag("latest");
    c.setCommand(new String[]{"/usr/sbin/sshd", "-D"});
    c.setBuild(IOUtils.toString(Resources.getResource("images/ubuntu/Dockerfile")));

    c = sut.startContainer(n, c);
    System.out.println(c);

//    Thread.sleep(5000);
//    sut.stopContainer(n, c);
  }
}
