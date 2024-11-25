package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

public class SignIn extends AppCompatActivity {
    EditText name,email,sdt,password,repassword;
    TextView dangnhap;
    Button signin;
    Context context;
    SQLiteDatabase database;
    private boolean isPasswordVisible = false;
    private boolean isPasswordVisible1 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        CreateDatabase createDatabase= new CreateDatabase(this);
            database=createDatabase.open();
            name=findViewById(R.id.name);
            email=findViewById(R.id.email);
            sdt=findViewById(R.id.sodienthoai);
            password=findViewById(R.id.password);
            repassword=findViewById(R.id.repassword);
            signin=findViewById(R.id.btnSignin);
            dangnhap=findViewById(R.id.dangnhap);
            dangnhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(SignIn.this, Login.class);//chuyển sang màn hình login
                    startActivity(intent);
                }
            });
            signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")||email.getText().toString().equals("")||sdt.getText().toString().equals("")||password.getText().toString().equals("")||repassword.getText().toString().equals(""))//nhập tài khoản và nhập mật khẩu
                {
                    Toast.makeText(SignIn.this, "Vui lòng nhập đủ thông tin!!!", Toast.LENGTH_SHORT).show();// thông báo lỗi
                }else if(!password.getText().toString().equals(repassword.getText().toString())){
                    Toast.makeText(SignIn.this, "Vui lòng nhập lại mật khẩu trùng khớp!!!", Toast.LENGTH_SHORT).show();//thông báo lỗi
                }else{
                    String Kiemtra="Select * from KHACHHANG where EmailKH=? or SdtKH=?";//Kiểm tra thông tin xem có khớp trong bảng không
                    Cursor cursor=database.rawQuery(Kiemtra,new String[]{email.getText().toString(),sdt.getText().toString()});//duyệt dữ liệu
                    if(cursor.getCount()!=0)
                    {
                        Toast.makeText(SignIn.this, "Email hoặc Số điện thoại đã tồn tại!!!", Toast.LENGTH_SHORT).show();//thông báo
                    }else{
                        ContentValues contentValues= new ContentValues();
                        contentValues.put(CreateDatabase.TB_KHACHHANG_HOTENKHACHHANG,name.getText().toString());//chuẩn bị thêm dữ liệu HOTENKHACHHANG vào bảng khách hàng
                        contentValues.put(CreateDatabase.TB_KHACHHANG_EMAIL,email.getText().toString());
                        contentValues.put(CreateDatabase.TB_KHACHHANG_SDT,sdt.getText().toString());
                        contentValues.put(CreateDatabase.TB_KHACHHANG_PASSWORD,password.getText().toString());
                        database.insert(CreateDatabase.TB_KHACHHANG,null,contentValues);
                        Toast.makeText(SignIn.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();//THông báo đăng ký thành công
                        Intent intent= new Intent(SignIn.this, Login.class);//chuyển màn hinhd
                        startActivity(intent);
                    }
                    
                    
                }
            }
        });
