package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.quanlybanhang.database.CreateDatabase;

import java.util.ArrayList;

public class SearchProduct extends AppCompatActivity implements CustomAdapterListProduct.OnBuyClickListener,CustomAdapterListProduct.OnLoveClickListener {

    EditText timkiem;
    ListView list;
    ImageView emty;
    SQLiteDatabase database;
    ImageButton back;
    int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        timkiem=findViewById(R.id.tk);
        list=findViewById(R.id.list);
        emty=findViewById(R.id.emptyImage);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });
        CreateDatabase createDatabase= new CreateDatabase(this);
        database=createDatabase.open();
        String LayDuLieuTheoLoai="Select * from SANPHAM";

        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("type");
        int id=bundle.getInt("id", 0);
        Id=id;
        Cursor cursor= database.rawQuery(LayDuLieuTheoLoai,null);
        ArrayList<String> tenList = new ArrayList<>();
        ArrayList<String> giaList = new ArrayList<>();
        ArrayList<Integer> maList = new ArrayList<>();
        ArrayList<String> Anh = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int ma=cursor.getInt(0);
                maList.add(ma);
                String ten = cursor.getString(1); // Lấy giá trị cột đầu tiên
                tenList.add(ten);
                String gia=cursor.getString(4);
                giaList.add(gia);
                Anh.add(cursor.getString(6));
            } while (cursor.moveToNext());
            cursor.close();
        }
        CustomAdapterListProduct customAdapterListProduct= new CustomAdapterListProduct(this,tenList.toArray(new String[0]),giaList.toArray(new String[0]),Anh.toArray(new String[0]), this,this,id);
        list.setAdapter(customAdapterListProduct);
        if(tenList.isEmpty())
        {
            emty.setVisibility(View.VISIBLE);  // Hiển thị hình ảnh trống
            list.setVisibility(View.GONE);
        }else{
            list.setVisibility(View.VISIBLE);  // Hiển thị hình ảnh trống
            emty.setVisibility(View.GONE);
        }
        timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
     public void Search(String query)
    {
        CreateDatabase createDatabase= new CreateDatabase(this);//Tạo đối tượng để kết nối csdl
        database=createDatabase.open();
        String LayDuLieuTheoLoai="Select * from SANPHAM where TenSP LIKE ?";
        Cursor cursor= database.rawQuery(LayDuLieuTheoLoai,new String[]{"%" + query + "%"});
        ArrayList<String> tenList = new ArrayList<>(); //Tạo danh sách để lưu chuỗi tenList
        ArrayList<String> giaList = new ArrayList<>();
        ArrayList<Integer> maList = new ArrayList<>();
        ArrayList<String> Anh = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int ma=cursor.getInt(0);
                maList.add(ma);
                String ten = cursor.getString(1); // Lấy giá trị cột đầu tiên
                tenList.add(ten);
                String gia=cursor.getString(4);
                giaList.add(gia);
                Anh.add(cursor.getString(6));

            } while (cursor.moveToNext());
            cursor.close();
        }
        CustomAdapterListProduct customAdapterListProduct= new CustomAdapterListProduct(this,tenList.toArray(new String[0]),giaList.toArray(new String[0]),Anh.toArray(new String[0]), this,this,Id);
        list.setAdapter(customAdapterListProduct);
        if(tenList.isEmpty())
        {
            emty.setVisibility(View.VISIBLE);  // Hiển thị hình ảnh trống
            list.setVisibility(View.GONE);
        }else{
            list.setVisibility(View.VISIBLE);  // Hiển thị hình ảnh trống
            emty.setVisibility(View.GONE);
        }
    }
    @Override
    public void onBuyClicked(int position) {
        // Xử lý sự kiện khi nhấn nút "Buy"

        // Mở Activity chi tiết sản phẩm
        Intent intent = new Intent(this, DetailProduct.class);
        intent.putExtra("product_position", position);
        startActivity(intent);
    }
    @Override
    public void onLoveClicked(int position,boolean isLoved){
           // Mở Activity chi tiết sản phẩm
    }
}
