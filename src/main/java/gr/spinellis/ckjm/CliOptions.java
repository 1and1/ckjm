/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.spinellis.ckjm;

import gr.spinellis.ckjm.ant.PrintXmlResults;
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Command line options.
 * @author stephan
 */
public class CliOptions {
    
    @Option(name = "-p", aliases = {"--only-public"}, usage = "True if the reports should only include public classes.")
    private boolean onlyPublic = false;
    
    @Option(name = "-j", aliases = {"--include-jdk-classes"}, usage = "True if the measurements should include calls to the Java JDK into account.")
    private boolean  includeJdkClasses = false;
    
    @Option(name = "-i", aliases = {"--use-stdin"}, usage = "Read class source from standard input.")
    private boolean  stdIn = false;
    
    @Option(name = "-t", aliases = {"--type"}, usage = "Output format to use.")
    private OutputType outputType = OutputType.Plain;
    
    @Option(name = "-h", aliases = {"--help"}, usage = "Show this help message.", help = true)
    private boolean help;
    
    @Argument(metaVar = "CLASS-OR-JAR")
    private List<String> files;
    
    enum OutputType {
        Xml(ps -> new PrintXmlResults(ps)),
        Plain(ps -> new PrintPlainResults(ps));
        
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
        return files;
    }

    public OutputType getOutputType() {
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
            
            if (result.getFiles() == null && !result.isStdIn()) {
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
