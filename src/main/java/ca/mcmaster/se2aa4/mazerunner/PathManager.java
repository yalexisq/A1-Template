//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

public class PathManager {

    public static String factorizePath(String path) {
        if (path.isEmpty()) return "";

        StringBuilder factorized = new StringBuilder();
        char prev = path.charAt(0);
        int count = 1;

        for (int i = 1; i < path.length(); i++) {
            if (path.charAt(i) == prev) {
                count++;
            } else {
                factorized.append(count > 1 ? count + "" + prev : prev);
                prev = path.charAt(i);
                count = 1;
            }
        }
        factorized.append(count > 1 ? count + "" + prev : prev);

        return factorized.toString();
    }

    public static boolean verifyPath(Maze maze, String path) {
        Player player = new Player();
        String computedPath = player.computePath(maze);
        return computedPath.equals(path);
    }
}
