package com.thundermoose.hobo.config;

import com.thundermoose.hobo.monitor.NodeMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Configuration
@Import(PropertiesConfig.class)
public class CronConfig {
  @Value("${monitor.frequency}")
  int frequency;
  @Autowired
  NodeMonitor monitor;

  @Bean
  public ScheduledExecutorFactoryBean execFactory() {
    ScheduledExecutorFactoryBean factory = new ScheduledExecutorFactoryBean();
    factory.setScheduledExecutorTasks(new ScheduledExecutorTask[]{
            pollNodes()
    });
    return factory;
  }

  @Bean
  public ScheduledExecutorTask pollNodes() {
    ScheduledExecutorTask task = new ScheduledExecutorTask();
    task.setDelay(1000);
    task.setPeriod(frequency);
    task.setRunnable(monitor);
    return task;
  }
}
