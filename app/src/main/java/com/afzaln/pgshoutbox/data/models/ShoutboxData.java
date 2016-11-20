package com.afzaln.pgshoutbox.data.models;

import java.util.List;

/**
 * Created by afzal on 2016-01-02.
 */
public class ShoutboxData {
    public List<Shout> shouts;
    public String sticky;
    public String ajax;
    public List<AopTime> aoptimes;
    public ActiveUsers activeusers;
    public String pmtime;
}