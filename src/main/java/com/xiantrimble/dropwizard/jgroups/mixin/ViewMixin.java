package com.xiantrimble.dropwizard.jgroups.mixin;

import java.util.List;

import org.jgroups.Address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public interface ViewMixin {
  @JsonProperty
  @JsonView(Views.Resource.class)
  public String getViewId();

  @JsonProperty
  @JsonSerialize(contentUsing = ToStringSerializer.class)
  @JsonView(Views.Resource.class)
  public List<Address> getMembers();
}