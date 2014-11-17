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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jgroups.JChannel;
import org.jgroups.View;
import org.jgroups.conf.ProtocolConfiguration;
import org.jgroups.stack.ProtocolStack;

import com.fasterxml.jackson.databind.MapperFeature;
import com.xiantrimble.dropwizard.jgroups.config.JchannelConfiguration;
import com.xiantrimble.dropwizard.jgroups.mixin.ChannelMixin;
import com.xiantrimble.dropwizard.jgroups.mixin.ViewMixin;
import com.xiantrimble.dropwizard.jgroups.resource.ChannelResource;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class JGroupsBundle<C extends Configuration> implements ConfiguredBundle<C> {
  
  public static class Builder<C extends Configuration> {
    Function<C, JchannelConfiguration> configAccessor;
    
    public Builder<C> withConfig( Function<C, JchannelConfiguration> configAccessor) {
      this.configAccessor = configAccessor;
      return this;
    }
    
    public JGroupsBundle<C> build() {
      return new JGroupsBundle<C>(configAccessor);
    }
  }
  
  public static <C extends Configuration> Builder<C> builder() {
    return new Builder<C>();
  }

  private Function<C, JchannelConfiguration> configAccessor;

  public JGroupsBundle(Function<C, JchannelConfiguration> configAccessor) {
    this.configAccessor = configAccessor;
  }

  @Override
  public void initialize(Bootstrap<?> arg0) {
  }

  @Override
  public void run(C config, Environment env) throws Exception {
    env.getObjectMapper().addMixInAnnotations(JChannel.class, ChannelMixin.class);
    env.getObjectMapper().addMixInAnnotations(View.class, ViewMixin.class);
    env.getObjectMapper().configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
    JChannel channel = createChannel(configAccessor.apply(config));

    env.lifecycle().manage(new JChannelManager(channel));

    env.jersey().register(new ChannelResource(channel));
  }
  
  public static JChannel createChannel(JchannelConfiguration config) throws Exception {
    JChannel ch = new JChannel(false);
    ProtocolStack stack = new ProtocolStack();
    ch.setProtocolStack(stack);

    List<ProtocolConfiguration> configs = config.getStack().stream().map(protocol -> {
      // eclipse is hating on Collectors.toMap here.
        Map<String, String> configMap = new LinkedHashMap<String, String>();
        for (Map.Entry<String, Object> entry : protocol.getAdditionalProperties().entrySet()) {
          configMap.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return new ProtocolConfiguration(protocol.getProtocol(), configMap);
      }).collect(Collectors.toList());

    stack.setup(configs);
    return ch;
  }

}
