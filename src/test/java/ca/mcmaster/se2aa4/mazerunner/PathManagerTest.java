//Alexis Quilatan, 400507554, 2AA4
//This test ensures that the PathManager class correctly converts between factorized
//and canonical path formats. It checks both valid and invalid inputs for the conversion methods. 
package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PathManagerTest {
    private PathManager pathManager;

    @BeforeEach
    void setUp() {
        pathManager = new PathManager();
    }

    @Test
    //Verifying the conversion of a factorized path to its canonical form
    void testToCanonical_ValidFactorizedPath() {
        assertEquals("FFFFRRLF", pathManager.toCanonical("4F2RLF"));
        assertEquals("FF", pathManager.toCanonical("2F"));
        assertEquals("FRFRF", pathManager.toCanonical("F1R1F1R1F"));
    }

    @Test
    //Verifying the conversion of a factorized path to its canonical form with invalid inputs
    void testToCanonical_InvalidFactorizedPath() {
        assertThrows(IllegalArgumentException.class, () -> pathManager.toCanonical("2"));
        assertThrows(IllegalArgumentException.class, () -> pathManager.toCanonical("3F232222222"));
        assertThrows(IllegalArgumentException.class, () -> pathManager.toCanonical("this is not a path"));
    }

    @Test
    //Verifying the conversion of a factorized path to its canonical form
    void testToFactorized_ValidCanonicalPath() {
        pathManager.setCanonicalPath("FFFFRRLF");
        assertEquals("4F2RLF", pathManager.toFactorized());
        
        pathManager.setCanonicalPath("FF");
        assertEquals("2F", pathManager.toFactorized());
    }

    @Test
    //Verifying empty canonical path
    void testToFactorized_EmptyCanonicalPath() {
        assertThrows(IllegalStateException.class, () -> pathManager.toFactorized());
    }
}
