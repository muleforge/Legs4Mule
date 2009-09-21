Overview:
=========
  The purpose of the legstar proxy quickstart sample is to demonstrate access
  to a POJO from a mainframe program. This is possible with the help of an ESB
  component known as a proxy.
  In this case, the mainframe uses HTTP transport.
  When you run this test, the mainframe is actually simulated by a Java HTTP
  client that behaves exactly like the mainframe would.

Prerequisites:
=============
  1. The sample uses the junit ant task. This is not part of the standard ant
     delivery. You need to add junit.jar and ant-junit.jar in ANT_HOME/lib.
  
  Refer to http://code.google.com/p/legstar-mule/wiki/InstallInstructions
  for details on the installation process.


Running this quickstart:
========================
  1. In a command terminal window in this folder, type 'ant generate'.
  2. Check the output for any build issues.
  3. From the same window type 'ant runtest'.
  4. Check the JUnit output files for any execution issues.
