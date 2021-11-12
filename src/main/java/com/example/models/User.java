package com.example.models;

import aquality.selenium.core.logging.Logger;

public class User {
    public int id;
    public String name;
    public String username;
    public String email;
    public Address address;
    public String phone;
    public String website;
    public Company company;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        User user = (User) obj;

        if (!name.equals(user.name)) {
            Logger.getInstance().error(String.format("Compared names %s and %s are not equal", name, user.name));
        }
        if (!username.equals(user.username)) {
            Logger.getInstance().error(String.format("Compared usernames %s and %s are not equal", username,
                    user.username));
        }
        if (!email.equals(user.email)) {
            Logger.getInstance().error(String.format("Compared emails %s and %s are not equal", email, user.email));
        }
        if (!address.equals(user.address)) {
            Logger.getInstance().error("Addresses are not equal");
        }
        if (!phone.equals(user.phone)) {
            Logger.getInstance().error(String.format("Compared phones %s and %s are not equal", phone, user.phone));
        }
        if (!website.equals(user.website)) {
            Logger.getInstance().error(String.format("Compared websites %s and %s are not equal", website,
                    user.website));
        }
        if (!company.equals(user.company)) {
            Logger.getInstance().error("Companies are not equal");
        }

        return name.equals(user.name) && username.equals(user.username) && email.equals(user.email) &&
                address.equals(user.address) && phone.equals(user.phone) && website.equals(user.website) &&
                company.equals(user.company);
    }
}