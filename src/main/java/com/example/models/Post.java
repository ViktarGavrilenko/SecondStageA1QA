package com.example.models;

import aquality.selenium.core.logging.Logger;

public class Post {
    public int userId;
    public int id;
    public String title;
    public String body;

    public int getId() {
        return id;
    }

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
            Logger.getInstance().error(String.format("Compared userIdes %s and %s are not equal", userId, post.userId));
        }
        if (!title.equals(post.title)) {
            Logger.getInstance().error(String.format("Compared titles %s and %s are not equal", title, post.title));
        }
        if (!body.equals(post.body)) {
            Logger.getInstance().error(String.format("Compared bodies %s and %s are not equal", body, post.body));
        }

        return userId == post.userId && title.equals(post.title) && body.equals(post.body);
    }
}