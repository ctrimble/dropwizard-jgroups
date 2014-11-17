# Dropwizard JGroups Integration

This project provides a Dropwizard Bundle that integrates JGroups configuration into the standard config file.  An [example](./example) project is also provided to demonstrate using the bundle.

[![Build Status](https://travis-ci.org/ctrimble/dropwizard-jgroups.svg)](https://travis-ci.org/ctrimble/dropwizard-jgroups)

## Usage

### Maven

To use SNAPSHOTs of this project, you will need to include the sonatype repository in your POM.

```
<repositories>
    <repository>
        <snapshots>
        <enabled>true</enabled>
        </snapshots>
        <id>sonatype-nexus-snapshots</id>
        <name>Sonatype Nexus Snapshots</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
```

You will also need to include the project in your dependencies.

```
<dependency>
  <groupId>com.xiantrimble.dropwizard</groupId>
  <artifactId>dropwizard-jgroups</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

### Java

Add a ChannelConfiguration to your applications configuration.

```
import com.xiantrimble.dropwizard.jgroups.config.ChannelConfiguration;

...

  @JsonProperty
  @NotNull
  protected ChannelConfiguration channel;

  public ChannelConfiguration getChannel() {
    return channel;
  }
```

and wire up the bundle in your application's initialize method.

```
import com.xiantrimble.dropwizard.jgroups.JGroupsBundle;

...

@Override
public void initialize(Bootstrap<MyConfiguration> bootstrap) {
  bootstrap.addBundle(
    JGroupsBundle.<MyConfiguration>builder()
    .withConfig(c->c.getChannel())
    .build());
}
```

### Config

Add the channel configuration to the dropwizard configuration file.  The `protocol` field is equivelent to element names in the standard JGroups config file.  All other fields are treated as properties for the protocol.

```
channel:
  stack:
    - protocol: TCP
      bind_port: 7801
      sock_conn_timeout: 500
    - protocol: MPING
      receive_on_all_interfaces: true
      mcast_port: 7500
    - protocol: MERGE3
    - protocol: FD_SOCK
    - protocol: FD
    - protocol: VERIFY_SUSPECT
    - protocol: pbcast.NAKACK2
    - protocol: UNICAST3
    - protocol: pbcast.STABLE
    - protocol: pbcast.GMS
    - protocol: MFC
    - protocol: FRAG2
    - protocol: pbcast.STATE_TRANSFER
```

## Building

This project builds with Java8 and Maven 3.  Simply clone the repo and run

```
mvn clean install
```

from the root directory.

## Contributing

This project accepts PRs, so feel free to fork the project and send contributions back.

### Formatting

This project contains formatters to help keep the code base consistent.  The formatter will update Java source files and add headers to other files.  When running the formatter, I suggest the following procedure:

1. Make sure any outstanding stages are staged.  This will prevent the formatter from destroying your code.
2. Run `mvn format`, this will format the source and add any missing license headers.
3. If the changes look good and the project still compiles, add the formatting changes to your staged code.

If things go wrong, you can run `git checkout -- .` to drop the formatting changes. 
