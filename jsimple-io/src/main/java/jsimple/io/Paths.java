/*
 * Copyright (c) 2012-2014, Microsoft Mobile
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package jsimple.io;

import jsimple.util.BasicException;
import org.jetbrains.annotations.Nullable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Bret Johnson
 * @since 1/19/13 4:31 PM
 */
public abstract class Paths {
    public static volatile @Nullable Paths instance = null;

    public static Paths getInstance() {
        if (instance == null)
            throw new BasicException("Paths instance isn't set; did you forget to call JSimpleIO.init()?");
        return instance;
    }

    public static void setInstance(Paths instance) {
        Paths.instance = instance;
    }

    /**
     * @return the directory where the application should store its private data.
     */
    public abstract Directory getApplicationDataDirectory();

    /**
     * Return the file system Directory object corresponding to the string serialized representation.  If the directory
     * doesn't exist, it's implementation dependent whether this method throws a PathNotFoundException or a
     * PathNotFoundException is thrown when the returned directory is used--different implementations do different
     * things there.
     * <p/>
     * Not all implementations of Directory support serializing it as a string.  An exception is thrown if it's not
     * supported.
     *
     * @param directoryPathString file system path string
     * @return Directory for the specified path
     */
    public abstract Directory getFileSystemDirectory(String directoryPathString);

    /**
     * Get the test output directory for the specified test name.  This directory is normally under
     * <projectBase>/target/test-output/<testName>.  Any existing directory will have its contents deleted by this
     * method, before returning.  The idea here is that test output sticks around after the tests are run, so it can be
     * inspected to maybe do some debugging.  But it's under "target" so it shouldn't show up as new files for source
     * control, just intermediate output generated by the build.
     *
     * @param testName test name, used as part of directory name
     * @return directory to hold test output
     */
    public Directory getTestOutputDirectory(String testName) {
        Directory projectDirectory = Paths.getInstance().getApplicationDataDirectory();
        Directory testOutputBase = projectDirectory.createDirectory("target").createDirectory("test-output");

        Directory testOutputDirectory = testOutputBase.createDirectory(testName);
        testOutputDirectory.deleteContents();
        return testOutputDirectory;
    }
}
