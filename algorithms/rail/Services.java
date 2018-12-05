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

    public void setDest(City dest) {
        this.dest = dest;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

}
