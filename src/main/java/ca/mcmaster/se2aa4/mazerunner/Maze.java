//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
    private char[][] grid;
    private int[] entry;

    public Maze(String filePath) throws IOException {
        loadMaze(filePath);
    }

    private void loadMaze(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            grid = reader.lines().map(String::toCharArray).toArray(char[][]::new);
            entry = findEntry();
        }
    }

    public boolean isPassage(int row, int col) {
        return grid[row][col] == ' ';
    }

    public int[] findEntry() {
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][0] == ' ') return new int[]{row, 0}; //Entry on left
            if (grid[row][grid[row].length - 1] == ' ') return new int[]{row, grid[row].length - 1}; //Entry on right
        }
        return null;
    }

    public boolean isEntry(int[] position) {
        return position[0] == entry[0] && position[1] == entry[1];
    }

    public char[][] getGrid() {
        return grid;
    }
}
