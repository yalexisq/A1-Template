package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
    private char[][] grid;

    public Maze(String filePath) throws IOException {
        loadMaze(filePath);
    }

    private void loadMaze(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            grid = reader.lines().map(String::toCharArray).toArray(char[][]::new);
        }
    }

    public boolean isPassage(int row, int col) {
        return grid[row][col] == ' ';
    }

    public int[] findEntry() {
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][grid[row].length - 1] == ' ') return new int[]{row, grid[row].length - 1}; //Entry on right
            if (grid[row][0] == ' ') return new int[]{row, 0}; //Entry on left
        }
        return null;
    }

    public char[][] getGrid() {
        return grid;
    }
}
