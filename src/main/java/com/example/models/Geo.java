package com.example.models;

import aquality.selenium.core.logging.Logger;

public class Geo {
    public String lat;
    public String lng;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Geo geo = (Geo) obj;

        if (!lat.equals(geo.lat)) {
            Logger.getInstance().error("Compared latitudes " + lat + " and " + geo.lat + " are not equal");
        }
        if (!lng.equals(geo.lng)) {
            Logger.getInstance().error("Compared longitudes " + lng + " and " + geo.lng + " are not equal");
        }

        return lat.equals(geo.lat) && lng.equals(geo.lng);
    }
}