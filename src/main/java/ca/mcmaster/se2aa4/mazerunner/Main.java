//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder("i")
                .hasArg()
                .desc("File path to the maze definition")
                .required()
                .build());
        options.addOption(Option.builder("p")
                .hasArg()
                .desc("Path move sequence (optional)")
                .build());

        CommandLineParser parser = new DefaultParser();

        try {

            CommandLine cmd = parser.parse(options, args);
            String inputmazeFile = cmd.getOptionValue("i");
            String moveSeq = cmd.getOptionValue("p");

            //Loads maze from file
            Maze maze = new Maze(inputmazeFile);
            PathManager pathMgr = new PathManager();
            //logger.info("** Starting Maze Runner");

            //Verifies any provided paths 
            if (moveSeq != null) {
                //logger.info("***Verifying provided path");
                String canonicalMoves = pathMgr.toCanonical(moveSeq);
                //Tries verifying using both possible starting orientations
                String resultE = verifyPath(maze, 'E', canonicalMoves);
                String resultW = verifyPath(maze, 'W', canonicalMoves);
                System.out.println(resultE.equalsIgnoreCase("correct path") 
                    || resultW.equalsIgnoreCase("correct path") 
                     ? "correct path" : "incorrect path");
            } 
            
            else {
                //No move sequence provided so solve the maze using our algorithm.
                maze.setEntrySide('E');
                Player player = new Player(maze.getEntry(), Direction.fromChar('E'));
                MazePathSolver solver = new MazePathSolver(maze, player, new RightHandSolver());
                //logger.info("**** Computing path");
                solver.computeSolution();
                String rawPath = solver.getSolution();
                pathMgr.setCanonicalPath(rawPath);
                System.out.println(pathMgr.toFactorized());
            }

            //logger.info("** End of MazeRunner");
        } catch (Exception e) {
            //logger.error("/!\ An error has occured /!\", e);
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static String verifyPath(Maze maze, char startSide, String moves) {
        maze.setEntrySide(startSide);
        Player tester = new Player(maze.getEntry(), Direction.fromChar(startSide));
        // Process the move sequence
        for (int i = 0; i < moves.length(); i++) {
            char move = moves.charAt(i);
            if (move != 'F' && move != 'L' && move != 'R') {
                throw new IllegalArgumentException("Invalid move at " + move);
            }
            tester.executeMove(move, maze);
        }
        int[] pos = tester.getCurrentPosition();
        int[] exitPos = maze.getExit();
        //logger.info("Final position: [{}, {}], Exit: [{}, {}]", pos[0], pos[1], exitPos[0], exitPos[1]);
        return (pos[0] == exitPos[0] && pos[1] == exitPos[1]) ? "correct path" : "incorrect path";
    }
    //logger.info("Maze Runner ended");
}
