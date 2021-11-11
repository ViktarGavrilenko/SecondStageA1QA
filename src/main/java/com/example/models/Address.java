package com.example.models;

import aquality.selenium.core.logging.Logger;

public class Address {
    public String street;
    public String suite;
    public String city;
    public String zipcode;
    public Geo geo;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Address address = (Address) obj;

        if (!street.equals(address.street)) {
            Logger.getInstance().error("Compared streets " + street + " and " + address.street + " are not equal");
        }
        if (!suite.equals(address.suite)) {
            Logger.getInstance().error("Compared suites " + suite + " and " + address.suite + " are not equal");
        }
        if (!city.equals(address.city)) {
            Logger.getInstance().error("Compared cities " + city + " and " + address.city + " are not equal");
        }
        if (!zipcode.equals(address.zipcode)) {
            Logger.getInstance().error("Compared zipcodes " + zipcode + " and " + address.zipcode + " are not equal");
        }
        if (!geo.equals(address.geo)) {
            Logger.getInstance().error("Geos are not equal");
        }

        return street.equals(address.street) && suite.equals(address.suite) && city.equals(address.city) &&
                zipcode.equals(address.zipcode) && geo.equals(address.geo);
    }
}