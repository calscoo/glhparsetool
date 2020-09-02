public class Place {

    private int dbid;
    private String address;
    private String name;

    public Place(int dbid, String address, String name) {
        this.dbid = dbid;
        this.address = address.replaceAll("\\n", ", ").replaceAll("'", "''");
        this.name = name.replaceAll("'", "''");
    }

    public int getDbid() {
        return dbid;
    }

    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address.replaceAll("\\n", ", ").replaceAll("'", "''");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replaceAll("'", "''");
    }

    @Override
    public String toString() {
        return "Place{" +
                "dbid=" + dbid +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public boolean placeEquals(Place place) {
        if (this.address.equals(place.address) && this.name.equals(place.name))
        {
            return true;
        }
        return false;
    }
}
