package com.example.databasemodels;

import java.sql.Timestamp;

public class TestTable {
    public String name;
    public int status_id;
    public String method_name;
    public int project_id;
    public int session_id;
    public Timestamp start_time;
    public Timestamp end_time;
    public String env;
    public String browser;
    public int author_id;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        TestTable testTable = (TestTable) obj;
        return name.equals(testTable.name) && status_id == testTable.status_id &&
                method_name.equals(testTable.method_name) && project_id == testTable.project_id &&
                session_id == testTable.session_id && start_time.equals(testTable.start_time) &&
                end_time.equals(testTable.end_time) && env.equals(testTable.env) && browser.equals(testTable.browser) &&
                author_id == testTable.author_id;
    }
}