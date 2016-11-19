package com.afzaln.pgshoutbox.data.models;

/**
 * Created by afzal on 2016-11-19.
 */

public class Post {
    public int userId;
    public int id;
    public String title;
    public String body;

    public static Post from(int userId, int id, String title, String body) {
        Post post = new Post();
        post.userId = userId;
        post.id = id;
        post.title = title;
        post.body = body;
        return post;
    }
}
