#!/bin/sh
# Copyright (c) 2019 Quinovas
#

echo "                                                                          "
echo "  ______      _                 ___  ________ _____ _____                 "
echo "  | ___ \    | |                |  \/  |  _  |_   _|_   _|                "
echo "  | |_/ /   _| |___  __ _ _ __  | .  . | | | | | |   | |                  "
echo "  |  __/ | | | / __|/ _  | '__| | |\/| | | | | | |   | |                  "
echo "  | |  | |_| | \__ \ (_| | |    | |  | \ \/' / | |   | |                  "
echo "  \_|   \__,_|_|___/\__,_|_|    \_|  |_/\_/\_\ \_/   \_/                  "
echo "										                                    "
echo "                                 version: 1.0-SNAPSHOT   			        "
echo "										                                    "

# Set JavaHome if it exists
if [ -f "${JAVA_HOME}/bin/java" ]; then 
   JAVA=${JAVA_HOME}/bin/java
else
   JAVA=java
fi
export JAVA

PULSAR_MQTT_PATH=${PWD}

echo Using JAVA_HOME:       "$JAVA_HOME"
echo Using PULSAR_MQTT_PATH:   "$PULSAR_MQTT_PATH"

## This class has the main method to start the app
MAIN_CLASS=com.echostreams.pulsar.mqtt.broker.Server

JAVA_OPTS_SCRIPT="-XX:+HeapDumpOnOutOfMemoryError -Djava.awt.headless=true"

## Use the Hotspot garbage-first collector.
JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC"

## Have the JVM do less remembered set work during STW, instead
## preferring concurrent GC. Reduces p99.9 latency.
JAVA_OPTS="$JAVA_OPTS -XX:G1RSetUpdatingPauseTimePercent=5"

## Main G1GC tunable: lowering the pause target will lower throughput and vise versa.
## 200ms is the JVM default and lowest viable setting
## 1000ms increases throughput. Keep it smaller than the timeouts.
JAVA_OPTS="$JAVA_OPTS -XX:MaxGCPauseMillis=500"

## Optional G1 Settings

# Save CPU time on large (>= 16GB) heaps by delaying region scanning
# until the heap is 70% full. The default in Hotspot 8u40 is 40%.
#JAVA_OPTS="$JAVA_OPTS -XX:InitiatingHeapOccupancyPercent=70"

# For systems with > 8 cores, the default ParallelGCThreads is 5/8 the number of logical cores.
# Otherwise equal to the number of cores when 8 or less.
# Machines with > 10 cores should try setting these to <= full cores.
#JAVA_OPTS="$JAVA_OPTS -XX:ParallelGCThreads=16"

# By default, ConcGCThreads is 1/4 of ParallelGCThreads.
# Setting both to the same value can reduce STW durations.
#JAVA_OPTS="$JAVA_OPTS -XX:ConcGCThreads=16"

### GC logging options -- uncomment to enable

JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDateStamps"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintHeapAtGC"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintTenuringDistribution"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCApplicationStoppedTime"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintPromotionFailure"
#JAVA_OPTS="$JAVA_OPTS -XX:PrintFLSStatistics=1"
JAVA_OPTS="$JAVA_OPTS -Xloggc:$PULSAR_MQTT_PATH/logs/gc.log"
JAVA_OPTS="$JAVA_OPTS -XX:+UseGCLogFileRotation"
JAVA_OPTS="$JAVA_OPTS -XX:NumberOfGCLogFiles=10"
JAVA_OPTS="$JAVA_OPTS -XX:GCLogFileSize=10M"

$JAVA -server $JAVA_OPTS $JAVA_OPTS_SCRIPT -cp "$PULSAR_MQTT_PATH/lib/*" $MAIN_CLASS
