//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathManager {
    private static final Logger logger = LogManager.getLogger(PathManager.class);
    private String canonicalPath = "";
    
    // Converts move sequence that may be factorized into its full form
    public String toCanonical(String inputPath) {
        StringBuilder fullPath = new StringBuilder();
        for (int i = 0; i < inputPath.length(); i++) {
            char ch = inputPath.charAt(i);
            if (Character.isDigit(ch)) {
                StringBuilder numBuffer = new StringBuilder();
                while (i < inputPath.length() && Character.isDigit(inputPath.charAt(i))) {
                    numBuffer.append(inputPath.charAt(i));
                    i++;
                }
                if (i >= inputPath.length()) {
                    throw new IllegalArgumentException("Invalid factorized path; number at end");
                }
                int count = Integer.parseInt(numBuffer.toString());
                char command = inputPath.charAt(i);
                for (int j = 0; j < count; j++) {
                    fullPath.append(command);
                }
            } else {
                fullPath.append(ch);
            }
        }
        canonicalPath = fullPath.toString();
        logger.debug("Canonical path: {}", canonicalPath);
        return canonicalPath;
    }
    
    //Simplifies canonical path
    public String toFactorized() {
        if (canonicalPath.isEmpty()) {
            throw new IllegalStateException("Canonical path not set");
        }
        StringBuilder factorized = new StringBuilder();
        int count = 1;
        for (int i = 0; i < canonicalPath.length(); i++) {
            if (i + 1 < canonicalPath.length() && canonicalPath.charAt(i) == canonicalPath.charAt(i + 1)) {
                count++;
            } else {
                if (count > 1) {
                    factorized.append(count);
                }
                factorized.append(canonicalPath.charAt(i));
                count = 1;
            }
        }
        String result = factorized.toString();
        // logger.debug("Factorized path: {}", result);
        return result;
    }
    
    public void setCanonicalPath(String path) {
        canonicalPath = path;
    }
}
