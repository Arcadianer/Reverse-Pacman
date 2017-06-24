/**
 * Created by handg on 24.06.2017.
 */
public class Weather_Condition
{
    //Basic Weather
    private double cloud_coverage;//IN Percentage between 0.0 and 1.0
    private boolean fog;
    private int windspeed_level;//Level 0=Calm Level 1=moderate  Level 2= strong
    private int swell_level;//Level 0=Flat  Level 1=Small waves  Level 2= White caps Level 3= large swells
    private int rain_level;//Level 0=Dry  Level 1=light showers  Level 2= steady rain Level 3= heavy rain

    //Advanced Weather

    private int beaufort_scale;
    //See http://www.metoffice.gov.uk/guide/weather/marine/beaufort-scale


    //FINAL ATTRIBUTES

    public static final double CLOUD_COVERAGE_SUNNY=0.0;
    public static final double CLOUD_COVERAGE_PARTLY_CLOUDY=0.5;
    public static final double CLOUD_COVERAGE_CLOUDY=1.0;
    public static final int WINDSPEED_CALM=0;
    public static final int WINDSPEED_MODERATE=1;
    public static final int WINDSPEED_STRONG=2;
    public static final int SWELL_FLAT=0;
    public static final int SWELL_SMALL_WAVES=1;
    public static final int SWELL_WHITE_CAPS=2;
    public static final int SWELL_LARGE_SWELLS=3;
    public static final int RAIN_DRY=0;
    public static final int RAIN_LIGHT_SHOWERS=1;
    public static final int RAIN_STEADY_RAIN=2;
    public static final int RAIN_HEAVY_RAIN=3;

}
