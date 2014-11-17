# Dropwizard JGroups Integration

This project provides a Dropwizard Bundle that integrates JGroups configuration into the standard config file.  An [example](./example) project is also provided to demonstrate using the bundle.

[![Build Status](https://travis-ci.org/ctrimble/dropwizard-jgroups.svg)](https://travis-ci.org/ctrimble/dropwizard-jgroups)

## Usage

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

Then add the ChannelConfiguration to your applications configuration.

```
import io.dropwizard.Configuration;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiantrimble.dropwizard.jgroups.config.ChannelConfiguration;

public class MyConfiguration extends Configuration {

  @JsonProperty
  @NotNull
  protected ChannelConfiguration channel;

  public ChannelConfiguration getChannel() {
    return channel;
  }
}
```

and finally, you will need to add the bundle to your application's initialize method.

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
