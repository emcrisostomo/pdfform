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

/**
 * @author Enrico M. Crisostomo
 */
public class HelpCommand implements Command {
    public HelpCommand(String[] args) {
    }

    @Override
    public void run() throws PDFFormException {
        printUsage();
    }

    @Override
    public boolean isVerbose() {
        return true;
    }

    @Override
    public void printUsage() {
        System.out.printf("%s %s%n", PDFForm.getProgramName(), PDFForm.getProgramVersion());
        System.out.println("");
        System.out.println("Usage:");
        System.out.printf("%s command (arguments)*%n", PDFForm.getProgramName());
        System.out.println("");
        System.out.println("Commands:");
        System.out.println("  list\tList the fields in a PDF form.");
        System.out.println("  dump\tDump the contents of a PDF form.");
        System.out.println("");
        System.out.println("Report bugs to <enrico.m.crisostomo@gmail.com>.");
    }
}
