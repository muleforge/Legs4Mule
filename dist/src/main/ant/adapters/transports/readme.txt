Overview:
=========
  The purpose of this transport quickstart sample is to test connectivity
  to mainframe using this particular legstar transport.
  This refers to a legstar core transport used for standalone configurations.

Prerequisites:
=============
  The LegStar z/OS modules must be installed in the target CICS region.

  Refer to http://www.legsem.com/legstar/legstar-distribution-zos/index.html
  for instructions.
  
Settings:
=========
  Check parameters in legstar-invoker-config.xml, they must match your
  mainframe settings.

Running this quickstart:
========================

  1. In a command terminal window in this folder, type 'ant'.
  2. Check the messages produced.

Final deployment:
=================
  
  Copy legstar-invoker-config.xml to $MULE_HOME/conf
