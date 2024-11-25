package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

public class Information extends AppCompatActivity {

    EditText hoten,sodienthoai;
    Button chinhsua;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        chinhsua=findViewById(R.id.btnchinhsua);
        hoten=findViewById(R.id.hoten);
        sodienthoai=findViewById(R.id.username);
        CreateDatabase createDatabase=new CreateDatabase(this);
        database=createDatabase.open();
        String layTaikhoan="Select * from KHACHHANG where MaKH=?";
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        int id=bundle.getInt("Id",0);
        String Id=""+id;
        Cursor cursor= database.rawQuery(layTaikhoan,new String[]{Id});
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            hoten.setText(cursor.getString(1));
            sodienthoai.setText(cursor.getString(2));
        }
        hoten.setEnabled(false);
        sodienthoai.setEnabled(false);
        chinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chinhsua.getText().toString().equalsIgnoreCase("Chỉnh sửa"))
                {
                    hoten.setEnabled(true);
                    sodienthoai.setEnabled(true);
                    chinhsua.setText("Lưu");

                }else
                {
                    String ChinhSua = "Update KHACHHANG SET EmailKH=?, HotenKH=? where MaKH=?";
                    SQLiteStatement statement = database.compileStatement(ChinhSua);
                    statement.bindString(1, sodienthoai.getText().toString()); // Bind số điện thoại vào vị trí đầu tiên
                    statement.bindString(2, hoten.getText().toString()); // Bind họ tên vào vị trí thứ hai
                    statement.bindString(3, Id); // Bind ID khách hàng vào vị trí thứ ba
                    statement.executeUpdateDelete(); // Thực thi câu lệnh SQL

                    Toast.makeText(Information.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    chinhsua.setText("Chỉnh sửa");

                    hoten.setEnabled(false);
                    sodienthoai.setEnabled(false);
                }
            }
        });


    }
