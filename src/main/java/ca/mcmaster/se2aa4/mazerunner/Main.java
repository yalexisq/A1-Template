//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

import java.io.IOException;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Maze Runner started");

        Options options = new Options();
        options.addOption("i", "input", true, "Specify the maze input file");
        options.addOption("p", "path", true, "Verify a given path for the maze");

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (!cmd.hasOption("i")) {
                logger.error("Input file not specified. Use the -i flag to provide the file path");
                return;
            }

            String inputFile = cmd.getOptionValue("i");
            logger.info("Reading maze from file: {}", inputFile);

            Maze maze = new Maze(inputFile);

            if (cmd.hasOption("p")) {
                String inputPath = cmd.getOptionValue("p").replaceAll("\\s+", "");
                boolean isValid = PathManager.verifyPath(maze, inputPath);
                System.out.println(isValid ? "correct path" : "incorrect path");
                return;
            }

            Player player = new Player();
            String rawPath = player.computePath(maze);
            String factorizedPath = PathManager.factorizePath(rawPath);
            System.out.println(factorizedPath);

        } catch (ParseException e) {
            logger.error("Error parsing command-line arguments: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Error reading the maze file: {}", e.getMessage());
        }

        logger.info("Maze Runner ended");
    }
}
