//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

public class MazePathSolver {
    private Maze maze;
    private Player player;
    private MazeSolver solver;
    private String solution = "";
    
    public MazePathSolver(Maze maze, Player player, MazeSolver solver) {
        this.maze = maze;
        this.player = player;
        this.solver = solver;
    }
    
    public void computeSolution() {
        solution = solver.solve(maze, player);
    }
    
    public String getSolution() {
        return solution;
    }
}
