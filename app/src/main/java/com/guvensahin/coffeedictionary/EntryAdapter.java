package com.guvensahin.coffeedictionary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class EntryAdapter extends ArrayAdapter<Entry> {
    private ArrayList<Entry> items;
    private ArrayList<Entry> originalItems;

    public int resource;

    public EntryAdapter(Context context, int resource, ArrayList<Entry> items) {
        super(context, resource, items);

        this.resource = resource;
        this.items = items;

        this.originalItems = new ArrayList<Entry>();
        this.originalItems.addAll(items);
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


        holder.name = entry.name;
        holder.viewName.setText(entry.name);

        return convertView;
    }

    private class ViewHolder {

        public TextView viewName;

        public String name;
    }

    public void filter(String queryText) {
        queryText = queryText.toLowerCase(Locale.getDefault());


        items.clear();

        if (queryText.length() == 0)
        {
            items.addAll(originalItems);
        }
        else
        {
            for (Entry entry : originalItems)
            {
                if (entry.name.toLowerCase(Locale.getDefault()).contains(queryText))
                {
                    items.add(entry);
                }
            }
        }

        notifyDataSetChanged();
    }
}


