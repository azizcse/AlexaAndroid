package com.willblaschko.android.avs.actions.adapter;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.willblaschko.android.avs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author will on 5/30/2016.
 */

public class ActionFragmentAdapter extends RecyclerView.Adapter<ActionFragmentAdapter.ActionFragmentViewHolder>{

    List<ActionFragmentItem> items = new ArrayList<>();

    public ActionFragmentAdapter(List<ActionFragmentItem> items){
        this.items = items;
    }

    @Override
    public ActionFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_action_fragment, parent, false);
        return new ActionFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActionFragmentViewHolder holder, int position) {
        ActionFragmentItem item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getIconResource());
        holder.itemView.setOnClickListener(item.getClickListener());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ActionFragmentViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView icon;

        public ActionFragmentViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }

    public static class ActionFragmentItem{
        String title;
        int iconResource;
        View.OnClickListener clickListener;
        public ActionFragmentItem(String title, @DrawableRes int iconResource, View.OnClickListener clickListener){
            this.title = title;
            this.iconResource = iconResource;
            this.clickListener = clickListener;
        }

        public String getTitle() {
            return title;
        }

        public int getIconResource() {
            return iconResource;
        }

        public View.OnClickListener getClickListener() {
            return clickListener;
        }
    }
}
