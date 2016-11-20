package com.afzaln.pgshoutbox.postlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afzaln.pgshoutbox.R;
import com.afzaln.pgshoutbox.data.models.Shout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A custom adapter to use with RecyclerView.
 */
class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostVH> {
    private List<Shout> messages;
    private final PostClickListener itemClickListener;

    PostAdapter(PostClickListener itemClickListener) {
        messages = new ArrayList<>();
        this.itemClickListener = itemClickListener;
    }

    @Override
    public PostVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        return new PostVH(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(PostVH holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    void setMessages(List<Shout> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    static class PostVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final PostClickListener itemClickListener;
        private Shout shout;

        @BindView(R.id.title)
        TextView titleV;

        @BindView(R.id.subtitle)
        TextView subtitleV;

        PostVH(View itemView, PostClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        void bind(Shout shout) {
            this.shout = shout;
            titleV.setText(shout.jsusername);
            subtitleV.setText(shout.message);
        }

        @Override
        public void onClick(View v) {
//            itemClickListener.onClick(post.id);
        }
    }
}
