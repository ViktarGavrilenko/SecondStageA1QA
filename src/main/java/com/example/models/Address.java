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
            Logger.getInstance().error(String.format("Compared streets %s and %s are not equal", street, address.street));
        }
        if (!suite.equals(address.suite)) {
            Logger.getInstance().error(String.format("Compared suites %s and %s are not equal", suite, address.suite));
        }
        if (!city.equals(address.city)) {
            Logger.getInstance().error(String.format("Compared cities %s and %s are not equal", city, address.city));
        }
        if (!zipcode.equals(address.zipcode)) {
            Logger.getInstance().error(String.format("Compared zipcodes %s and %s are not equal", zipcode, address.zipcode));
        }
        if (!geo.equals(address.geo)) {
            Logger.getInstance().error("Geos are not equal");
        }

        return street.equals(address.street) && suite.equals(address.suite) && city.equals(address.city) &&
                zipcode.equals(address.zipcode) && geo.equals(address.geo);
    }
}