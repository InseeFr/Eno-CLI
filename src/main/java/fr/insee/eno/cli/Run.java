package fr.insee.eno.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Defines the command-line interface to Eno.
 * A basic scenario is to produce an XForms questionnaire from a DDI specification.
 * */
public class Run {

	public static void main(String[] args) {
		Options options = defineOptions();
		
		HelpFormatter formatter = new HelpFormatter();		
		
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cli = parser.parse(options, args);
			if(cli.hasOption("h")) {
				formatter.printHelp("Eno-CLI", options);
			}
		} catch (ParseException e) {
			System.out.println("Error when parsing command: " + e.getMessage());
		}

	}

	private static Options defineOptions() {
		Options options = new Options();
		options.addOption("f", true, "Defines the source format, for example DDI");
		options.addOption("t", true, "Defines the target format, for example XForms");
		options.addOption("h", "Get some help");
		return options;
	}

}
