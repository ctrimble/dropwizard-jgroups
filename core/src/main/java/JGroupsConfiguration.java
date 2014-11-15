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

import io.dropwizard.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.jgroups.JChannel;
import org.jgroups.conf.ProtocolConfiguration;
import org.jgroups.stack.ProtocolStack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiantrimble.dropwizard.jgroups.config.JchannelConfiguration;

public class JGroupsConfiguration extends Configuration {
  @JsonProperty
  protected String clusterName;

  @JsonProperty
  @NotNull
  protected JchannelConfiguration jchannel;

  public JChannel createChannel() throws Exception {

    JChannel ch = new JChannel(false);
    ProtocolStack stack = new ProtocolStack();
    ch.setProtocolStack(stack);

    List<ProtocolConfiguration> configs = jchannel.getStack().stream().map(protocol -> {
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
