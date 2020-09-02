public class PlaceVisit {

    private int dbid;
    private Place place;
    private int lat;
    private int lon;
    private long startTime;
    private long endTime;

    public PlaceVisit(int dbid, Place place, int lat, int lon, long startTime, long endTime) {
        this.dbid = dbid;
        this.place = place;
        this.lat = lat;
        this.lon = lon;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getDbid() {
        return dbid;
    }

    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PlaceVisit{" +
                "dbid=" + dbid +
                ", place=" + place +
                ", lat=" + lat +
                ", lon=" + lon +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
