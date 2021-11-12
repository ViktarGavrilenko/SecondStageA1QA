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
            Logger.getInstance().error(String.format("Compared company names %s and %s are not equal", name,
                    company.name));
        }
        if (!catchPhrase.equals(company.catchPhrase)) {
            Logger.getInstance().error(String.format("Compared company catchPhrases %s and %s are not equal",
                    catchPhrase, company.catchPhrase));
        }
        if (!bs.equals(company.bs)) {
            Logger.getInstance().error(String.format("Compared company bs %s and %s are not equal", bs, company.bs));
        }

        return name.equals(company.name) && catchPhrase.equals(company.catchPhrase) && bs.equals(company.bs);
    }
}