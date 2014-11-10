package com.xiantrimble.dropwizard.jgroups.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jgroups.JChannel;
import org.jgroups.View;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

@Path("/channel")
public class ChannelResource {

  JChannel channel;
  
  public ChannelResource(JChannel channel) {
    this.channel = channel;
  }

  @GET
  @Produces("application/json")
  @JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
  @JsonView(JGroupsResource.class)
  public JChannel getState() {
    return channel;
  }
}
