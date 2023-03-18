#!/usr/bin/env bash

scala -classpath ./target/scala-2.11/basic-kafka-example-assembly-1.0.jar:. com.example.basic.DemoBasicProducer "$@"
