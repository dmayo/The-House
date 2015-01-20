package stats;

public enum Position {
FIRST, MIDDLE, LAST;


public boolean isBefore(Position other){
    return other.ordinal() < this.ordinal();
}
}
