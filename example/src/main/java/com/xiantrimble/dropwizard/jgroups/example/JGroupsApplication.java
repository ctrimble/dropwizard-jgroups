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
package com.xiantrimble.dropwizard.jgroups.example;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.xiantrimble.dropwizard.jgroups.*;

public class JGroupsApplication extends Application<JGroupsConfiguration> {
  public static void main(String[] args) throws Exception {
    new JGroupsApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<JGroupsConfiguration> bootstrap) {
    bootstrap.addBundle(
        JGroupsBundle.<JGroupsConfiguration>builder()
        .withConfig(c->c.getChannel()).build());
  }

  @Override
  public void run(JGroupsConfiguration config, Environment env) throws Exception {
  }
}
