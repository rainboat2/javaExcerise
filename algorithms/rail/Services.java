package rail;

public class Services {

    private City dest;
    private int fee;
    private int distance;

    public Services(City dest, int fee, int distance) {
        this.dest = dest;
        this.fee = fee;
        this.distance = distance;
    }

    public City getDest() {
        return dest;
    }

    public int getFee() {
        return fee;
    }

    public int getDistance() {
        return distance;
    }

}
