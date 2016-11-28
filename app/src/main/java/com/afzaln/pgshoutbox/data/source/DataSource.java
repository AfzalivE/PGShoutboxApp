package com.afzaln.pgshoutbox.data.source;

import com.afzaln.pgshoutbox.data.models.ShoutboxData;

import rx.Observable;

/**
 * Created by afzal on 2016-11-19.
 */
public interface DataSource {
    Observable<Boolean> login(String username, String password);

    Observable<ShoutboxData> postMessage(String message);

    Observable<ShoutboxData> getMessages();
}
