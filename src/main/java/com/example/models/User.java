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
            Logger.getInstance().error("Names are not equal");
        }
        if (!username.equals(user.username)) {
            Logger.getInstance().error("Usernames are not equal");
        }
        if (!email.equals(user.email)) {
            Logger.getInstance().error("Emails are not equal");
        }
        if (!address.equals(user.address)) {
            Logger.getInstance().error("Addresses are not equal");
        }
        if (!phone.equals(user.phone)) {
            Logger.getInstance().error("Phones are not equal");
        }
        if (!website.equals(user.website)) {
            Logger.getInstance().error("Websites are not equal");
        }
        if (!company.equals(user.company)) {
            Logger.getInstance().error("Companies are not equal");
        }

        return name.equals(user.name) && username.equals(user.username) && email.equals(user.email) &&
                address.equals(user.address) && phone.equals(user.phone) && website.equals(user.website) &&
                company.equals(user.company);
    }
}