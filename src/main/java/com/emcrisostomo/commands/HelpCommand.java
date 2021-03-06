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

package com.emcrisostomo.commands;

import com.emcrisostomo.PDFForm;
import com.emcrisostomo.PDFFormException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Enrico M. Crisostomo
 */
public class HelpCommand extends BaseCommand {
    private final String[] args;

    public HelpCommand(String name, String[] args) {
        super(name);
        this.args = args;
    }

    @Override
    public void run() throws PDFFormException {
        if (args.length == 0) {
            printUsage();
            return;
        } else if (args.length > 1) {
            System.err.println("Invalid argument: " + StringUtils.join(args, " "));
            printUsage();
            return;
        }

        final Command helpCommand = Commands.createFromArguments(args);
        helpCommand.printUsage();
    }

    @Override
    public boolean isVerbose() {
        return true;
    }

    @Override
    public void doPrintUsage() {
        System.out.println("Usage:");
        System.out.printf("  %s %s command (arguments)*%n", PDFForm.getProgramName(), getName());
        System.out.println("");
        System.out.println("Commands:");
        System.out.println("  dump\tDump the contents of a PDF form.");
        System.out.println("  help\tPrints the usage of the specified command.");
        System.out.println("  list\tList the fields in a PDF form.");
        System.out.println("");
        System.out.println("To read the help of a specific command:");
        System.out.printf("  %s help command%n", PDFForm.getProgramName());
    }
}
