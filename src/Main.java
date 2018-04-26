
/*==============================================================================
*                                                                              *
* Long Long Long Slot Simulator version 1.0.0                                  *
* Copyrights (C) 2018 Velbazhd Software LLC                                    *
*                                                                              *
* developed by Todor Balabanov ( todor.balabanov@gmail.com )                   *
* Sofia, Bulgaria                                                              *
*                                                                              *
* This program is free software: you can redistribute it and/or modify         *
* it under the terms of the GNU General Public License as published by         *
* the Free Software Foundation, either version 3 of the License, or            *
* (at your option) any later version.                                          *
*                                                                              *
* This program is distributed in the hope that it will be useful,              *
* but WITHOUT ANY WARRANTY; without even the implied warranty of               *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                *
* GNU General Public License for more details.                                 *
*                                                                              *
* You should have received a copy of the GNU General Public License            *
* along with this program. If not, see <http://www.gnu.org/licenses/>.         *
*                                                                              *
==============================================================================*/

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

/**
 * Application single entry point class.
 * 
 * @author Todor Balabanov
 */
public class Main {
	/**
	 * Pseudo-random number generator.
	 */
	private static final RandomGenerator PRNG = new MersenneTwister();

	/**
	 * List of symbols names.
	 */
	private static final List<String> SYMBOLS_NAMES = new ArrayList<String>();

	/**
	 * List of symbols names.
	 */
	private static final List<Integer> SYMBOLS_NUMBERS = new ArrayList<Integer>();

	/**
	 * Slot game pay table.
	 */
	private static int[][] paytable = {};

	/**
	 * Stips in the game as symbols names.
	 */
	private static String[][] strips = {};

	/**
	 * Stips in the game.
	 */
	private static int[][] reels = null;

	/**
	 * Use reels stops in brute force combinations generation.
	 */
	private static int[] reelsStops = {};

	/**
	 * Current visible symbols on the screen.
	 */
	private static int[] line = {};

	/**
	 * Total bet in single base game spin.
	 */
	private static int singleLineBet = 0;

	/**
	 * Total bet in single base game spin.
	 */
	private static int totalBet = 0;

	/**
	 * Total amount of won money.
	 */
	private static long wonMoney = 0L;

	/**
	 * Total amount of lost money.
	 */
	private static long lostMoney = 0L;

	/**
	 * All values as win in the game (even zeros) for the whole simulation.
	 */
	private static List<Integer> gameOutcomes = new ArrayList<Integer>();

	/**
	 * Max amount of won money in base game.
	 */
	private static long maxWin = 0L;

	/**
	 * Total number of base games played.
	 */
	private static long totalNumberOfGames = 0L;

	/**
	 * Hit rate of wins in base game.
	 */
	private static long hitRate = 0L;

	/**
	 * Brute force all winning combinations in base game only flag.
	 */
	private static boolean bruteForce = false;

	/**
	 * Verbose output flag.
	 */
	private static boolean verboseOutput = false;

	/**
	 * Number of bins used in the histogram.
	 */
	private static int numberOfBins = 1000;

	/**
	 * Symbols win hit rate in base game.
	 */
	private static long[][] symbolMoney = {};

	/**
	 * Symbols hit rate in base game.
	 */
	private static long[][] symbolsHitRate = {};

	/**
	 * Distribution of the wins according their amount in the base game.
	 */
	private static long winsHistogram[] = {};

	/**
	 * Highest win according pay table. It is used in wins histogram calculations.
	 */
	private static int highestPaytableWin = 0;

	/**
	 * Print simulation execution command.
	 *
	 * @param args
	 *            Command line arguments list.
	 *
	 * @author Todor Balabanov
	 */
	private static void printExecuteCommand(String[] args) {
		System.out.println("Execute command:");
		System.out.println();
		System.out.print("java Main ");
		for (int i = 0; i < args.length; i++) {
			System.out.print(args[i] + " ");
		}
		System.out.println();
	}

	/**
	 * Print about information.
	 *
	 * @author Todor Balabanov
	 */
	private static void printAbout() {
		System.out.println("*******************************************************************************");
		System.out.println("* Long Long Long Slot Simulator version 1.0.0                                 *");
		System.out.println("* Copyrights (C) 2018 Velbazhd Software LLC                                   *");
		System.out.println("*                                                                             *");
		System.out.println("* developed by Todor Balabanov ( todor.balabanov@gmail.com )                  *");
		System.out.println("* Sofia, Bulgaria                                                             *");
		System.out.println("*                                                                             *");
		System.out.println("* This program is free software: you can redistribute it and/or modify        *");
		System.out.println("* it under the terms of the GNU General Public License as published by        *");
		System.out.println("* the Free Software Foundation, either version 3 of the License, or           *");
		System.out.println("* (at your option) any later version.                                         *");
		System.out.println("*                                                                             *");
		System.out.println("* This program is distributed in the hope that it will be useful,             *");
		System.out.println("* but WITHOUT ANY WARRANTY; without even the implied warranty of              *");
		System.out.println("* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               *");
		System.out.println("* GNU General Public License for more details.                                *");
		System.out.println("*                                                                             *");
		System.out.println("* You should have received a copy of the GNU General Public License           *");
		System.out.println("* along with this program. If not, see <http://www.gnu.org/licenses/>.        *");
		System.out.println("*                                                                             *");
		System.out.println("*******************************************************************************");
	}

	/**
	 * Load data structures from ODS file.
	 * 
	 * @param inputFileName
	 *            Name of the input file.
	 * @param reelsSheetName
	 *            Name of the game reels sheet.
	 */
	private static void loadGameStructure(String inputFileName, String reelsSheetName) {
	}

	/**
	 * Data initializer.
	 *
	 * @author Todor Balabanov
	 */
	private static void initialize() {
	}

	/**
	 * Generate initial reels according pay table values.
	 * 
	 * @param targetLength
	 *            Initial desired length of the reels.
	 */
	private static void initialReels(int targetLength) {
	}

	/**
	 * Print all simulation input data structures.
	 *
	 * @author Todor Balabanov
	 */
	private static void printDataStructures() {
	}

	/**
	 * Shuffle loaded reals in stack of symbols.
	 */
	private static void shuffleReels() {
	}

	/**
	 * Play single base game.
	 *
	 * @author Todor Balabanov
	 */
	private static void singleGame() {
	}

	/**
	 * Print simulation statistics.
	 *
	 * @author Todor Balabanov
	 */
	private static void printStatistics() {
	}

	/**
	 * Application single entry point method.
	 * 
	 * java Main -g 10m -p 100k -input "./doc/game001.xlsx" -reels "Reels 96.3 RTP"
	 * 
	 * java Main -g 100m -p 1m -input "./doc/game001.xlsx" -histogram 2000 -reels
	 * "Reels 96.3 RTP"
	 * 
	 * @param args
	 *            Command line arguments.
	 * 
	 * @throws ParseException
	 *             When there is a problem with command line arguments.
	 */
	public static void main(String[] args) throws ParseException {
		/*
		 * Print execution command.
		 */
		printExecuteCommand(args);
		System.out.println();

		/*
		 * Handling command line arguments with library.
		 */
		Options options = new Options();
		options.addOption(new Option("h", "help", false, "Help screen."));

		options.addOption(Option.builder("input").argName("file").hasArg().valueSeparator()
				.desc("Input Excel file name.").build());

		options.addOption(Option.builder("reels").argName("sheet").hasArg().valueSeparator()
				.desc("Excel sheet name with game reels.").build());

		options.addOption(Option.builder("g").longOpt("generations").argName("number").hasArg().valueSeparator()
				.desc("Number of games (default 20m).").build());
		options.addOption(Option.builder("p").longOpt("progress").argName("number").hasArg().valueSeparator()
				.desc("Progress on each iteration number (default 1m).").build());

		options.addOption(Option.builder("histogram").argName("size").hasArg().valueSeparator()
				.desc("Histograms of the wins with particular number of bins (default 1000).").build());

		options.addOption(Option.builder("initial").argName("number").hasArg().valueSeparator()
				.desc("Generate initial reels according paytable values and reel target length.").build());

		options.addOption(new Option("shuffle", false, "Shuffle loaded reels with symbols."));

		options.addOption(new Option("verbose", false, "Print intermediate results."));
		options.addOption(new Option("verify", false, "Print input data structures."));

		/*
		 * Parse command line arguments.
		 */
		CommandLineParser parser = new DefaultParser();
		CommandLine commands = parser.parse(options, args);

		/*
		 * If help is required print it and quit the program.
		 */
		if (commands.hasOption("help") == true) {
			printAbout();
			System.out.println();
			(new HelpFormatter()).printHelp("java Main", options, true);
			System.out.println();
			System.exit(0);
		}

		/*
		 * Read input file name.
		 */
		String inputFileName = "";
		if (commands.hasOption("input") == true) {
			inputFileName = commands.getOptionValue("input");
		} else {
			System.out.println("Input file name is missing!");
			System.out.println();
			(new HelpFormatter()).printHelp("java Main", options, true);
			System.out.println();
			System.exit(0);
		}

		/*
		 * Base game reels sheet name.
		 */
		String reelsSheetName = "";
		if (commands.hasOption("basereels") == true) {
			reelsSheetName = commands.getOptionValue("basereels");
		} else {
			System.out.println("Base game reels sheet name is missing!");
			System.out.println();
			(new HelpFormatter()).printHelp("java Main", options, true);
			System.out.println();
			System.exit(0);
		}

		/*
		 * Number of bins used in the wins histogram.
		 */
		if (commands.hasOption("histogram") == true) {
			numberOfBins = Integer.valueOf(commands.getOptionValue("histogram"));
		}

		/*
		 * Reading of input file and reels data sheet.
		 */
		loadGameStructure(inputFileName, reelsSheetName);
		initialize();

		/*
		 * Generate initial reels according pay table values.
		 */
		if (commands.hasOption("initial") == true) {
			initialReels(Integer.valueOf(commands.getOptionValue("initial")));
			initialize();
			printDataStructures();
			System.exit(0);
		}

		/*
		 * Shuffle loaded reels with stacked size value.
		 */
		if (commands.hasOption("shuffle") == true) {
			shuffleReels();
			initialize();
			printDataStructures();
			System.exit(0);
		}

		/*
		 * Verification of the data structures.
		 */
		if (commands.hasOption("verify") == true) {
			printDataStructures();
			System.exit(0);
		}

		/*
		 * Run brute force instead of Monte Carlo simulation.
		 */
		if (commands.hasOption("bruteforce") == true) {
			bruteForce = true;
		}

		/*
		 * Print calculation progress.
		 */
		if (commands.hasOption("verbose") == true) {
			verboseOutput = true;
		}

		/*
		 * Simulation parameters.
		 */
		long numberOfSimulations = 20_000_000L;
		long progressPrintOnIteration = 1_000_000L;

		/*
		 * Adjust number of simulations.
		 */
		if (commands.hasOption("generations") == true) {
			try {
				numberOfSimulations = Long
						.valueOf(commands.getOptionValue("generations").replace("m", "000000").replace("k", "000"));
			} catch (Exception e) {
			}
		}

		/*
		 * Adjust progress reporting interval.
		 */
		if (commands.hasOption("progress") == true) {
			try {
				progressPrintOnIteration = Long
						.valueOf(commands.getOptionValue("progress").replace("m", "000000").replace("k", "000"));
				verboseOutput = true;
			} catch (Exception e) {
			}
		}

		/*
		 * Calculate all combinations in base game.
		 */
		if (bruteForce == true) {
			/*
			 * Minus one is needed in order first combination to start from zeros in brute
			 * force calculations.
			 */
			reelsStops = new int[reels.length];
			for (int i = 1; i < reelsStops.length; i++) {
				reelsStops[i] = 0;
			}
			reelsStops[0] = -1;

			numberOfSimulations = 1;
			for (int i = 0; i < reels.length; i++) {
				numberOfSimulations *= reels[i].length;
			}
		}

		/*
		 * Simulation main loop.
		 */
		for (long g = 0L; g < numberOfSimulations; g++) {
			if (verboseOutput == true && g == 0) {
				System.out.println("Games\tRTP");
			}

			/*
			 * Print progress report.
			 */
			if (verboseOutput == true && g % progressPrintOnIteration == 0) {
				try {
					System.out.print(g + " of " + numberOfSimulations);
					System.out.print("\t");
					System.out.print(String.format("  %6.2f", 100D * ((double) wonMoney / (double) lostMoney)));
				} catch (Exception e) {
					System.err.println(e);
				}
				System.out.println();
			}

			totalNumberOfGames++;

			lostMoney += totalBet;

			singleGame();
		}

		System.out.println("********************************************************************************");
		printStatistics();
		System.out.println("********************************************************************************");
	}
}
