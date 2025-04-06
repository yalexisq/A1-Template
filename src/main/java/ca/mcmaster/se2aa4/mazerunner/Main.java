//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
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
            String inputMazeFile = cmd.getOptionValue("i");
            String moveSeq = cmd.getOptionValue("p");

            //Load the maze -> using Singleton pattern
            Maze maze = Maze.getInstance(inputMazeFile);
            PathManager pathMgr = new PathManager();

            if (moveSeq != null) {
                String canonicalMoves = pathMgr.toCanonical(moveSeq);
                String resultE = verifyPath(maze, 'E', canonicalMoves);
                String resultW = verifyPath(maze, 'W', canonicalMoves);
                System.out.println(resultE.equalsIgnoreCase("correct path")
                        || resultW.equalsIgnoreCase("correct path")
                        ? "correct path" : "incorrect path");
            } else {
                maze.setEntrySide('E');
                Player player = new Player(maze.getEntry(), Direction.fromChar('E'));
                MazePathSolver solver = new MazePathSolver(maze, player, new RightHandSolver());
                solver.computeSolution();
                String rawPath = solver.getSolution();
                pathMgr.setCanonicalPath(rawPath);
                System.out.println(pathMgr.toFactorized());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String verifyPath(Maze maze, char startSide, String moves) {
        maze.setEntrySide(startSide);
        Player tester = new Player(maze.getEntry(), Direction.fromChar(startSide));
        for (int i = 0; i < moves.length(); i++) {
            char move = moves.charAt(i);
            if (move != 'F' && move != 'L' && move != 'R') {
                throw new IllegalArgumentException("Invalid move at " + move);
            }
            tester.executeMove(move, maze);
        }
        int[] pos = tester.getCurrentPosition();
        int[] exitPos = maze.getExit();
        return (pos[0] == exitPos[0] && pos[1] == exitPos[1]) ? "correct path" : "incorrect path";
    }
}
