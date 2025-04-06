//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RightHandSolver implements MazeSolver {
    private static final Logger logger = LogManager.getLogger(RightHandSolver.class);

    @Override
    public String solve(Maze maze, Player player) {
        StringBuilder moves = new StringBuilder();
        int[] exitPos = maze.getExit();

        //Continue until the player’s position is at the exit
        while (!atExit(player.getCurrentPosition(), exitPos)) {
            
            Direction rightDir = player.getFacing().turnRight();
            int testRow = player.getCurrentPosition()[0] + rightDir.getDeltaRow();
            int testCol = player.getCurrentPosition()[1] + rightDir.getDeltaCol();
            
            if (maze.isOpen(testRow, testCol)) {
                //Turn right then move forward
                player.executeMove('R', maze);
                moves.append('R');
                player.executeMove('F', maze);
                moves.append('F');
                //logger.debug("Turned right and moved forward now at [{}, {}]", 
                        //player.getCurrentPosition()[0], player.getCurrentPosition()[1]);
            } 
            
            else {
                //Moves forward if possible
                int frontRow = player.getCurrentPosition()[0] + player.getFacing().getDeltaRow();
                int frontCol = player.getCurrentPosition()[1] + player.getFacing().getDeltaCol();
                if (maze.isOpen(frontRow, frontCol)) {
                    player.executeMove('F', maze);
                    moves.append('F');
                    //logger.debug("Moved forward to [{}, {}]", 
                            //player.getCurrentPosition()[0], player.getCurrentPosition()[1]);
                } 
                
                else {
                    //Otherwise, turn left
                    player.executeMove('L', maze);
                    moves.append('L');
                    //logger.debug("Turned left; now facing {}", player.getFacing());
                }
            }
        }
        //logger.info("Maze exit reached at [{}, {}]", player.getCurrentPosition()[0], player.getCurrentPosition()[1]);
        return moves.toString();
    }
    
    private boolean atExit(int[] current, int[] exit) {
        return current[0] == exit[0] && current[1] == exit[1];
    }
}
