package com.example.quanlybanhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapterProfile extends BaseAdapter {
    String [] name;
    LayoutInflater inflater;
    Context context;
public CustomAdapterProfile (String []name, Context context)
{
    this.context=context;
    this.name=name;
    inflater=LayoutInflater.from(context);
}
    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_menu, parent, false);
        }
        TextView Name=(TextView) convertView.findViewById(R.id.name);
        Name.setText(name[position]);
        return convertView;
    }
}
