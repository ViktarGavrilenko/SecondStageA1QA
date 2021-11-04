package com.example.models;

import aquality.selenium.core.logging.Logger;

public class Company {
    public String name;
    public String catchPhrase;
    public String bs;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Company company = (Company) obj;

        if (!name.equals(company.name)) {
            Logger.getInstance().error("Names are not equal");
        }
        if (!catchPhrase.equals(company.catchPhrase)) {
            Logger.getInstance().error("CatchPhrases are not equal");
        }
        if (!bs.equals(company.bs)) {
            Logger.getInstance().error("Bs are not equal");
        }

        return name.equals(company.name) && catchPhrase.equals(company.catchPhrase) && bs.equals(company.bs);
    }
}