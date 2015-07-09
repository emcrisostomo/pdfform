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

package eu.greysystems;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Enrico M. Crisostomo
 * @version 1.0.0
 * @since 1.0.0
 */
final class PDFForm {
    private static final String OPT_S = "s";
    private static final String OPT_F = "f";
    private static final String OPT_V = "v";
    private static final String OPT_UH = "H";
    private static final String OPT_H = "h";
    private static final String PROGRAM_VERSION = "1.0.0";
    private static final String PROGRAM_NAME = "pdfform";
    private static String separator = ",";
    private static String[] fields;
    private static String[] files;
    private static boolean printHeader;
    private static boolean verbose;

    public static void main(String args[]) {
        Logger.getLogger("org.apache.pdfbox").setLevel(Level.OFF);

        try {
            parseArgs(args);
            checkArgs();

            if (printHeader) System.out.println(StringUtils.join(fields, separator));

            for (String file : files) {
                process(file, fields, separator);
            }
        } catch (ParseException e) {
            System.err.printf("Syntax error: %s%n", e.getMessage());
            printException(e);
            System.exit(2);
        } catch (CryptographyException e) {
            System.err.printf("Cryptographic error: %s%n", e.getMessage());
            printException(e);
            System.exit(3);
        } catch (IOException e) {
            System.err.printf("I/O error: %s%n", e.getMessage());
            printException(e);
            System.exit(5);
        }
    }

    private static void printException(Exception e) {
        if (verbose) e.printStackTrace(System.err);
    }

    private static void checkArgs() {
        if (files.length == 0) {
            System.err.println("Missing argument.");
            System.exit(1);
        }

        if (fields == null) {
            System.err.println("Missing fields.");
            System.exit(4);
        }
    }

    private static void parseArgs(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(OPT_S, "separator", true, "Use the specified separator.");
        options.addOption(OPT_F, "field", true, "Extract the specified field.");
        options.addOption(OPT_UH, "header", false, "Print the column headers.");
        options.addOption(OPT_H, "help", false, "Print the help message.");
        options.addOption(OPT_V, "verbose", false, "Print verbose output.");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        final Option[] cmdOptions = cmd.getOptions();

        for (Option opt : cmdOptions) {
            switch (opt.getOpt()) {
                case OPT_F:
                    fields = cmd.getOptionValues(OPT_F);
                    break;

                case OPT_H:
                    printUsage(options);
                    System.exit(0);

                case OPT_UH:
                    printHeader = true;
                    break;

                case OPT_S:
                    separator = cmd.getOptionValue(OPT_S);
                    break;

                case OPT_V:
                    verbose = true;
                    break;

                default:
                    throw new ParseException("Unknown option: " + opt.getOpt());
            }
        }

        files = cmd.getArgs();
    }

    private static void printUsage(Options options) {
        System.out.printf("%s %s%n", PROGRAM_NAME, PROGRAM_VERSION);
        System.out.println("");
        System.out.println("Usage:");
        System.out.printf("%s (option)* (-f field)+ path*%n", PROGRAM_NAME);
        System.out.println("");
        System.out.println("Options:");
        System.out.println("");

        for (Option opt : options.getOptions()) {
            if (opt.getLongOpt() == null) {
                System.out.printf("  -%s\t%s%n", opt.getOpt(), opt.getDescription());
            } else {
                System.out.printf("  -%s, --%s\t%s%n", opt.getOpt(), opt.getLongOpt(), opt.getDescription());
            }
        }

        System.out.println("");
        System.out.println("Report bugs to <enrico.m.crisostomo@gmail.com>.");
    }

    private static void process(String file, String[] fields, String separator)
            throws IOException, CryptographyException {
        if (separator == null) throw new IllegalArgumentException("Separator cannot be null.");

        try (PDDocument document = PDDocument.load(file)) {
            if (document.isEncrypted()) {
                document.decrypt("");
                document.setAllSecurityToBeRemoved(true);
            }

            final PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
            final PDAcroForm acrobatForm = documentCatalog.getAcroForm();

            List<String> values = new ArrayList<>();

            for (String field : fields) {
                final PDField acrobatFormField = acrobatForm.getField(field);

                String value;

                if (acrobatFormField == null) {
                    System.err.printf("Cannot find %s.%n", field);
                    value = null;
                } else {
                    value = acrobatFormField.getValue();
                }

                values.add((value == null ? "" : StringEscapeUtils.escapeCsv(value)));
            }

            System.out.println(StringUtils.join(values, separator));
        }
    }
}
