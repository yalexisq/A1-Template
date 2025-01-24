package ca.mcmaster.se2aa4.mazerunner;

import java.io.IOException;
import org.apache.commons.cli.*;
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

            if (!cmd.hasOption("i")) {
                logger.error("Input file not specified. Use the -i flag to provide the file path");
                return;
            }

            String inputFile = cmd.getOptionValue("i");
            logger.info("Reading maze from file: {}", inputFile);

            //Initializes maze
            Maze maze = new Maze(inputFile);

            //Prints maze visualization
            logger.info("Maze visualization:");
            char[][] grid = maze.getGrid();
            for (char[] row : grid) {
                StringBuilder rowOutput = new StringBuilder();
                for (char c : row) {
                    rowOutput.append(c == '#' ? "WALL " : "PASS ");
                }
                logger.info(rowOutput.toString());
            }

            //Finds path using player
            Player player = new Player();
            String canonicalPath = player.computePath(maze);
            logger.info("Canonical Path: {}", canonicalPath);
        } catch (ParseException e) {
            logger.error("Error parsing command-line arguments: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Error reading the maze file: {}", e.getMessage());
        }

        logger.info("Maze Runner ended");
    }
}
