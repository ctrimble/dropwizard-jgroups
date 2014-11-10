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
package com.xiantrimble.dropwizard.jgroups;

import java.util.List;

import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.View;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xiantrimble.dropwizard.jgroups.resource.ChannelResource;
import com.xiantrimble.dropwizard.jgroups.resource.JGroupsResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 *
 */
public class JGroupsApplication extends Application<JGroupsConfiguration> {
  public static void main(String[] args) throws Exception {
    new JGroupsApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<JGroupsConfiguration> bootstrap) {
    bootstrap.getObjectMapper().addMixInAnnotations(JChannel.class, ChannelMixin.class);
    bootstrap.getObjectMapper().addMixInAnnotations(Channel.class, ChannelMixin.class);
    bootstrap.getObjectMapper().addMixInAnnotations(View.class, ViewMixin.class);
  }

  @Override
  public void run(JGroupsConfiguration config, Environment env) throws Exception {
    env.getObjectMapper().addMixInAnnotations(JChannel.class, ChannelMixin.class);
    env.getObjectMapper().addMixInAnnotations(View.class, ViewMixin.class);
    env.getObjectMapper().configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
    JChannel channel = config.createChannel();

    env.lifecycle().manage(new JChannelManager(channel));

    env.jersey().register(new ChannelResource(channel));
  }

  public static interface ChannelMixin {
    @JsonProperty
    @JsonView(JGroupsResource.class)
    public String getState();

    @JsonProperty
    @JsonView(JGroupsResource.class)
    public View getView();

    @JsonProperty
    @JsonView(JGroupsResource.class)
    public String getName();

    @JsonProperty
    @JsonView(JGroupsResource.class)
    public String getClusterName();

    @JsonProperty
    @JsonView(JGroupsResource.class)
    public boolean getDiscardOwnMessages();
  }

  public static interface ViewMixin {
    @JsonProperty
    @JsonView(JGroupsResource.class)
    public String getViewId();

    @JsonProperty
    @JsonSerialize(contentUsing = ToStringSerializer.class)
    @JsonView(JGroupsResource.class)
    public List<Address> getMembers();
  }
}
