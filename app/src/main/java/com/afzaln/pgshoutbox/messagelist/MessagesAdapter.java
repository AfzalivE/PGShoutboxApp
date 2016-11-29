package com.afzaln.pgshoutbox.messagelist;

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
class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageVH> {
    private List<Shout> messages;
    private final MessageClickListener itemClickListener;

    MessagesAdapter(MessageClickListener itemClickListener) {
        messages = new ArrayList<>();
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MessageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        return new MessageVH(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(MessageVH holder, int position) {
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

    static class MessageVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final MessageClickListener itemClickListener;
        private Shout shout;

        @BindView(R.id.title)
        TextView titleV;

        @BindView(R.id.subtitle)
        TextView subtitleV;

        MessageVH(View itemView, MessageClickListener itemClickListener) {
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
