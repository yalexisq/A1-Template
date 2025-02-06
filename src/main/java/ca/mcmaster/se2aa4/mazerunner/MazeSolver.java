//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

public interface MazeSolver {
    // Computes a path for the player to reach the maze exit
    String solve(Maze maze, Player player);
}
