//Alexis Quilatan, 400507554, 2AA4
package ca.mcmaster.se2aa4.mazerunner;

public enum Direction {
    NORTH(-1, 0, 'N'),
    EAST(0, 1, 'E'),
    SOUTH(1, 0, 'S'),
    WEST(0, -1, 'W');
    
    private final int deltaRow;
    private final int deltaCol;
    private final char symbol;
    
    Direction(int dRow, int dCol, char symbol) {
        this.deltaRow = dRow;
        this.deltaCol = dCol;
        this.symbol = symbol;
    }
    
    public int getDeltaRow() {
        return deltaRow;
    }
    
    public int getDeltaCol() {
        return deltaCol;
    }
    
    public char getSymbol() {
        return symbol;
    }
    
    public Direction turnLeft() {
        switch (this) {
            case NORTH: return WEST;
            case WEST:  return SOUTH;
            case SOUTH: return EAST;
            case EAST:  return NORTH;
            default:    return NORTH;
        }
    }
    
    public Direction turnRight() {
        switch (this) {
            case NORTH: return EAST;
            case EAST:  return SOUTH;
            case SOUTH: return WEST;
            case WEST:  return NORTH;
            default:    return NORTH;
        }
    }
    
    public static Direction fromChar(char c) {
        for (Direction d : values()) {
            if (d.symbol == Character.toUpperCase(c)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Invalid direction " + c);
    }
}
