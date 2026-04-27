#!/bin/bash

mvn compile
mvn -q exec:java -Dexec.mainClass="se.yrgo.client.Main"
