package com.example.android.xjournal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private int mNumberItems;
    public JournalAdapter(int numberItems) {
        mNumberItems = numberItems;
    }

    @Override
    public JournalViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        JournalViewHolder viewHolder = new JournalViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(JournalViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class JournalViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;

        public JournalViewHolder(View itemView) {
            super(itemView);

            titleText = (TextView) itemView.findViewById(R.id.title);
        }
        void bind(int listIndex) {
            titleText.setText(String.valueOf(listIndex));
        }
    }
}
