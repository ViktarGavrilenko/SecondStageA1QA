package com.example.models;

import aquality.selenium.core.logging.Logger;

public class Post {
    public int userId;
    public int id;
    public String title;
    public String body;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Post post = (Post) obj;

        if (userId != post.userId) {
            Logger.getInstance().error("UserIdes are not equal");
        }
        if (!title.equals(post.title)) {
            Logger.getInstance().error("Titles are not equal");
        }
        if (!body.equals(post.body)) {
            Logger.getInstance().error("Bodies are not equal");
        }

        return userId == post.userId && title.equals(post.title) && body.equals(post.body);
    }
}