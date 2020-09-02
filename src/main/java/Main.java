import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    static List<PlaceVisit> placeVisits = new ArrayList<>();
    static List<ActivitySegment> activitySegments = new ArrayList<>();
    static List<Place> places = new ArrayList<>();
    static int placeVisitDbid = 1;
    static int activitySegmentDbid = 1;
    static int placeDbid = 1;

    static final String PROJECT_PATH = "C:/Users/scout/Google Drive/School/20/Spring/CSCI 366 Database Systems/Project/";
    static final String LOCATION_HISTORY_PATH = PROJECT_PATH + "locationHistory/Location History/Semantic Location History/";
    static final String QUERY_PATH = PROJECT_PATH + "queries/";
    static final String[] MONTHS = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER", };

    public static void main(String[] args) throws IOException {
        for (String month : MONTHS)
        {
            readJSONFile(LOCATION_HISTORY_PATH + String.format("2017/2017_%s.json", month));
        }
        generateASQuery(1);
        generatePVQueries(1);

        placeVisits = new ArrayList<>();
        activitySegments = new ArrayList<>();

        for (String month : MONTHS)
        {
            readJSONFile(LOCATION_HISTORY_PATH + String.format("2016/2016_%s.json", month));
        }
        generateASQuery(2);
        generatePVQueries(2);
        generatePQuery();
    }

    private static void readJSONFile(String filePath)
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filePath))
        {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONArray timelineObjects = (JSONArray) obj.get("timelineObjects");

            timelineObjects.forEach( temp -> parseTimelineObject( (JSONObject) temp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseTimelineObject(JSONObject timelineObjects)
    {
        if (timelineObjects.containsKey("placeVisit"))
        {
            JSONObject placeVisitObject = (JSONObject) timelineObjects.get("placeVisit");
            parsePlaceVisitObject(placeVisitObject);
        }
        if (timelineObjects.containsKey("activitySegment"))
        {
            JSONObject activitySegmentObject = (JSONObject) timelineObjects.get("activitySegment");
            parseActivitySegmentObject(activitySegmentObject);
        }
    }
    private static void parsePlaceVisitObject(JSONObject placeVisitObject)
    {
        JSONObject locationObject = (JSONObject) placeVisitObject.get("location");
        JSONObject durationObject = (JSONObject) placeVisitObject.get("duration");

        String address = locationObject.containsKey("address") ? (String) locationObject.get("address") : "UNKNOWN";
        String name = locationObject.containsKey("name") ? (String) locationObject.get("name") : "UNKNOWN";
        int lat = 0;
        int lon = 0;
        long startTime = 0L;
        long endTime = 0L;

        if (locationObject.containsKey("latitudeE7") && locationObject.containsKey("longitudeE7"))
        {
            lat = Math.toIntExact((long) locationObject.get("latitudeE7"));
            lon = Math.toIntExact((long) locationObject.get("longitudeE7"));
        }
        if (durationObject != null)
        {
            if (durationObject.containsKey("startTimestampMs") && durationObject.containsKey("endTimestampMs"))
            {
                startTime = Long.valueOf((String)durationObject.get("startTimestampMs"));
                endTime = Long.valueOf((String)durationObject.get("endTimestampMs"));
            }
        }

        Place place = new Place(placeDbid, address, name);
        Boolean exists = false;
        for (Place listPlace : places)
        {
            if (listPlace.placeEquals(place))
            {
                exists = true;
                place = listPlace;
                placeDbid--;
                break;
            }
        }
        if (!exists)
        {
            places.add(place);
        }
        PlaceVisit placeVisit = new PlaceVisit(placeVisitDbid, place, lat, lon, startTime, endTime);
        placeVisits.add(placeVisit);
        placeVisitDbid++;
        placeDbid++;
    }

    private static void parseActivitySegmentObject(JSONObject activitySegmentObject)
    {
        JSONObject startLocationObject = (JSONObject) activitySegmentObject.get("startLocation");
        JSONObject endLocationObject = (JSONObject) activitySegmentObject.get("endLocation");
        JSONObject durationObject = (JSONObject) activitySegmentObject.get("duration");

        String activityType = activitySegmentObject.containsKey("activityType") ? (String) activitySegmentObject.get("activityType") : "UNKNOWN";
        int distance = activitySegmentObject.containsKey("distance") ? Math.toIntExact((long) activitySegmentObject.get("distance")) : 0;
        int startLat = 0;
        int startLon = 0;
        int endLat = 0;
        int endLon = 0;
        long startTime = 0L;
        long endTime = 0L;

        if (startLocationObject != null)
        {
            if (startLocationObject.containsKey("latitudeE7") && startLocationObject.containsKey("longitudeE7"))
            {
                startLat = Math.toIntExact((long) startLocationObject.get("latitudeE7"));
                startLon = Math.toIntExact((long) startLocationObject.get("longitudeE7"));
            }
        }
        if (endLocationObject != null)
        {
            if (endLocationObject.containsKey("latitudeE7") && endLocationObject.containsKey("longitudeE7"))
            {
                endLat = Math.toIntExact((long) endLocationObject.get("latitudeE7"));
                endLon = Math.toIntExact((long) endLocationObject.get("longitudeE7"));
            }
        }
        if (durationObject != null)
        {
            if (durationObject.containsKey("startTimestampMs") && durationObject.containsKey("endTimestampMs"))
            {
                startTime = Long.valueOf((String)durationObject.get("startTimestampMs"));
                endTime = Long.valueOf((String)durationObject.get("endTimestampMs"));
            }
        }

        ActivitySegment activitySegment = new ActivitySegment(activitySegmentDbid, activityType, startLat, startLon, endLat, endLon, distance, startTime, endTime);
        activitySegments.add(activitySegment);
        activitySegmentDbid++;
    }

    private static void generateASQuery(int userDbid) throws IOException {
        Path file = Paths.get(QUERY_PATH + String.format("raw_activity_segment_q%d.sql", userDbid));
        List<String> lines = new ArrayList<>();
        lines.add("INSERT INTO raw_activity_segment (id, user_id, activity_type, start_latitude, start_longitude, end_latitude, end_longitude, distance, start_time, end_time) VALUES ");

        for (ActivitySegment activitySegment : activitySegments)
        {
            lines.add(String.format("('%d', '%d', '%s', '%d', '%d', '%d', '%d', '%d', '%d', '%d'), ",
                    activitySegment.getDbid(), userDbid, activitySegment.getActivityType(), activitySegment.getStartLat(), activitySegment.getStartLon(), activitySegment.getEndLat(), activitySegment.getEndLon(), activitySegment.getDistance(), activitySegment.getStartTime(), activitySegment.getEndTime()));
        }

        Files.write(file, lines, StandardCharsets.UTF_8);
    }

    private static void generatePVQueries(int userDbid) throws IOException {
        Path rpvFile = Paths.get(QUERY_PATH + String.format("raw_place_visit_q%d.sql", userDbid));
        Path ptrpvFile = Paths.get(QUERY_PATH + String.format("place_to_rawplacevisits_q%d.sql", userDbid));
        List<String> rpvLines = new ArrayList<>();
        List<String> ptrpvLines = new ArrayList<>();
        rpvLines.add("INSERT INTO raw_place_visit (id, user_id, latitude, longitude, start_time, end_time) VALUES ");
        ptrpvLines.add("INSERT INTO place_to_rawplacevisits (place_id, rawplacevisit_id) VALUES ");


        for (PlaceVisit placeVisit : placeVisits)
        {
            rpvLines.add(String.format("('%d', '%d', '%d', '%d', '%d', '%d'), ",
                    placeVisit.getDbid(), userDbid, placeVisit.getLat(), placeVisit.getLon(), placeVisit.getStartTime(), placeVisit.getEndTime()));
            ptrpvLines.add(String.format("('%d', '%d'), ",
                    placeVisit.getPlace().getDbid(), placeVisit.getDbid()));
        }

        Files.write(rpvFile, rpvLines, StandardCharsets.UTF_8);
        Files.write(ptrpvFile, ptrpvLines, StandardCharsets.UTF_8);
    }

    private static void generatePQuery() throws IOException {
        Path pFile = Paths.get(QUERY_PATH + "place_q.sql");
        List<String> pLines = new ArrayList<>();
        pLines.add("INSERT INTO place (id, address, name) VALUES ");
        for (Place place : places)
        {
            pLines.add(String.format("('%d', '%s', '%s'), ",
                    place.getDbid(), place.getAddress(), place.getName()));
        }
        Files.write(pFile, pLines, StandardCharsets.UTF_8);
    }
}

