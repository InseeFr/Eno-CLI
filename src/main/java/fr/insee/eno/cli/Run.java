package fr.insee.eno.cli;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.insee.eno.DDI2FRContext;
import fr.insee.eno.GenerationService;

/**
 * Defines the command-line interface to Eno.
 * A basic scenario is to produce an XForms questionnaire from a DDI specification.
 * */
public class Run {
	
	private static final Logger logger = LogManager.getLogger(Run.class);
	
	// FIXME temporary
	private static final String SIMPSONS_TEST_PATH = "D:/__TEMP/eno-test/simpsons.xml"; 

	public static void main(String[] args) {
		logger.info("> Start <");
		Options options = defineOptions();
		CommandLineParser parser = new DefaultParser();		
		try {
			launchGeneration(args, options, parser);
		} catch (ParseException e) {
			logger.error("Error when parsing command: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Generic exception: " + e.getMessage());
		}		
		System.out.println("> End <");
	}

	/**
	 * @param args
	 * @param options
	 * @param parser
	 * @throws ParseException
	 * @throws Exception
	 */
	private static void launchGeneration(String[] args, Options options, CommandLineParser parser)
			throws ParseException, Exception {
		CommandLine cli = parser.parse(options, args);
		if(cli.hasOption("h")) {
			printHelp(options);
		}
		Injector injector = Guice.createInjector(new DDI2FRContext());
		GenerationService service = injector.getInstance(GenerationService.class);
		File generatedFile = service.generateQuestionnaire(new File(SIMPSONS_TEST_PATH), null);
		logger.info("File generated successfully: " + generatedFile);
	}

	private static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Eno-CLI", options);
	}

	private static Options defineOptions() {
		Options options = new Options();
		options.addOption("f", true, "Defines the source format, for example DDI");
		options.addOption("t", true, "Defines the target format, for example XForms");
		options.addOption("h", "Get some help");
		return options;
	}

}
