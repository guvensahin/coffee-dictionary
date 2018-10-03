package com.guvensahin.coffeedictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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


        holder.name = entry.getName();
        holder.viewName.setText(entry.getName());

        return convertView;
    }

    private class ViewHolder {

        public TextView viewName;

        public String name;
    }

    public void filter(String filterName, String filterCategory) {
        filterName = filterName.toLowerCase(Locale.getDefault());
        filterCategory = filterCategory.toLowerCase(Locale.getDefault());

        items.clear();

        // filtre yok ise tüm veri yüklenir
        if (filterName.length() == 0
            && filterCategory.length() == 0)
        {
            items.addAll(originalItems);
        }
        else
        {
            for (Entry entry : originalItems)
            {
                boolean validName = false;
                boolean validCategory = false;

                if (filterName.length() == 0
                    || entry.getName().toLowerCase(Locale.getDefault()).contains(filterName))
                {
                    validName = true;
                }

                if (validName
                    && (filterCategory.length() == 0
                        || entry.category.toLowerCase(Locale.getDefault()).contains(filterCategory)))
                {
                    validCategory = true;
                }

                if (validName && validCategory)
                {
                    items.add(entry);
                }
            }
        }

        notifyDataSetChanged();
    }
}


