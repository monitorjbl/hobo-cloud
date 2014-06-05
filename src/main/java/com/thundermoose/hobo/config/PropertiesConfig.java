package com.thundermoose.hobo.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Configuration
public class PropertiesConfig {
  public static final String CONFIG_OPTION = "conf";

  @Bean
  public static PropertyPlaceholderConfigurer properties() {
    PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
    props.setNullValue("@null");
    List<Resource> files = new ArrayList<>();
    files.add(new ClassPathResource("defaults.conf"));
    if (System.getProperty(CONFIG_OPTION) != null) {
      files.add(new FileSystemResource(System.getProperty(CONFIG_OPTION)));
    }
    props.setLocations(files.toArray(new Resource[files.size()]));
    return props;
  }

}
