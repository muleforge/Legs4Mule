/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.providers.legstar.gen.util;

import java.io.File;

import junit.framework.TestCase;

/**
 * Test cases for CixsMuleGenUtil.
 */
public class CixsMuleGenUtilTest extends TestCase {
    
    /**
     * Check that getFile works with an absolute file name.
     * @throws Exception if test fails
     */
    public final void testNoDirAbsoluteFileName() throws Exception {
        File file = new File("test.file");
        file.createNewFile();
        file.deleteOnExit();
        File file2 = CixsMuleGenUtil.getFile(null, file.getAbsolutePath());
        assertTrue(null != file2);
    }

    /**
     * Check that getFile works with a relative file name.
     * @throws Exception if test fails
     */
   public final void testDirRelativeFileName() throws Exception {
        File dir = new File("test.dir");
        dir.mkdir();
        dir.deleteOnExit();
        File file2 = CixsMuleGenUtil.getFile("test.dir", "test.file");
        assertTrue(file2.getAbsolutePath().contains("test.dir"));
        assertTrue(file2.getAbsolutePath().contains("test.file"));
    }

   /**
    * Check that class normalization works.
    * @throws Exception if test fails
    */
  public final void testClassNormalization() throws Exception {
       assertEquals(null, CixsMuleGenUtil.classNormalize(null));
       assertEquals("A", CixsMuleGenUtil.classNormalize("a"));
       assertEquals("Abc", CixsMuleGenUtil.classNormalize("abc"));
   }

  /**
   * Check that location from pakage name works.
   * @throws Exception if test fails
   */
 public final void testRelativeLocation() throws Exception {
      assertEquals("", CixsMuleGenUtil.relativeLocation(null));
      assertEquals("/abc/", CixsMuleGenUtil.relativeLocation("abc"));
      assertEquals("/abc/def/", CixsMuleGenUtil.relativeLocation("abc.def"));
  }

}
