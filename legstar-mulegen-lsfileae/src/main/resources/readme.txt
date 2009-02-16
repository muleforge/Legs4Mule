Overview:
=========
  The purpose of the legstar adapter quickstart sample is to demonstrate access
  to a mainframe program from an Mule component known as an adapter. This will
  also check that Mule and LegStar are properly configured.
  
  The sample demonstrates 2 different setups:
  - The bridge setup uses the Legs4Mule transport which uses HTTP as its underlying
    protocol.
  - The standalone setting uses regular LegStar transport and can use HTTP, Sockets
    or WebSphere MQ.

Prerequisites:
=============
  1. This sample requires access to a mainframe on which LegStar has been
     installed.
  2. The sample uses the junit ant task. This is not part of the standard ant
     delivery. You need to add junit.jar and ant-junit.jar in ANT_HOME/lib.
  
  Refer to http://www.mulesource.org/display/LEGSTAR/InstallationInstructions
  for details on the installation process.

Settings:
=========
  Check parameters in legstar-invoker-config.xml and ant/build-mule2cixs.xml.
  Make sure connectivity parameters match your mainframe settings.

Running this quickstart:
========================
  1. In a command terminal window in this folder, type 'ant generate'.
  2. Check the output for any build issues.
  3. From the same window type 'ant runtest'.
  4. Check the JUnit output files for any execution issues.
