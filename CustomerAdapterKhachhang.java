package com.example.quanlybanhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomerAdapterKhachhang extends BaseAdapter {
    String []name;
    String []sdt;
    String []diachi;
    LayoutInflater inflater;
    Context context;
    public CustomerAdapterKhachhang( String []name,
    String []sdt,
    String []diachi,Context context)
    {
        this.name=name;
        this.sdt=sdt;
        this.diachi=diachi;
        this.context=context;
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
            convertView = inflater.inflate(R.layout.khachhang_item, parent, false);
        }
        TextView Name=(TextView) convertView.findViewById(R.id.name);
        TextView Sdt=(TextView) convertView.findViewById(R.id.sdt);
        TextView Diachi=(TextView) convertView.findViewById(R.id.diachi);
        Name.setText(name[position]);
        Sdt.setText(sdt[position]);
        Diachi.setText(diachi[position]);
        return convertView;
    }
}
