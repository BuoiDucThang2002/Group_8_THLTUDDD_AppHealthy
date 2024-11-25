package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

public class ForgetPassword extends AppCompatActivity {
    EditText sdt,newpass;
    Button quenmatkhau;
    TextView dangnhap;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        sdt=findViewById(R.id.sodienthoai);
        quenmatkhau=findViewById(R.id.quenmatkhau);
        dangnhap=findViewById(R.id.dangnhap);
        CreateDatabase createDatabase= new CreateDatabase(this);
        database=createDatabase.open();
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ForgetPassword.this, Login.class);
                startActivity(intent);
            }
        });
        quenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sdt.getText().toString().equals(""))//nếu chưa nhập sđt thì thông báo
                {
                    Toast.makeText(ForgetPassword.this, "Hãy nhập đủ thông tin!", Toast.LENGTH_SHORT).show();//thông báo
                }else{
                    Cursor cursor=database.rawQuery("Select * from KHACHHANG where SdtKH=? or EmailKH=?",new String[]{sdt.getText().toString(),sdt.getText().toString()});
                    if(cursor.getCount()!=0)
                    {
                        Intent intent= new Intent(ForgetPassword.this, Renewpassword.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("sdt",sdt.getText().toString());
                        intent.putExtra("bundle",bundle);
                        startActivityForResult(intent,100);
                    }else{
                        Toast.makeText(ForgetPassword.this, "Số điện thoại hoặc Email của bạn chưa đúng hoặc chưa đăng ký", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}
