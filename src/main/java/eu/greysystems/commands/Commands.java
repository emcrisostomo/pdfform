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

import eu.greysystems.PDFFormException;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;

/**
 * @author Enrico M. Crisostomo
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Commands {
    public static Command createFromArguments(String[] args) throws PDFFormException {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Arguments cannot be emtpy.");
        }

        final String[] options = Arrays.copyOfRange(args, 1, args.length);
        Command command;

        try {
            final String commandName = args[0];

            switch (commandName) {
                case "dump":
                    command = new DumpCommand(commandName, options);
                    break;

                case "help":
                    command = new HelpCommand(commandName, options);
                    break;

                case "list":
                    command = new ListCommand(commandName, options);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown command: " + commandName);
            }

            return command;
        } catch (ParseException e) {
            throw new PDFFormException(e);
        }
    }
}
