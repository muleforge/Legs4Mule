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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Various utility methods which are mostly useful for code generation
 * using velocity templates.
 */
public final class CixsMuleGenUtil {

    /** Generated code has reference to generation date following this format.*/
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    
    /** Generated code reference this generator name.*/
    public static final String GENERATOR_NAME = "Legs4Mule generator";

    
    /**
     * Defeats instantiation. Utility class.
     */
    private CixsMuleGenUtil() {
     }
    
    /**
     * Check that a directory is valid.
     * 
     * @param dir
     *            the directory name to check
     * @param create
     *            true if directory should be created when not found
     */
    public static void checkDirectory(final String dir, final boolean create) {

        if (dir == null || dir.length() == 0) {
            throw (new IllegalArgumentException("No directory name was specified"));
        }

        File fdir = new File(dir);

        if (!fdir.exists()) {
            if (!create) {
                throw (new IllegalArgumentException(dir + " does not exist"));
            } else {
                if (!fdir.mkdirs()) {
                    throw (new IllegalArgumentException("Could not create directory "
                            + dir));
                } else {
                    return;
                }
            }
        }
        if (!fdir.isDirectory()) {
            throw (new IllegalArgumentException(dir + " is not a directory"));
        }
        if (!fdir.canWrite()) {
            throw (new IllegalArgumentException("Directory " + dir + " is not writable"));
        }
    }

    /**
     * Retrieve a file.
     * Given a directory name and a filename, this creates a File according to
     * the following rules:
     * <ul>
     *  <li>If the filename is absolute, the directory name is ignored</li>
     *  <li>If the directory is not null, it is assumed to exist</li>
     *  <li>If the directory is not null and the filename is not absolute, then
     *   directory is appended to the filename</li>
     * </ul>
     * @param dir
     *            parent directory
     * @param filename
     *            absolute or relative file name
     * @return a File
     */
    public static File getFile(final String dir, final String filename) {
        File file = new File(filename);
        if (file.isAbsolute()) {
            return file;
        }
        if (dir == null || dir.length() == 0) {
            return new File(filename);
        }
        return new File(dir, filename);
    }

    /**
     * Create a valid Java class name from a given noun.
     * 
     * @param noun
     *            the characters to turn into a java class name
     * @return the Java class name
     */
    public static String classNormalize(final String noun) {
        String className = null;
        if (noun != null && noun.length() > 0) {
            className = noun.substring(0, 1).toUpperCase();
            if (noun.length() > 1) {
                className += noun.substring(1, noun.length());
            }
        }
        return className;
    }

    /**
     * Given a package name, this method returns the relative path location of
     * the java files. A package like seg1.seg2.seg3 becomes /seg1/seg2/seg3/
     * 
     * @param packageName
     *            the package name
     * @return the relative location of java files
     */
    public static String relativeLocation(final String packageName) {
        if (packageName == null || packageName.length() == 0) {
            return "";
        }
        String loc = packageName.replace('.', '/');
        if (loc.charAt(0) != '/') {
            loc = '/' + loc;
        }
        if (loc.charAt(loc.length() - 1) != '/') {
            loc += '/';
        }
        return loc;
    }

    /**
     * Setup Velocity so that it searches for templates in the classpath.
     * @throws Exception if setup fails
     */
    public static void initVelocity() throws Exception {
        Velocity.addProperty("resource.loader", "classpath");
        Velocity.addProperty("classpath.resource.loader.description",
                "Velocity Classpath Resource Loader");
        Velocity
                .addProperty("classpath.resource.loader.class",
                        "org.apache.velocity.runtime.resource.loader."
                        + "ClasspathResourceLoader");
        Velocity.addProperty("classpath.resource.loader.cache", true);
        Velocity.init();
    }

    /**
     * A simple context to use by generation templates.
     * @return a velocity context
     */
    public static VelocityContext getContext() {
        VelocityContext context = new VelocityContext();
        context.put("formattedDate", now());
        context.put("generatorName", GENERATOR_NAME);
        return context;
    }

    /**
     * Formats todays date and time.
     * @return a formatted date
     */
    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

}
