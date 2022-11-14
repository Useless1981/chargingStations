public final class Position {
    Double x;
    Double y;

    public Position(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double x() {
        return x;
    }

    public Double y() {
        return y;
    }

    @Override
    public String toString() {
        return "{" +
                x +
                ", " + y +
                '}';
    }
}
