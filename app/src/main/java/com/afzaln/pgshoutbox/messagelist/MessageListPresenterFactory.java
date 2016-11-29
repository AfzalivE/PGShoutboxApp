package com.afzaln.pgshoutbox.messagelist;

import android.content.Context;

import com.afzaln.pgshoutbox.Injection;
import com.afzaln.pgshoutbox.util.PresenterFactory;

/**
 * Created by afzal on 2016-11-19.
 */
class MessageListPresenterFactory extends PresenterFactory<MessageListPresenter> {
    @Override
    public MessageListPresenter create(Context context) {
        return new MessageListPresenter(
                Injection.provideDataRepository(context)
        );
    }
}
