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
            Logger.getInstance().error("Compared company names " + name + " and " + company.name + " are not equal");
        }
        if (!catchPhrase.equals(company.catchPhrase)) {
            Logger.getInstance().error("Compared company catchPhrases " + catchPhrase + " and " + company.catchPhrase +
                    " are not equal");
        }
        if (!bs.equals(company.bs)) {
            Logger.getInstance().error("Compared company bs " + bs + " and " + company.bs + " are not equal");
        }

        return name.equals(company.name) && catchPhrase.equals(company.catchPhrase) && bs.equals(company.bs);
    }
}