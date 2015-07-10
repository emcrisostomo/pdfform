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

package eu.greysystems.commands;

import eu.greysystems.PDFForm;
import eu.greysystems.PDFFormException;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico M. Crisostomo
 * @version 1.0.0
 * @since 1.0.0
 */
public class ListCommand implements Command {
    private static final String OPT_S = "s";
    private static final String OPT_V = "v";
    private static final String OPT_H = "h";
    private static Options options;
    private static String separator = "\n";
    private static boolean verbose;
    private static boolean help;
    private static String[] files;
    private final String name;

    public ListCommand(String name, String[] args) throws ParseException {
        this.name = name;

        options = new Options();
        options.addOption(OPT_S, "separator", true, "Use the specified separator.");
        options.addOption(OPT_H, "help", false, "Print the help message.");
        options.addOption(OPT_V, "verbose", false, "Print verbose output.");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        final Option[] cmdOptions = cmd.getOptions();

        for (Option opt : cmdOptions) {
            switch (opt.getOpt()) {
                case OPT_H:
                    help = true;
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

    private static void checkArgs() throws PDFFormException {
        if (help) return;

        if (files.length == 0) {
            throw new PDFFormException("Missing argument.");
        }
    }

    @Override
    public void run() throws PDFFormException {
        checkArgs();

        if (help) {
            printUsage();
        } else {
            list();
        }
    }

    private void list() throws PDFFormException {
        for (String file : files) {
            try {
                listFieldsOf(file);
            } catch (IOException | CryptographyException e) {
                throw new PDFFormException(e);
            }
        }
    }

    private void listFieldsOf(String file) throws IOException, CryptographyException {
        try (PDDocument document = PDDocument.load(file)) {
            if (document.isEncrypted()) {
                document.decrypt("");
                document.setAllSecurityToBeRemoved(true);
            }

            final PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
            final PDAcroForm acrobatForm = documentCatalog.getAcroForm();

            if (acrobatForm == null) return;

            List<?> fields = acrobatForm.getFields();
            List<String> fieldNames = new ArrayList<>();

            for (Object o : fields) {
                PDField field = (PDField) o;
                fieldNames.add(field.getFullyQualifiedName());
            }

            System.out.println(StringUtils.join(fieldNames, separator));
        }
    }

    @Override
    public void printUsage() {
        System.out.printf("%s %s%n", PDFForm.getProgramName(), PDFForm.getProgramVersion());
        System.out.println("");
        System.out.println("Usage:");
        System.out.printf("  %s %s (option)* (-f field)+ path*%n", PDFForm.getProgramName(), getName());
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isVerbose() {
        return verbose;
    }
}
