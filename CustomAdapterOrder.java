package com.example.quanlybanhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CustomAdapterOrder extends BaseAdapter {

    String []Madon;
    String []Ngaydat;
    String []Trangthai;
    String []tongdon;
    Context context;
    LayoutInflater inflater;
    public CustomAdapterOrder(String []madon, String []ngaydat, String []trangthai, String[]tongdon,Context context)
    {
        this.Madon=madon;
        this.Ngaydat=ngaydat;
        this.Trangthai=trangthai;
        this.tongdon=tongdon;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    public String[]getMadon()
    {
        return Madon;
    }
    public String[]getTongdon()
    {
        return tongdon;
    }
    @Override
    public int getCount() {
        return Madon.length;
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
            convertView = inflater.inflate(R.layout.list_order_item, parent, false);
        }
        String trangthai="";
        if(Trangthai[position].equals("Waiting"))
        {
            trangthai="Chờ xác nhận";
        }
        if(Trangthai[position].equals("Tranferent"))
        {
            trangthai="Đang giao hàng";
        }
        if(Trangthai[position].equals("Done"))
        {
            trangthai="Đã nhận hàng";
        }

        // Convert the Date object to the desired format
        TextView tmadon=convertView.findViewById(R.id.Madon);
        TextView tngaydat=convertView.findViewById(R.id.ngaydat);
        TextView ttrangthai=convertView.findViewById(R.id.trangthai);
        TextView ttongtien=convertView.findViewById(R.id.tongtien);
        String formattedDate = formatDate(Ngaydat[position]);

        tmadon.setText("Đơn hàng: #DH"+Madon[position]);
        tngaydat.setText("Ngày đặt: 11/9/2024");
        ttrangthai.setText(trangthai);
        ttongtien.setText(formatCurrency(Integer.parseInt(tongdon[position])));

        return convertView;
    }
    private String formatCurrency(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + "đ";
    }
    private String formatDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            Date date = inputFormat.parse(dateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("d/M/yyyy");
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return dateString; // Return the original string if parsing fails
        }
    }

}
