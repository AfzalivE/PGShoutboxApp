package com.afzaln.pgshoutbox.messagelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.afzaln.pgshoutbox.Injection;
import com.afzaln.pgshoutbox.R;
import com.afzaln.pgshoutbox.data.source.preference.PreferenceProvider;
import com.afzaln.pgshoutbox.util.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by afzal on 2016-11-19.
 */

public class MessageListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MessageListFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        ButterKnife.bind(this);

        // do things with preferences
        PreferenceProvider preferenceProvider = Injection.providePreferenceProvider(this);

        // TODO check if logged in, if not, finish this activity and show LoginActivity

        fragment = (MessageListFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        showFragment();
    }

    private void showFragment() {
        if (fragment == null) {
            fragment = MessageListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

        toolbar.inflateMenu(R.menu.listscreen_menu);
        Toolbar.OnMenuItemClickListener menuListener = item -> {
            switch (item.getItemId()) {
                case R.id.menu_add:
                    fragment.addButtonClicked();
                    break;
            }
            return true;
        };
        toolbar.setOnMenuItemClickListener(menuListener);
    }
}
