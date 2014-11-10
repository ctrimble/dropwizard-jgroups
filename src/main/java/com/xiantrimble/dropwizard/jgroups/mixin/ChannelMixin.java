package com.xiantrimble.dropwizard.jgroups.mixin;

import org.jgroups.View;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public interface ChannelMixin {
  @JsonProperty
  @JsonView(Views.Resource.class)
  public String getState();

  @JsonProperty
  @JsonView(Views.Resource.class)
  public View getView();

  @JsonProperty
  @JsonView(Views.Resource.class)
  public String getName();

  @JsonProperty
  @JsonView(Views.Resource.class)
  public String getClusterName();

  @JsonProperty
  @JsonView(Views.Resource.class)
  public boolean getDiscardOwnMessages();
}