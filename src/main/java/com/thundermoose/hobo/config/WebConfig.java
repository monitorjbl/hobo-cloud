package com.thundermoose.hobo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Thundermoose on 6/2/2014.
 */
@Configuration
@ComponentScan(basePackages = {"com.thundermoose.hobo"})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

  @Value("${docker.trace}")
  boolean dockerTrace;

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
    stringConverter.setWriteAcceptCharset(false);
    converters.add(new ByteArrayHttpMessageConverter());
    converters.add(stringConverter);
    converters.add(new ResourceHttpMessageConverter());
    converters.add(new SourceHttpMessageConverter());
    converters.add(new AllEncompassingFormHttpMessageConverter());
    converters.add(jackson());
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer config) {
    config.favorParameter(true)
            .useJaf(false)
            .ignoreAcceptHeader(true)
            .mediaType("html", MediaType.TEXT_HTML)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .defaultContentType(MediaType.APPLICATION_JSON);
  }

  @Bean
  public MappingJackson2HttpMessageConverter jackson() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(objectMapper());
    return converter;
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper o = new ObjectMapper();
    o.enable(SerializationFeature.INDENT_OUTPUT);
    o.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return o;
  }

  @PostConstruct
  public void setJavaLogging() {
    if (dockerTrace) {
      Logger.getLogger("com.sun.jersey.api.client.filter.LoggingFilter").setLevel(Level.FINEST);
    } else {
      Logger.getLogger("com.sun.jersey.api.client.filter.LoggingFilter").setLevel(Level.OFF);
    }
  }
}
