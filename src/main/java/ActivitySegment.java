public class ActivitySegment {

    private int dbid;
    private String activityType;
    private int startLat;
    private int startLon;
    private int endLat;
    private int endLon;
    private int distance;
    private long startTime;
    private long endTime;

    public ActivitySegment(int dbid, String activityType, int startLat, int startLon, int endLat, int endLon, int distance, long startTime, long endTime) {
        this.dbid = dbid;
        this.activityType = activityType;
        this.startLat = startLat;
        this.startLon = startLon;
        this.endLat = endLat;
        this.endLon = endLon;
        this.distance = distance;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getDbid() {
        return dbid;
    }

    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public int getStartLat() {
        return startLat;
    }

    public void setStartLat(int startLat) {
        this.startLat = startLat;
    }

    public int getStartLon() {
        return startLon;
    }

    public void setStartLon(int startLon) {
        this.startLon = startLon;
    }

    public int getEndLat() {
        return endLat;
    }

    public void setEndLat(int endLat) {
        this.endLat = endLat;
    }

    public int getEndLon() {
        return endLon;
    }

    public void setEndLon(int endLon) {
        this.endLon = endLon;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
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
        return "ActivitySegment{" +
                "dbid=" + dbid +
                ", activityType='" + activityType + '\'' +
                ", startLat=" + startLat +
                ", startLon=" + startLon +
                ", endLat=" + endLat +
                ", endLon=" + endLon +
                ", distance=" + distance +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
