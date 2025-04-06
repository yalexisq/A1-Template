//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Maze {
    private static Maze instance; //Singleton instance
    private char[][] grid;
    private int[] entry = new int[2];
    private int rows, cols;
    private int[] exit = new int[2];

    //Private constructor to prevent direct instantiation
    private Maze(String filePath) {
        grid = loadMazeFromFile(filePath);
        if (grid == null) {
            throw new IllegalArgumentException("Failed to load maze file from " + filePath);
        }
    }

    //Static method to get the singleton instance
    public static Maze getInstance(String filePath) {
        if (instance == null) {
            instance = new Maze(filePath);
        }
        return instance;
    }

    //Public reset method for testing purposes
    public static void resetInstance() {
        instance = null;
    }

    private char[][] loadMazeFromFile(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ioe) {
            return null;
        }

        if (lines.isEmpty()) {
            return null;
        }

        rows = lines.size();
        cols = lines.get(0).length();
        char[][] tempGrid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            String rowLine = lines.get(i);
            if (rowLine.length() < cols) {
                rowLine = padLine(rowLine);
            }
            tempGrid[i] = rowLine.toCharArray();
        }
        return tempGrid;
    }

    private String padLine(String line) {
        StringBuilder sb = new StringBuilder(line);
        while (sb.length() < cols) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public void setEntrySide(char side) {
        int[] left = null;
        int[] right = null;

        //Scan through rows to find openings on maze edges
        for (int i = 0; i < rows; i++) {
            if (grid[i][0] == ' ' && left == null) {  //First open space on the left edge
                left = new int[]{i, 0};
            }
            if (grid[i][cols - 1] == ' ') { //Last open space on the right edge
                right = new int[]{i, cols - 1};
            }
        }
        //Set entry and exit based on the specified side
        if (Character.toUpperCase(side) == 'E') {
            entry = left; //Entry on the left edge
            exit = right; //Exit on the right edge
        } else if (Character.toUpperCase(side) == 'W') {
            entry = right; //Entry on the right edge
            exit = left; //Exit on the left edge
        } else {
            throw new IllegalArgumentException("Unknown entry side on " + side);
        }
    }

    public int[] getEntry() {
        return entry;
    }

    public int[] getExit() {
        return exit;
    }

    public boolean isOpen(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return false;
        }
        return grid[r][c] == ' ';
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
