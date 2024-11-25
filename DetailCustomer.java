package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

import com.example.quanlybanhang.database.CreateDatabase;

public class DetailCustomer extends AppCompatActivity {

    EditText ten,sdt;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);
        ten=findViewById(R.id.username);
        sdt=findViewById(R.id.hoten);
        CreateDatabase createDatabase=new CreateDatabase(this);
        database=createDatabase.open();
        String layTaikhoan="Select * from KHACHHANG where MaKH=?";
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        String id=bundle.getString("Id","0");
        Cursor cursor= database.rawQuery(layTaikhoan,new String[]{id});
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            ten.setText(cursor.getString(1));
            sdt.setText(cursor.getString(2));
        }
        ten.setEnabled(false);
        sdt.setEnabled(false);
    }
}
