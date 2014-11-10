package com.xiantrimble.dropwizard.jgroups;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;










import org.jgroups.JChannel;
import org.jgroups.conf.ProtocolConfiguration;
import org.jgroups.protocols.BARRIER;
import org.jgroups.protocols.FD_ALL;
import org.jgroups.protocols.FD_SOCK;
import org.jgroups.protocols.FRAG2;
import org.jgroups.protocols.MERGE3;
import org.jgroups.protocols.MFC;
import org.jgroups.protocols.PING;
import org.jgroups.protocols.UDP;
import org.jgroups.protocols.UFC;
import org.jgroups.protocols.UNICAST2;
import org.jgroups.protocols.VERIFY_SUSPECT;
import org.jgroups.protocols.pbcast.GMS;
import org.jgroups.protocols.pbcast.NAKACK;
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.stack.Configurator;
import org.jgroups.stack.ProtocolStack;










import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiantrimble.dropwizard.jgroups.config.JchannelConfiguration;










import io.dropwizard.Configuration;

public class JGroupsConfiguration extends Configuration {
  @JsonProperty
  protected String clusterName;

  @JsonProperty
  @NotNull
  protected JchannelConfiguration jchannel;

  public JChannel createChannel() throws Exception {

    JChannel ch = new JChannel(false);
    ProtocolStack stack = new ProtocolStack();
    ch.setProtocolStack(stack);;
    
    List<ProtocolConfiguration> configs = jchannel.getStack().stream()
      .map(protocol->{
        // eclipse is hating on Collectors.toMap here.
        Map<String, String> configMap = new LinkedHashMap<String, String>();
        for( Map.Entry<String, Object> entry : protocol.getAdditionalProperties().entrySet()) {
          configMap.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return new ProtocolConfiguration(protocol.getProtocol(), configMap);
      }).collect(Collectors.toList());
    
    stack.setup(configs);
    return ch;
  }
}
