/*
 * (C) Copyright 2016 Stephan Fuhrmann
 *
 * Permission to use, copy, and distribute this software and its documentation
 * for any purpose and without fee is hereby granted, provided that the above
 * copyright notice appear in all copies and that both that copyright notice and
 * this permission notice appear in supporting documentation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gr.spinellis.ckjm;

import gr.spinellis.ckjm.ant.PrintXmlResults;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Command line options.
 *
 * @author Stephan Fuhrmann
 */
public class CliOptions {

    @Option(name = "-p", aliases = {"--only-public"}, usage = "True if the reports should only include public classes.")
    private boolean onlyPublic = false;

    @Option(name = "-j", aliases = {"--include-jdk-classes"}, usage = "True if the measurements should include calls to the Java JDK into account.")
    private boolean includeJdkClasses = false;

    @Option(name = "-i", aliases = {"--use-stdin"}, usage = "Read class file names from standard input instead of adding them in the command line arguments.")
    private boolean stdIn = false;

    @Option(name = "-t", aliases = {"--type"}, usage = "Output format to use.")
    private OutputType outputType = OutputType.PLAIN;

    @Option(name = "-h", aliases = {"--help"}, usage = "Show this help message.", help = true)
    private boolean help;

    @Argument(metaVar = "CLASS-OR-JAR-SPACE-CLASS")
    private List<String> files;

    enum OutputType {
        XML(ps -> new PrintXmlResults(ps)),
        CSV(ps -> new PrintCSVResults(ps)),
        PLAIN(ps -> new PrintPlainResults(ps));

        private final Function<PrintStream, CkjmOutputHandler> func;

        private OutputType(Function<PrintStream, CkjmOutputHandler> func) {
            this.func = func;
        }

        public CkjmOutputHandler getInstance(PrintStream s) {
            return func.apply(s);
        }
    }

    public boolean isHelp() {
        return help;
    }

    public boolean isIncludeJdkClasses() {
        return includeJdkClasses;
    }

    public boolean isOnlyPublic() {
        return onlyPublic;
    }

    public boolean isStdIn() {
        return stdIn;
    }

    public List<String> getFiles() {
        return files != null ? files : Collections.emptyList();
    }

    OutputType getOutputType() {
        return outputType;
    }

    public static CliOptions create(String[] args) {
        Objects.requireNonNull(args);
        try {
            CliOptions result = new CliOptions();
            CmdLineParser parser = new CmdLineParser(result);
            parser.parseArgument(args);

            if (result.isHelp()) {
                parser.printUsage(System.err);
                return null;
            }

            if (result.getFiles().isEmpty() && !result.isStdIn()) {
                parser.printUsage(System.err);
                return null;
            }

            return result;
        } catch (CmdLineException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
}
