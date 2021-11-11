package com.example.utils;

import com.example.models.Post;

import java.util.List;

public class CollectionUtils {
    public static boolean isListSortedById(List<Post> posts) {
        int index = posts.get(0).id;
        for (int i = 1; i < posts.size(); i++) {
            if (index > posts.get(i).id) {
                return false;
            } else {
                index = posts.get(i).id;
            }
        }
        return true;
    }
}