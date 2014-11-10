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

import io.dropwizard.lifecycle.Managed;

import org.jgroups.JChannel;

public class JChannelManager implements Managed {
  private JChannel channel;

  public JChannelManager(JChannel channel) {
    this.channel = channel;
  }

  @Override
  public void start() throws Exception {
    channel.connect("test");
  }

  @Override
  public void stop() throws Exception {
    channel.close();
  }
}
