package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Maze Runner started");

        //Set up cmd-line options
        Options options = new Options();
        options.addOption("i", "input", true, "Specify the maze input file");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            //Parses cmd-line arguments
            cmd = parser.parse(options, args);

            //Checks if the -i option is provided
            if (!cmd.hasOption("i")) {
                logger.error("Input file not specified. Use the -i flag to provide the file path.");
                return;
            }

            String inputFile = cmd.getOptionValue("i");
            logger.info("Reading maze from file: {}", inputFile);

            //Reads and processes maze file
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    StringBuilder rowOutput = new StringBuilder();
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '#') {
                            rowOutput.append("WALL ");
                        } else if (line.charAt(i) == ' ') {
                            rowOutput.append("PASS ");
                        }
                    }
                    logger.trace(rowOutput.toString());
                }
            } catch (Exception e) {
                logger.error("Error reading the maze file: {}", e.getMessage());
            }

            logger.info("Path computation started.");
            logger.warn("Path computation is not implemented.");
        } catch (ParseException e) {
            logger.error("Error parsing command-line arguments: {}", e.getMessage());
        }

        logger.info("Maze Runner ended.");
    }
}
