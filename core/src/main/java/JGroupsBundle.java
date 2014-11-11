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

import org.jgroups.JChannel;
import org.jgroups.View;

import com.fasterxml.jackson.databind.MapperFeature;
import com.xiantrimble.dropwizard.jgroups.mixin.ChannelMixin;
import com.xiantrimble.dropwizard.jgroups.mixin.ViewMixin;
import com.xiantrimble.dropwizard.jgroups.resource.ChannelResource;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class JGroupsBundle implements ConfiguredBundle<JGroupsConfiguration> {

  @Override
  public void initialize(Bootstrap<?> arg0) {
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

}
