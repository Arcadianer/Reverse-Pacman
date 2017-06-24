import java.sql.Time;
import java.sql.Date;

/**
 * Created by handg on 24.06.2017.
 */
public class Trip {
    //Basic Trip Telemetry
    private Date trip_date;
    private Time log_start_time;
    private String notes;
    //Boat Information
    private String boat_name;
    private int people_on_board;
    private int maximum_boat_capasity;
    //Personal Info
    private String guide_name;
    private String skipper_name;
    private String[] volunteer_names;
    //Weather Info
    private Weather_Condition start_weather;





    //TRIP START
    private Time boat_launch_time;
    double gps_start_latitude;
    double gps_start_longitude;

    //TRIP TIME LINE


}
