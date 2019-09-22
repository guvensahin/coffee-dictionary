package com.guvensahin.coffeedictionary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.guvensahin.coffeedictionary.Models.Entry;
import com.guvensahin.coffeedictionary.R;

import java.util.ArrayList;

public class EntryAdapter extends ArrayAdapter<Entry> {
    private ArrayList<Entry> items;

    public int resource;

    public EntryAdapter(Context context, int resource, ArrayList<Entry> items) {
        super(context, resource, items);

        this.resource = resource;
        this.items = items;
    }

    @Override
    public int getCount(){
        return items != null ? items.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Entry entry = this.getItem(position);

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(this.resource, null);

        holder = new ViewHolder();
        holder.viewName = (TextView) convertView.findViewById(R.id.label);
        convertView.setTag(holder);


        holder.name = entry.getName();
        holder.viewName.setText(entry.getName());

        return convertView;
    }

    private class ViewHolder {

        public TextView viewName;

        public String name;
    }
}


