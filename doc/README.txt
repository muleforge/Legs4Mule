Legs4Mule Distribution content:
------------------------------
This distribution provides a Mule transport for bi-directional mainframe
access based on the LegStar open-source product.

The transport is called mule-transport-legstar-x.jar, where x is the version
number, under the lib/mule sub-directory.

The distribution also contains a generation tool that creates ad-hoc Mule
transformers for mainframe programs. Without such a tool, you would have to
manually map a mainframe program input and output structures to a Mule
component interface.

The generation tool is called mule-transport-legstar-tools-x.jar, where x is
the version number, under the lib/opt sub-directory.

The lib/opt sub-directory contains all the core LegStar libraries and their
dependencies.

Before you can use the Mule Legstar transport, you will need to learn how
to use the LegStar core functionalities. You can learn more about LegStar at
http://www.legsem.com/legstar.

The rest of this document focuses on the steps that are specific to Mule.

Setting up Legs4Mule:
---------------------
You should have Mule installed and the MULE_HOME environment variable should
point to your Mule installation folder.

Unzip the distribution file to a location of your choice.

The layout of the lib folder mimics the Mule installation folders.

You can copy over the various jars to their corresponding Mule locations but
this is not mandatory.

Exposing a Mainframe program to Mule clients:
--------------------------------------------
The ant folder contains a sample build-mule2cixs.xml script that you can
customize to generate a Mule component.

By default, build-mule2cixs.xml generates a component for the sample LSFILEAE
cobol program, part of the standard LegStar delivery.

Type: ant -f build-mule2cixs.xml. This will create a src folder. Under
src/main/resources/conf, Mule configuration samples are generated:

- mule-bridge-config-MuleLsfileae.xml exposes the Mainframe program via 
  the Mule standard BridgeComponent. Connectivity is via the LegStar
  transport for Mule. Connectivity to the host is via Http only for now.
  build-mule2cixs.xml generates Mule Transformers that are referenced in the
  configuration file.

- mule-standalone-config-MuleLsfileae.xml is an alternative to the previous
  configuration file, it exposes the Mainframe program as a Mule component.
  This component internally uses standard LegStar connectivity
  which means it can connect to the host over TCP, Http or Websphere MQ. These
  artifacts will be removed when the LegStar transport for Mule also supports
  TCP and Websphere MQ.
  
Depending on the type of connectivity you have chosen, you will need to follow
instructions from the following URLs in order to complete installation on the
mainframe side:
 - HTTP: http://www.legsem.com/legstar/modules/legstar-chttprt/
 - TCP: http://www.legsem.com/legstar/modules/legstar-csokrt/
 - Websphere MQ: http://www.legsem.com/legstar/modules/legstar-cmqrt/
 
The src/main/resources/ant folder contains generated ant scripts.

The build.xml script deploys the generated component to your Mule installation
(Defined by the MULE_HOME environment variable).

As an alternative to deploying the component to your Mule installation, you
can use the start-x.xml scripts that start Mule directly with one of the
generated configuration files.
  
Consuming Mule services from a Mainframe program:
------------------------------------------------
The ant folder contains a sample build-cixs2mule.xml script that you can
customize to generate Mule transformers and configuration files to provide
mainframe programs with access to a Mule component.

By default, build-cixs2mule.xml generates artefacts for the sample JVMQuery
java bean, part of the standard LegStar delivery.

The build-cixs2mule.xml ant script generates a sample Mule configuration file:
mule-local-config-MuleJvmquery.xml, that demonstrates how a local component
can be consumed by a mainframe program.

The src/main/resources/ant folder contains generated ant scripts.

The build.xml script deploys the generated component to your Mule installation
(Defined by the MULE_HOME environment variable).

Also generated is a Mule startup ant script, start-x.xml, that runs Mule and
have a listener wait for incoming requests from the Mainframe.

Finally, a COBOL template for a CICS program is generated in the
src\main\resources\cobol directory. Provided that you add COBOL instructions
to set and fetch data, this template can become a Mainframe client.

A working sample is provided in samples/cobol.

The transport is not complete without the Mainframe runtime that is part
of the regular LegStar delivery at http://www.legsem.com/legstar/legstar-c2wsrt.

Using build-mule2cixs.xml and build-cixs2mule.xml for your own mainframe programs:
---------------------------------------------------------------------
In order to create your own Mule-LegStar components you will have to
create legStar binding classes first. These classes are generated using the
standard LegStar binding generator as described here:
http://www.legsem.com/legstar/legstar-coxbgen.


