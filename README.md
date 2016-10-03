# PokeGo Lure AEM Client

[![Build Status](https://travis-ci.org/mickleroy/aem-pokego-lure-client.svg?branch=master)](https://travis-ci.org/mickleroy/aem-pokego-lure-client)

An AEM dashboard to manage lure modules in various locations.

## Building/deploying from source

This project uses Maven for building. Common commands:

From the root directory, run ``mvn -PautoInstallPackage clean install`` to build the bundle and content package and install to a CQ instance.

From the bundle directory, run ``mvn -PautoInstallBundle clean install`` to build *just* the bundle and install to a CQ instance.

## Using the application

[Video screencast](https://www.youtube.com/watch?v=QuUoPM76kUM)

The dashboard can be accessed at http://localhost:4502/etc/pokegolure/index.html and a shortcut is available in the Tools > General > Pokemon Go Lures. Note: A Pokemon Go account is required to use this application.

![dashboard](dashboard.png)

