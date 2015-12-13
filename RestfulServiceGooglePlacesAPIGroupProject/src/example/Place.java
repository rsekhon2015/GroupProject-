package example;

/**
 * Created by Ranjit on 12/12/2015.
 */
import java.util.Collections;
import java.util.Set;

public class Place {
    public String formatted_phone_number;

    //formatted_phone_number

    public static class Formatted_Phone_Number {

        private Formatted_Phone_Number formatted_phone_number;

        public Formatted_Phone_Number getFormatted_Phone_Number( ) {
            return this.formatted_phone_number;
        }

        @Override
        public String toString( ) {
            return this.getFormatted_Phone_Number().toString();
        }

    }


    public static class Icon {

        private Icon icon;

        public Icon getIcon( ) {
            return this.icon;
        }

        @Override
        public String toString( ) {
            return this.getIcon().toString( );
        }

    }

    public static class Geometry {

        private Location location;

        public Location getLocation( ) {
            return this.location;
        }

        @Override
        public String toString( ) {
            return this.getLocation( ).toString( );
        }

    }

    public static class Location {

        private float lat;

        private float lng;

        public float getLat( ) {
            return this.lat;
        }

        public float getLng( ) {
            return this.lng;
        }

        @Override
        public String toString( ) {
            return this.getLat( ) + ", " + this.getLng( );
        }

    }

    String formattedAddress;

    private Geometry geometry;

    public String icon;

    private String id;

    public String name;

    private String placeId;

    private Float rating;

    public String reference;

    private Set<String> types = Collections.emptySet( );

    private String url;

    private String vicinity;

    public String getFormattedAddress( ) {
        return this.formattedAddress;
    }

    public Geometry getGeometry( ) {
        return this.geometry;
    }

    public String getIcon( ) {
        return this.icon;
    }

    @Deprecated
    public String getId( ) {
        return this.id;
    }

    public String getName( ) {
        return this.name;
    }

    public String getPlaceId( ) {
        return this.placeId;
    }

    public Float getRating( ) {
        return this.rating;
    }

    @Deprecated
    public String getReference( ) {
        return this.reference;
    }

    public Set<String> getTypes( ) {
        return this.types;
    }

    public String getUrl( ) {
        return this.url;
    }

    public String getVicinity( ) {
        return this.vicinity;
    }

}
