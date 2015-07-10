[![License](https://img.shields.io/badge/license-BSD--3-blue.svg?style=flat)](https://github.com/emcrisostomo/pdfform/blob/master/LICENSE)

pdfform
=======

A command-line tool to manipulate PDF documents with forms.

Features
--------

`pdfform` main features are:

  * Listing fields in PDF forms.
  * Dumping the value of fields in PDF forms.

Getting pdfform
---------------

The latest `pdfform` release can be obtained [here][latest].

[latest]: https://github.com/emcrisostomo/pdfform/releases/latest

Requirements
------------

The minimum Java version required to build and use this library is Java 7.

Installation
------------

`pdfform` is a Java program packaged as a JAR file with dependencies and requires no installation. 

Documentation
-------------

Documentation is not yet available.

Usage
-----

The syntax of `pdfform` is the following:

    pdfform command (arguments)*

Available commands are:

  * `dump`
  * `help`
  * `list`

### dump

The `dump` command is used to dump the contents of a PDF form:

    pdfform dump (option)* (file)+

where:

  * `option` are optional, command-specific options.
  * `file` is the list of PDF files to process.
  
The available options are the following:

  * `-f, --field`: specifies the name of the field to dump.  If no fields are specified, all the fields are dumped.
  * `-H, --header`: dumps a header line with the name of the fields.
  * `-h, --help`: prints the help message.
  * `-v, --verbose`: prints verbose output.

### help

The `help` command is used to dump the contents of a PDF form:

    pdfform help (command)?

where:

  * `command`: if specified, the help message of the specified command is shown.
  
### list

The `list` command is used to dump the contents of a PDF form:

    pdfform list (option)* (file)+

where:

  * `option` are optional, command-specific options.
  * `file` is the list of PDF files to process.
  
The available options are the following:

  * `-h, --help`: prints the help message.
  * `-s, --separator`: uses the specified separator.
  * `-v, --verbose`: prints verbose output.

Bug Reports
-----------

Bug reports can be sent directly to the authors.

-----

Copyright (c) 2015 Enrico Maria Crisostomo

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

  * Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.

  * Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

  * Neither the name of pdfform nor the names of its contributors may be used to
    endorse or promote products derived from this software without specific
    prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.