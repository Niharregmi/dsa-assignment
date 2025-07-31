import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MazeSolver {
    private final int[][] maze;
    private final List<Point> path = new ArrayList<>();
    private final int rows, cols;
    private final Point start, end;
    
    public MazeSolver(int[][] maze, Point start, Point end) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.start = start;
        this.end = end;
    }
    
    public void solveDFS() {
        path.clear();
        boolean[][] visited = new boolean[rows][cols];
        for (boolean[] row : visited) Arrays.fill(row, false);
        
        if (dfs(start.x, start.y, visited)) {
            System.out.println("DFS Path found!");
        } else {
            System.out.println("No DFS path found!");
        }
    }
    
    private boolean dfs(int x, int y, boolean[][] visited) {
        if (x < 0 || x >= rows || y < 0 || y >= cols || visited[x][y] || maze[x][y] == 1) {
            return false;
        }
        
        visited[x][y] = true;
        path.add(new Point(x, y));
        
        if (x == end.x && y == end.y) {
            return true;
        }
        
        // Try all 4 directions
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        for (int i = 0; i < 4; i++) {
            if (dfs(x + dx[i], y + dy[i], visited)) {
                return true;
            }
        }
        
        path.remove(path.size() - 1);
        return false;
    }
    
    public void solveBFS() {
        path.clear();
        boolean[][] visited = new boolean[rows][cols];
        for (boolean[] row : visited) Arrays.fill(row, false);
        
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> parentMap = new HashMap<>();
        
        queue.offer(start);
        visited[start.x][start.y] = true;
        
        while (!queue.isEmpty()) {
            Point curr = queue.poll();
            
            if (curr.x == end.x && curr.y == end.y) {
                // Reconstruct path
                Point current = end;
                while (current != null) {
                    path.add(0, current);
                    current = parentMap.get(current);
                }
                System.out.println("BFS Path found!");
                return;
            }
            
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};
            
            for (int i = 0; i < 4; i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];
                
                if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && !visited[nx][ny] && maze[nx][ny] == 0) {
                    Point next = new Point(nx, ny);
                    queue.offer(next);
                    visited[nx][ny] = true;
                    parentMap.put(next, curr);
                }
            }
        }
        
        System.out.println("No BFS path found!");
    }
    
    public List<Point> getPath() {
        return new ArrayList<>(path);
    }
    
    public void draw(Graphics g, int size) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw maze
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = j * size;
                int y = i * size;
                
                if (maze[i][j] == 1) {
                    g2.setColor(new Color(40, 40, 40)); // dark gray walls
                } else {
                    g2.setColor(new Color(230, 230, 230)); // light path
                }
                g2.fillRect(x, y, size, size);
                g2.setColor(Color.BLACK);
                g2.drawRect(x, y, size, size);
            }
        }
        
        // Draw path
        if (!path.isEmpty()) {
            g2.setColor(new Color(150, 200, 255, 100));
            for (Point p : path) {
                int x = p.y * size;
                int y = p.x * size;
                g2.fillRect(x, y, size, size);
            }
            
            g2.setColor(new Color(0, 255, 255, 180));
            for (Point p : path) {
                int x = p.y * size + size / 4;
                int y = p.x * size + size / 4;
                g2.fillOval(x, y, size / 2, size / 2);
            }
        }
        
        // Draw start and end
        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(start.y * size, start.x * size, size, size);
        
        g2.setColor(new Color(0, 200, 0)); // green
        g2.fillOval(start.y * size + size / 4, start.x * size + size / 4, size / 2, size / 2);
        
        g2.setColor(new Color(220, 30, 30)); // red
        g2.fillOval(end.y * size + size / 4, end.x * size + size / 4, size / 2, size / 2);
    }
}
    

