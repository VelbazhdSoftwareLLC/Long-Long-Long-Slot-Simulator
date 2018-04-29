
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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
	 * Index of the none symbol in the array of symbols.
	 */
	private static final int NO_SYMBOL_INDEX = -1;

	/**
	 * Index of the empty symbol in the array of symbols.
	 */
	private static final int EMPTY_SYMBOL_INDEX = 0;

	/**
	 * List of symbols names.
	 */
	private static final List<String> SYMBOLS_NAMES = new ArrayList<>();

	/**
	 * List of symbols names.
	 */
	private static final List<Integer> SYMBOLS_NUMBERS = new ArrayList<>();

	/**
	 * Mapping symbols names to symbols numbers.
	 */
	private static final Map<String, Integer> SYMBOLS_TO_NUMBERS = new HashMap<>();

	/**
	 * Mapping symbols numbers to symbols names.
	 */
	private static final Map<Integer, String> NUMBERS_TO_SYMBOLS = new HashMap<>();

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
	 * Total bet in single game spin.
	 */
	private static int singleLineBet = 0;

	/**
	 * Total bet in single game spin.
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
	 * Max amount of won money in game.
	 */
	private static long maxWin = 0L;

	/**
	 * Total number of games played.
	 */
	private static long totalNumberOfGames = 0L;

	/**
	 * Hit rate of wins in game.
	 */
	private static long hitRate = 0L;

	/**
	 * Brute force all winning combinations flag.
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
	 * Symbols win hit rate in the game.
	 */
	private static long[] symbolsMoney = {};

	/**
	 * Symbols hit rate in the game.
	 */
	private static long[] symbolsHitRate = {};

	/**
	 * Distribution of the wins according their amount in the game.
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
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(new File(inputFileName)));
		} catch (Exception e) {
			System.out.println("Input file " + inputFileName + " is not usable!");
			System.exit(0);
		}

		XSSFSheet sheet = null;

		/*
		 * Load common game information.
		 */
		sheet = workbook.getSheet("Summary");
		int numberOfReels = (int) sheet.getRow(1).getCell(1).getNumericCellValue();
		int numberOfRows = (int) sheet.getRow(2).getCell(1).getNumericCellValue();
		int numberOfLines = (int) sheet.getRow(3).getCell(1).getNumericCellValue();
		int numberOfSymbols = (int) sheet.getRow(4).getCell(1).getNumericCellValue();

		/*
		 * Store all symbol names and mark special like wilds and scatters.
		 */
		sheet = workbook.getSheet("Symbols");
		for (int s = 1; s <= numberOfSymbols; s++) {
			String name = sheet.getRow(s).getCell(0).getStringCellValue();
			int number = (int) sheet.getRow(s).getCell(2).getNumericCellValue();

			SYMBOLS_NAMES.add(name);
			SYMBOLS_NUMBERS.add(number);

			SYMBOLS_TO_NUMBERS.put(name, number);
			NUMBERS_TO_SYMBOLS.put(number, name);
		}

		/*
		 * Load pay table.
		 */
		sheet = workbook.getSheet("Paytable");
		List<Integer[]> rows = new ArrayList<>();
		loop: for (int r = 0; true; r++) {
			Integer row[] = new Integer[numberOfReels + 1];
			for (int c = 0; c < numberOfReels; c++) {
				try {
					row[c] = SYMBOLS_TO_NUMBERS.get(sheet.getRow(r).getCell(c).getStringCellValue());
				} catch (Exception e) {
					break loop;
				}
			}
			row[numberOfReels] = (int) sheet.getRow(r).getCell(numberOfReels).getNumericCellValue();
			rows.add(row);
		}

		/*
		 * Fill paytable array.
		 */
		paytable = new int[numberOfReels + 1][rows.size()];
		for (int r = 0; r < rows.size(); r++) {
			Integer[] row = rows.get(r);
			for (int c = 0; c < row.length; c++) {
				paytable[c][r] = row[c];
			}
		}

		/*
		 * Load game reels.
		 */
		sheet = workbook.getSheet(reelsSheetName);
		strips = new String[numberOfReels][];
		for (int c = 0; c < strips.length; c++) {
			/*
			 * Calculate length of the reel.
			 */
			int length = 0;
			for (int r = 0; true; r++) {
				try {
					sheet.getRow(r).getCell(c).getStringCellValue();
				} catch (Exception e) {
					break;
				}

				length++;
			}

			/*
			 * Read the reel itself.
			 */
			strips[c] = new String[length];
			for (int r = 0; r < strips[c].length; r++) {
				strips[c][r] = sheet.getRow(r).getCell(c).getStringCellValue();
			}
		}

		/*
		 * Load size of the line.
		 */
		line = new int[numberOfReels];
	}

	/**
	 * Data initializer.
	 *
	 * @author Todor Balabanov
	 */
	private static void initialize() {
		/*
		 * Transform symbols names to integer values.
		 */
		reels = new int[strips.length][];
		for (int i = 0; i < strips.length; i++) {
			reels[i] = new int[strips[i].length];
			for (int j = 0; j < strips[i].length; j++) {
				for (int s = 0; s < SYMBOLS_NAMES.size(); s++) {
					if (SYMBOLS_NAMES.get(s).trim().equals(strips[i][j].trim()) == true) {
						reels[i][j] = s;
						break;
					}
				}
			}
		}

		/*
		 * Initialize view with no symbols.
		 */
		for (int i = 0; i < line.length; i++) {
			line[i] = NO_SYMBOL_INDEX;
		}

		/*
		 * Adjust multipliers.
		 */
		singleLineBet = 1;

		/*
		 * Calculate total bet.
		 */
		totalBet = singleLineBet;

		/*
		 * Allocate memory for the counters.
		 */
		symbolsMoney = new long[paytable[0].length];
		symbolsHitRate = new long[paytable[0].length];
		winsHistogram = new long[numberOfBins];
		// TODO Counters should be initialized with zeros.

		/*
		 * Calculate highest win according total bet and pay table values.
		 */
		highestPaytableWin = 0;
		for (int j = 0; j < paytable[reels.length].length; j++) {
			if (highestPaytableWin < paytable[reels.length][j]) {
				highestPaytableWin = paytable[reels.length][j];
			}
		}

		gameOutcomes.clear();
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
		System.out.println("Paytable:");
		for (int j = 0; j < paytable[0].length; j++) {
			for (int i = 0; i < paytable.length - 1; i++) {
				System.out.print(NUMBERS_TO_SYMBOLS.get(paytable[i][j]) + "\t");
			}
			System.out.print(paytable[paytable.length - 1][j]);
			System.out.println();
		}
		System.out.println();

		/* Vertical print of the reels. */ {
			int max = 0;
			for (int i = 0; reels != null && i < reels.length; i++) {
				if (max < reels[i].length) {
					max = reels[i].length;
				}
			}
			System.out.println("Game Reels:");
			for (int j = 0; reels != null && j < max; j++) {
				for (int i = 0; i < reels.length; i++) {
					if (j < reels[i].length) {
						System.out.print(SYMBOLS_NAMES.get(reels[i][j]));
					}
					System.out.print("\t");
				}
				System.out.print("\t");
				for (int i = 0; i < reels.length; i++) {
					if (j < reels[i].length) {
						System.out.print(SYMBOLS_NUMBERS.get(reels[i][j]));
					}
					System.out.print("\t");
				}
				System.out.println();
			}
			System.out.println();
		}

		System.out.println("Game Reels:");
		/* Count symbols in reels. */ {
			int[][] counters = new int[paytable.length - 1][SYMBOLS_NAMES.size()];
			// TODO Counters should be initialized with zeros.
			for (int i = 0; reels != null && i < reels.length; i++) {
				for (int j = 0; j < reels[i].length; j++) {
					counters[i][reels[i][j]]++;
				}
			}
			for (int i = 0; reels != null && i < reels.length; i++) {
				System.out.print("\tReel " + (i + 1));
			}
			System.out.println();
			for (int j = 0; j < SYMBOLS_NAMES.size(); j++) {
				System.out.print(SYMBOLS_NAMES.get(j) + "\t");
				for (int i = 0; i < counters.length; i++) {
					System.out.print(counters[i][j] + "\t");
				}
				System.out.println();
			}
			System.out.println("---------------------------------------------");
			System.out.print("Total:\t");
			long combinations = (reels == null) ? 0L : 1L;
			for (int i = 0; i < counters.length; i++) {
				int sum = 0;
				for (int j = 0; j < counters[0].length; j++) {
					sum += counters[i][j];
				}
				System.out.print(sum + "\t");
				if (sum != 0) {
					combinations *= sum;
				}
			}
			System.out.println();
			System.out.println("---------------------------------------------");
			System.out.println("Combinations:\t" + combinations);
		}
		System.out.println();
	}

	/**
	 * Shuffle loaded reals in stack of symbols.
	 */
	private static void shuffleReels() {
		/*
		 * Handle each reel by itself.
		 */
		for (int reel = 0; reel < reels.length; reel++) {
			for (int changes = 0, a = -1, b = -1; changes < reels[reel].length; changes++) {
				/*
				 * Select random elements.
				 */
				do {
					a = PRNG.nextInt(strips[reel].length);
					b = PRNG.nextInt(strips[reel].length);
				} while (reels[reel][a] == EMPTY_SYMBOL_INDEX || reels[reel][b] == EMPTY_SYMBOL_INDEX);

				/*
				 * Swap elements.
				 */
				int buffer = reels[reel][a];
				reels[reel][a] = reels[reel][b];
				reels[reel][b] = buffer;
			}
		}

		/*
		 * Fill strips according shuffling.
		 */
		for (int reel = 0; reel < reels.length; reel++) {
			for (int element = 0; element < reels[reel].length; element++) {
				strips[reel][element] = NUMBERS_TO_SYMBOLS.get(reels[reel][element]);
			}
		}
	}

	/**
	 * Single reels spin to fill view with symbols.
	 *
	 * @param reels
	 *            Reels strips.
	 *
	 * @author Todor Balabanov
	 */
	private static void nextCombination(int[] reelsStops) {
		reelsStops[0] += 1;
		for (int i = 0; i < reelsStops.length; i++) {
			if (reelsStops[i] >= reels[i].length) {
				reelsStops[i] = 0;
				if (i < reelsStops.length - 1) {
					reelsStops[i + 1] += 1;
				}
			}
		}
	}

	/**
	 * Single reels spin to fill view with symbols.
	 *
	 * @param reels
	 *            Reels strips.
	 *
	 * @author Todor Balabanov
	 */
	private static void spin(int[][] reels) {
		for (int i = 0; i < line.length && i < reels.length; i++) {
			int stop = -1;
			if (bruteForce == true) {
				stop = reelsStops[i];
			} else {
				stop = PRNG.nextInt(reels[i].length);
			}

			line[i] = reels[i][stop];
		}
	}

	/**
	 * Calculate win in particular line.
	 *
	 * @param line
	 *            Single line.
	 *
	 * @return Calculated win.
	 *
	 * @author Todor Balabanov
	 */
	private static int lineWin(int[] line) {
		int win = 0;

		/*
		 * Check all possible win patterns.
		 */
		for (int j=0; j<paytable[0].length; j++) {
			boolean found = true;
			for (int i = 0; i < line.length; i++) {
				if (paytable[i][j] != line[i]) {
					found = false;
					break;
				}
			}

			/*
			 * If the pattern is found just take the multiplier.
			 */
			if (found == true) {
				win = paytable[line.length][j] * totalBet;
				symbolsMoney[j] += win;
				if (win > 0) {
					symbolsHitRate[j]++;
				}
				break;
			}
		}

		return (win);
	}

	/**
	 * Update histogram information when there is a win.
	 * 
	 * @param histogram
	 *            Histogram array.
	 * @param biggest
	 *            Expected biggest win.
	 * @param win
	 *            Win value.
	 */
	private static void updateHistogram(long[] histogram, int biggest, int win) {
		/*
		 * If the win is bigger than highest according pay table values mark it in the
		 * last bin.
		 */
		if (win >= biggest) {
			histogram[histogram.length - 1]++;
			return;
		}

		int index = histogram.length * win / biggest;
		histogram[index]++;
	}

	/**
	 * Play single game.
	 *
	 * @author Todor Balabanov
	 */
	private static void singleGame() {
		/*
		 * In brute force mode reels stops are not random.
		 */
		if (bruteForce == true) {
			nextCombination(reelsStops);
		}

		/*
		 * Spin is working even in brute force mode.
		 */
		spin(reels);

		/*
		 * Win accumulated by lines.
		 */
		int win = lineWin(line);

		/*
		 * Keep values for mathematical expectation and standard deviation calculation.
		 */
		gameOutcomes.add(win);

		/*
		 * Add win to the statistics.
		 */
		wonMoney += win;
		if (maxWin < win) {
			maxWin = win;
		}

		/*
		 * Count game hit rate.
		 */
		if (win > 0) {
			hitRate++;
		}

		/*
		 * Count in the histogram.
		 */
		if (win > 0) {
			updateHistogram(winsHistogram, highestPaytableWin * totalBet, win);
		}
	}

	/**
	 * Print simulation statistics.
	 *
	 * @author Todor Balabanov
	 */
	private static void printStatistics() {
		System.out.println("Won money:\t" + wonMoney);
		System.out.println("Lost money:\t" + lostMoney);
		System.out.println("Total Number of Games:\t" + totalNumberOfGames);
		System.out.println();
		System.out.println("Total RTP:\t" + ((double) wonMoney / (double) lostMoney) + "\t\t"
				+ (100.0D * (double) wonMoney / (double) lostMoney) + "%");
		System.out.println();
		System.out.println("Hit Frequency in the Game:\t" + ((double) hitRate / (double) totalNumberOfGames) + "\t\t"
				+ (100.0D * (double) hitRate / (double) totalNumberOfGames) + "%");
		System.out.println();

		System.out.println("Max Win in the Game:\t" + maxWin);
		System.out.println();

		System.out.println("Game Symbols RTP:");
		for (int j = 0; j < symbolsMoney.length; j++) {
			for (int i = 0; i < reels.length; i++) {
				System.out.print(NUMBERS_TO_SYMBOLS.get(paytable[i][j]) + "\t");
			}
			System.out.print((double) symbolsMoney[j] / (double) lostMoney + "\t");
			System.out.println();
		}
		System.out.println();
		System.out.println("Game Symbols Hit Rate:");
		for (int j = 0; j < symbolsHitRate.length; j++) {
			for (int i = 0; i < reels.length; i++) {
				System.out.print(NUMBERS_TO_SYMBOLS.get(paytable[i][j]) + "\t");
			}
			System.out.print((double) symbolsHitRate[j] + "\t");
			System.out.println();
		}
		System.out.println();
		System.out.println("Game Symbols Hit Frequency:");
		for (int j = 0; j < symbolsHitRate.length; j++) {
			for (int i = 0; i < reels.length; i++) {
				System.out.print(NUMBERS_TO_SYMBOLS.get(paytable[i][j]) + "\t");
			}
			System.out.print((double) symbolsHitRate[j] / (double) totalNumberOfGames + "\t");
			System.out.println();
		}
		System.out.println();
		System.out.println("Game Wins Histogram:");
		/* Histogram. */ {
			for (int i = 0, bin = highestPaytableWin * totalBet
					/ winsHistogram.length; i < winsHistogram.length; i++, bin += highestPaytableWin * totalBet
							/ winsHistogram.length) {
				System.out.print("< " + bin + "\t");
			}
			System.out.println();
			double sum = 0;
			for (int i = 0; i < winsHistogram.length; i++) {
				System.out.print(winsHistogram[i] + "\t");
				sum += winsHistogram[i];
			}
			System.out.println();
			for (int i = 0; i < winsHistogram.length; i++) {
				System.out.print(100D * winsHistogram[i] / sum + "\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.print("Game Win Mean:\t");
		/* Mean */ {
			double mean = 0;
			for (Integer value : gameOutcomes) {
				mean += value;
			}
			mean /= gameOutcomes.size() != 0 ? gameOutcomes.size() : 1;
			System.out.println(mean);
		}
		System.out.print("Game Win Standard Deviation:\t");
		/* Standard Deviation */ {
			double mean = 0;
			for (Integer value : gameOutcomes) {
				mean += value;
			}
			mean /= gameOutcomes.size() != 0 ? gameOutcomes.size() : 1;

			double deviation = 0;
			for (Integer value : gameOutcomes) {
				deviation += (value - mean) * (value - mean);
			}
			deviation /= gameOutcomes.size() != 0 ? gameOutcomes.size() : 1;
			deviation = Math.sqrt(deviation);
			System.out.println(deviation);
		}
		System.out.println();
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
		 * Game reels sheet name.
		 */
		String reelsSheetName = "";
		if (commands.hasOption("reels") == true) {
			reelsSheetName = commands.getOptionValue("reels");
		} else {
			System.out.println("Game reels sheet name is missing!");
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
		 * Calculate all combinations in the game.
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
