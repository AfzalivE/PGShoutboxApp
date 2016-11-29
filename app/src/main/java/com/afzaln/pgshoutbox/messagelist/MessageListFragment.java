package com.afzaln.pgshoutbox.messagelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.afzaln.pgshoutbox.R;
import com.afzaln.pgshoutbox.data.models.ShoutboxData;
import com.afzaln.pgshoutbox.util.BaseFragment;
import com.afzaln.pgshoutbox.util.PresenterFactory;

import butterknife.BindView;

/**
 * Created by afzal on 2016-11-19.
 */
public class MessageListFragment extends BaseFragment<MessageListPresenter, MessageListContract.View> implements MessageListContract.View {
    private MessageListPresenter presenter;

    @BindView(R.id.message_list)
    RecyclerView messageListV;

    private MessageClickListener itemClickListener = shoutId -> Toast.makeText(getContext(), "Shout " + shoutId + " clicked", Toast.LENGTH_SHORT).show();
    private MessagesAdapter shoutsAdapter;

    // Initializer methods

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get any intent extras here ...
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize adapters etc here ...
        shoutsAdapter = new MessagesAdapter(itemClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        messageListV.setLayoutManager(layoutManager);
        messageListV.setAdapter(shoutsAdapter);
    }

    // View Contract methods

    @Override
    public void addButtonClicked() {
        Toast.makeText(getContext(), "Add menu button clicked", Toast.LENGTH_SHORT).show();
        presenter.postMessage("Test 3");
    }

    @Override
    public void showError(String errorMsg) {
        Snackbar.make(getView(), errorMsg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessages(ShoutboxData shoutboxData) {
        shoutsAdapter.setMessages(shoutboxData.shouts);
    }

    @Override
    public void showProgressBar(boolean show) {
        // show progress bar here ...
    }

    // Internal methods

    // Overridden base methods

    @Override
    protected PresenterFactory<MessageListPresenter> getPresenterFactory() {
        return new MessageListPresenterFactory();
    }

    @Override
    protected void onPresenterPrepared(MessageListPresenter presenter) {
        this.presenter = presenter;
        // load initial data from presenter here ...
        presenter.loadMessages();
    }

    // static creation method with layout

    public static MessageListFragment newInstance() {
        MessageListFragment fragment = new MessageListFragment();
        // This layout is inflated in BaseFragment
        fragment.setLayout(R.layout.messagelist_fragment);

        return fragment;
    }
}
