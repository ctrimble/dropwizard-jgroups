/**
 * Copyright (C) 2014 Christian Trimble (xiantrimble@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiantrimble.dropwizard.jgroups.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jgroups.JChannel;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.xiantrimble.dropwizard.jgroups.mixin.Views;

@Path("/channel")
public class ChannelResource {

  JChannel channel;

  public ChannelResource(JChannel channel) {
    this.channel = channel;
  }

  @GET
  @Produces("application/json")
  @JacksonFeatures(serializationEnable = { SerializationFeature.INDENT_OUTPUT })
  @JsonView(Views.Resource.class)
  public JChannel getState() {
    return channel;
  }
}
