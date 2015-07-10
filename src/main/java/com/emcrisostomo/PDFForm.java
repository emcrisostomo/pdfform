/*
 * Copyright (c) 2015 Enrico Maria Crisostomo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *   * Neither the name of pdfform nor the names of its contributors may be used to
 *     endorse or promote products derived from this software without specific
 *     prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.emcrisostomo;

import com.emcrisostomo.commands.Command;
import com.emcrisostomo.commands.Commands;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Enrico M. Crisostomo
 * @version 1.0.0
 * @since 1.0.0
 */
public final class PDFForm {
    /**
     * The property names are prefixed by ${groupId}.${artifactId}.
     */
    private static final String PACKAGE_NAME = PDFForm.class.getPackage().getName();
    private static final String PACKAGE_NAME_AS_PATH = PACKAGE_NAME.replaceAll("\\.", "/");
    private static final String ARTIFACT_ID = "pdfform";
    private static final String PROPERTIES_PREFIX = StringUtils.join(new String[]{PACKAGE_NAME, ARTIFACT_ID}, ".");
    private static boolean verbose;
    private static Properties properties;

    public static void main(String args[]) {
        Logger.getLogger("org.apache.pdfbox").setLevel(Level.OFF);

        try {
            if (args.length == 0) {
                System.err.println("Missing command.");
                System.exit(1);
            }

            Command command = Commands.createFromArguments(args);
            verbose = command.isVerbose();
            command.run();
        } catch (Exception e) {
            System.err.printf("Error: %s: %s%n", e.getClass().getName(), e.getMessage());
            printException(e);
            System.exit(5);
        }
    }

    private static void printException(Exception e) {
        if (verbose) e.printStackTrace(System.err);
    }

    private static void loadProperties() {
        if (properties != null) return;

        properties = new Properties();

        try (InputStream resourceAsStream =
                     PDFForm.class.getResourceAsStream(
                             String.format("/%s/version.properties", PACKAGE_NAME_AS_PATH))) {
            if (resourceAsStream == null) throw new IllegalStateException("Cannot find required resource.");
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String getProgramName() {
        loadProperties();
        return properties.getProperty(PROPERTIES_PREFIX + ".name");
    }

    public static String getProgramVersion() {
        loadProperties();
        return properties.getProperty(PROPERTIES_PREFIX + ".version");
    }
}
