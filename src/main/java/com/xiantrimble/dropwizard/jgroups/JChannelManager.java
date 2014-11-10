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
