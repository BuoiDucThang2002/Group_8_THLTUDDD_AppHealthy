package com.example.quanlybanhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerAdapterDetailOrder extends BaseAdapter {
    String []name;
    String []gia;
    String []soluong;
    String []thanhtien;
    String []anh;
    LayoutInflater inflater;
    Context context;
    public CustomerAdapterDetailOrder(String []name,String []gia, String []soluong,String []thanhtien,String[]anh,Context context)
    {
        this.name=name;
        this.gia=gia;
        this.soluong=soluong;
        this.anh=anh;
        this.thanhtien=thanhtien;
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
            convertView = inflater.inflate(R.layout.list_detail_order, parent, false);
        }
        TextView Name=convertView.findViewById(R.id.tenSanPham);
        TextView Gia=convertView.findViewById(R.id.gia);
        TextView Soluong=convertView.findViewById(R.id.soluong);
        TextView Thanhtien=convertView.findViewById(R.id.thanhtien);
        ImageView Anh=(ImageView) convertView.findViewById(R.id.anhsp);
        int imageResId = convertView.getContext().getResources().getIdentifier(anh[position], "drawable", convertView.getContext().getPackageName());
        if (imageResId != 0) {
            Anh.setImageResource(imageResId);
        } else {
            // Nếu không tìm thấy tài nguyên, có thể đặt ảnh mặc định
            Anh.setImageResource(R.drawable.thitlon);  // default_image là ảnh mặc định nếu không tìm thấy
        }
        Name.setText(name[position]);
        Gia.setText(gia[position]);
        Soluong.setText(soluong[position]);
        Thanhtien.setText(thanhtien[position]);

        return convertView;
    }
}
