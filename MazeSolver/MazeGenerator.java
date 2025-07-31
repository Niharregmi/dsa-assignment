

import java.util.*;

public class MazeGenerator {
    private static final int WALL = 1;
    private static final int PATH = 0;
    
    public static int[][] generateMaze(int rows, int cols) {
        int[][] maze = new int[rows][cols];
        for (int[] row : maze) Arrays.fill(row, WALL);
        
        // Start with all walls, then carve paths
        generateMazeRecursive(maze, 1, 1, rows - 2, cols - 2);
        
        // Ensure start and end are accessible
        maze[0][0] = PATH;
        maze[rows - 1][cols - 1] = PATH;
        
        return maze;
    }
    
    private static void generateMazeRecursive(int[][] maze, int startRow, int startCol, int endRow, int endCol) {
        if (startRow >= endRow || startCol >= endCol) return;
        
        Random rand = new Random();
        
        // Choose a random wall to break
        int wallRow = startRow + rand.nextInt(endRow - startRow + 1);
        int wallCol = startCol + rand.nextInt(endCol - startCol + 1);
        
        // Carve a path through the wall
        maze[wallRow][wallCol] = PATH;
        
        // Recursively carve the four quadrants
        generateMazeRecursive(maze, startRow, startCol, wallRow - 1, wallCol - 1);
        generateMazeRecursive(maze, startRow, wallCol + 1, wallRow - 1, endCol);
        generateMazeRecursive(maze, wallRow + 1, startCol, endRow, wallCol - 1);
        generateMazeRecursive(maze, wallRow + 1, wallCol + 1, endRow, endCol);
    }
    
    public static int[][] generateSimpleMaze(int rows, int cols) {
        int[][] maze = new int[rows][cols];
        Random rand = new Random();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    maze[i][j] = WALL; // Border walls
                } else {
                    maze[i][j] = rand.nextDouble() < 0.3 ? WALL : PATH; // 30% chance of wall
                }
            }
        }
        
        // Ensure start and end are accessible
        maze[0][0] = PATH;
        maze[rows - 1][cols - 1] = PATH;
        
        return maze;
    }
}
