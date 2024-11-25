package com.example.quanlybanhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class CustomAdapterDoanhThu extends BaseAdapter {
    String []maDon;
    String []trangThai;
    String []tongGia;
    String []ngayDat;
    String []hotenKH;
    String []sdtKH;
    String []diaChi;
    Context context;
    LayoutInflater inflater;
    public CustomAdapterDoanhThu(String []maDon,
    String []trangThai,
    String []tongGia,
    String []ngayDat,
    String []hotenKH,
    String []sdtKH,
    String []diaChi,Context context)
    {
        this.maDon=maDon;
        this.trangThai=trangThai;
        this.tongGia=tongGia;
        this.ngayDat=ngayDat;
        this.hotenKH=hotenKH;
        this.sdtKH=sdtKH;
        this.diaChi=diaChi;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    public String[]getMaDon(){
        return maDon;
    }
    public String[]getTongDon(){
        return tongGia;
    }
    @Override
    public int getCount() {
        return maDon.length;
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
            convertView = inflater.inflate(R.layout.list_donhang_item, parent, false);
        }
        TextView madon=(TextView) convertView.findViewById(R.id.madon);
        TextView trangthai=(TextView) convertView.findViewById(R.id.trangthai);
        TextView tonggia=(TextView) convertView.findViewById(R.id.tonggia);
        TextView ngaydat=(TextView) convertView.findViewById(R.id.ngaydat);
        TextView Khachhang=(TextView) convertView.findViewById(R.id.khachhang);
        TextView Diachi=(TextView) convertView.findViewById(R.id.diachi);
        madon.setText("#DH"+maDon[position]);
        String Trangthai="";
        if(trangThai[position].equals("Waiting"))
        {
            Trangthai="Chờ xác nhận";
        }
        if(trangThai[position].equals("Tranferent"))
        {
            Trangthai="Đang giao hàng";
        }
        if(trangThai[position].equals("Cancel"))
        {
            Trangthai="Đã hủy";
        }
        if(trangThai[position].equals("Done"))
        {
            Trangthai="Đã nhận hàng";
        }
        trangthai.setText(Trangthai);
        tonggia.setText(formatCurrency(Integer.parseInt(tongGia[position])));
        ngaydat.setText("1/1/2002");
        Khachhang.setText(hotenKH[position]+" - "+sdtKH[position]);
        Diachi.setText(diaChi[position]);
        return convertView;
    }
    private String formatCurrency(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + "đ";
    }
}
