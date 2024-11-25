package com.example.quanlybanhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class CustomAdapterAdminProduct extends BaseAdapter {
    String []name;
    String []ma;
    String []danhmuc;
    String []gia;
    String []anh;
    Context context;
    LayoutInflater inflater;
    public  CustomAdapterAdminProduct(String []name,
    String []ma,
    String []danhmuc,
    String []gia,
    String []anh,
    Context context
    )
    {
        this.name=name;
     this.ma=ma;
     this.anh=anh;
     this.danhmuc=danhmuc;
     this.gia=gia;
     this.context=context;
     inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return ma.length;
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
            convertView = inflater.inflate(R.layout.list_item_product_admin, parent, false);
        }
        TextView Name=(TextView) convertView.findViewById(R.id.name);
        TextView Ma=(TextView) convertView.findViewById(R.id.ma);
        TextView Gia=(TextView) convertView.findViewById(R.id.gia);
        ImageView Anh=(ImageView) convertView.findViewById(R.id.anhsp);
        int imageResId = convertView.getContext().getResources().getIdentifier(anh[position], "drawable", convertView.getContext().getPackageName());
        if (imageResId != 0) {
            Anh.setImageResource(imageResId);
        } else {
            // Nếu không tìm thấy tài nguyên, có thể đặt ảnh mặc định
            Anh.setImageResource(R.drawable.thitlon);  // default_image là ảnh mặc định nếu không tìm thấy
        }
        Name.setText(name[position]);
        String loai="---";
        if(danhmuc[position].equals("1"))
        {
            loai="Thịt bò";
        }
        if(danhmuc[position].equals("2"))
        {
            loai="Thịt lợn";
        }
        if(danhmuc[position].equals("3"))
        {
            loai="Gia cầm";
        }
        if(danhmuc[position].equals("4"))
        {
            loai="Cá sông";
        }
        if(danhmuc[position].equals("5"))
        {
            loai="Rau xanh";
        }
        if(danhmuc[position].equals("6"))
        {
            loai="Củ quả";
        }
        Ma.setText("SP000"+ma[position]+" - Danh mục: "+loai);
        Gia.setText(formatCurrency(Integer.parseInt(gia[position])));
        return convertView;    }
    private String formatCurrency(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + "đ";
    }
}

