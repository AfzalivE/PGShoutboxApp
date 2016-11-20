package com.afzaln.pgshoutbox.postlist;

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
public class PostListFragment extends BaseFragment<PostListPresenter, PostListContract.View> implements PostListContract.View {
    private PostListPresenter presenter;

    @BindView(R.id.post_list)
    RecyclerView postListV;

    private PostClickListener itemClickListener = postId -> Toast.makeText(getContext(), "Post " + postId + " clicked", Toast.LENGTH_SHORT).show();
    private PostAdapter postAdapter;

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
        postAdapter = new PostAdapter(itemClickListener);

        postListV.setLayoutManager(new LinearLayoutManager(getContext()));
        postListV.setAdapter(postAdapter);
    }

    // View Contract methods

    @Override
    public void addButtonClicked() {
        Toast.makeText(getContext(), "Add menu button clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String errorMsg) {
        Snackbar.make(getView(), errorMsg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessages(ShoutboxData shoutboxData) {
        postAdapter.setMessages(shoutboxData.shouts);
    }

    @Override
    public void showProgressBar(boolean show) {
        // show progress bar here ...
    }

    // Internal methods

    // Overridden base methods

    @Override
    protected PresenterFactory<PostListPresenter> getPresenterFactory() {
        return new PostListPresenterFactory();
    }

    @Override
    protected void onPresenterPrepared(PostListPresenter presenter) {
        this.presenter = presenter;
        // load initial data from presenter here ...
        presenter.loadPosts();
    }

    // static creation method with layout

    public static PostListFragment newInstance() {
        PostListFragment fragment = new PostListFragment();
        // This layout is inflated in BaseFragment
        fragment.setLayout(R.layout.postlist_fragment);

        return fragment;
    }
}
