## Pulsar MQTT Provider

Java server application that listens for MQTT traffic

* [MQTT Documentation reference guide](http://mqtt.org/documentation/) Go here to check about MQTT
* [Pulsar Documentation reference guide](https://pulsar.apache.org/) Go here to check about apache pulsar

 
## Embedding in other projects

To embed pulsar-mqtt-provider in another maven project is sufficient to include a repository and declare the dependency:

```
<repositories>
  <repository>
    <id>quinovas</id>
    <url>https://dependency.quinovas.com</url>
    <releases>
      <enabled>true</enabled>
    </releases>
    <snapshots>
      <enabled>false</enabled>
    </snapshots>
  </repository>
</repositories>
```

Include dependency in your project:

```
<dependency>
      <groupId>com.echostreams</groupId>
      <artifactId>pulsar-mqtt-provider</artifactId>
      <version>1.0-SNAPSHOT</version>
</dependency>
```

## Build from sources

After a git clone of this repository, cd into the cloned sources and make sure you have maven installed to build this:

`mvn install`.

In project_source_folder/target directory will be produced the selfcontained file for the broker with all dependencies and a running script.

## Download Runnable Build

Download the self distribution tar from [QuiNovas](https://quinovas.com/artifact/download/quinovas/generic/pulsar-mqtt-provider-1.0-SNAPSHOT-bin.tar.gz) ,
then untar and start the broker listening on `1883` port and enjoy!

Ubuntu

```
tar xvf pulsar-mqtt-provider-1.0-SNAPSHOT-bin.tar.gz
cd pulsar-mqtt-provider-1.0-SNAPSHOT
chmod 777 startApp.sh
./startApp.sh
```

Or if you are on Windows shell, Unzip the build

```
 cd pulsar-mqtt-provider-1.0-SNAPSHOT
 .\startApp.bat
```

---