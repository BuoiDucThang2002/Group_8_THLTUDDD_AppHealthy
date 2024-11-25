package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailOrder extends AppCompatActivity {

    TextView madh,hoten,time,diachi;
    ListView listView;
    TextView tienhang,Tongtien;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        madh=findViewById(R.id.madonhang);
        hoten=findViewById(R.id.tenkhachhang);
        time=findViewById(R.id.thoigian);
        diachi=findViewById(R.id.diachi);
        listView=findViewById(R.id.list);
        tienhang=findViewById(R.id.tienhang);
        Tongtien=findViewById(R.id.tongtien);

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        String maDon=bundle.getString("IdDonHang");
        String userId=bundle.getString("IdUser");
        String tongtien=bundle.getString("tongtien");
        Toast.makeText(DetailOrder.this, "Alo: "+userId, Toast.LENGTH_SHORT).show();
        CreateDatabase createDatabase= new CreateDatabase(this);
      database=createDatabase.open();
        Cursor cursor = database.rawQuery(
                "SELECT DONHANG.MaDH, KHACHHANG.HotenKH, KHACHHANG.SdtKH, DONHANG.Ngaydat, DONHANG.Diachi " +
                        "FROM DONHANG " +
                        "INNER JOIN HANGTRONGDON ON DONHANG.MaDH = HANGTRONGDON.MaDH " +
                        "INNER JOIN KHACHHANG ON DONHANG.MaKH = KHACHHANG.MaKH " +
                        "WHERE DONHANG.MaKH = ? AND DONHANG.MaDH = ?",
                new String[]{userId, maDon});
        if (cursor != null && cursor.moveToFirst()) {
            madh.setText("#DH" + maDon);
            hoten.setText(cursor.getString(1) + " - " + cursor.getString(2));
            time.setText(cursor.getString(3));
            diachi.setText(cursor.getString(4));
        } else {
            Toast.makeText(DetailOrder.this, "Error", Toast.LENGTH_SHORT).show();
        }
        ArrayList<String> TenSP = new ArrayList<>();//Lưu trữ TenSP
        ArrayList<String> Gia = new ArrayList<>();
        ArrayList<String> Soluong = new ArrayList<>();
        ArrayList<String> ThanhTien = new ArrayList<>();
        ArrayList<String> Anh = new ArrayList<>();

        Cursor cursor1 = database.rawQuery(
                "SELECT SANPHAM.TenSP,SANPHAM.Gia,HANGTRONGDON.Soluong,DONHANG.GiaDH,SANPHAM.Anh " +
                        "FROM HANGTRONGDON " +
                        "INNER JOIN SANPHAM  ON SANPHAM.MaSP = HANGTRONGDON.MaSP " +
                        "INNER JOIN DONHANG ON DONHANG.MaDH = HANGTRONGDON.MaDH " +
                        "WHERE  DONHANG.MaDH = ?",
                new String[]{maDon});
        if (cursor1 != null && cursor1.moveToFirst()) {
            do {
                String ten=cursor1.getString(0);//Lấy giá trị cột
                TenSP.add(ten);
                String gia = cursor1.getString(1); // Lấy giá trị cột
                Gia.add(gia);
                String soluong=cursor1.getString(2);////Lấy giá trị cột
                Soluong.add(soluong);
                String thanhtien=cursor1.getString(3);////Lấy giá trị cột
                ThanhTien.add(thanhtien);
                Anh.add(cursor1.getString(4));
            } while (cursor1.moveToNext());
        } else {
            Toast.makeText(DetailOrder.this, "Error", Toast.LENGTH_SHORT).show();
        }
        CustomerAdapterDetailOrder customerAdapterDetailOrder= new CustomerAdapterDetailOrder(TenSP.toArray(new String[0]),Gia.toArray(new String[0]),Soluong.toArray(new String[0]),ThanhTien.toArray(new String[0]),Anh.toArray(new String[0]), this);
listView.setAdapter(customerAdapterDetailOrder);
tienhang.setText(formatCurrency(Integer.parseInt(tongtien)));
        Tongtien.setText(formatCurrency(Integer.parseInt(tongtien)));
        
    }
    private String formatCurrency(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + " đ";
    }

