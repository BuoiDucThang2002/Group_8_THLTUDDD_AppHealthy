package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

import java.util.Date;

public class Renewpassword extends AppCompatActivity {

    EditText pass,newpass;
    Button xacnhan;
    SQLiteDatabase database;
    private boolean isPasswordVisible = false;
    private boolean isPasswordVisible1 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renewpassword);
        pass=findViewById(R.id.password);
        newpass=findViewById(R.id.newpass);
        xacnhan=findViewById(R.id.xacnhan);

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        String sdt=bundle.getString("sdt","");
        CreateDatabase createDatabase= new CreateDatabase(this);
        database=createDatabase.open();
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().equals("")||newpass.getText().toString().equals(""))//Nếu trống mật khẩu và tài khoản thì  thông báo
                {
                    Toast.makeText(Renewpassword.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(pass.getText().toString().equals(newpass.getText().toString()))
                    {
                        String Update="UPDATE KHACHHANG SET Password=? where SdtKH=? or EmailKH=?";
                        Date currentDate = new Date();
                        database.execSQL(Update, new Object[]{pass.getText().toString(),sdt,sdt});
                        Toast.makeText(Renewpassword.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(Renewpassword.this, Login.class);//CHuyển màn hình
                        startActivity(intent);
                    }else{
                        Toast.makeText(Renewpassword.this, "Mật khẩu chưa trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Kiểm tra nếu chạm vào drawableEnd (drawableRight)
                    if (event.getRawX() >= (pass.getRight() - pass.getCompoundDrawables()[2].getBounds().width())) {
                        // Đổi trạng thái hiển thị mật khẩu
                        isPasswordVisible1 = !isPasswordVisible1;

                        if (isPasswordVisible1) {
                            // Hiển thị mật khẩu
                            pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0);
                        } else {
                            // Ẩn mật khẩu
                            pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_closed, 0);
                        }
                        // Đảm bảo con trỏ ở cuối văn bản
                        pass.setSelection(pass.getText().length());
                        return true;
                    }
                }

                return false;
            }
        });
        newpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Kiểm tra nếu chạm vào drawableEnd (drawableRight)
                    if (event.getRawX() >= (newpass.getRight() - newpass.getCompoundDrawables()[2].getBounds().width())) {
                        // Đổi trạng thái hiển thị mật khẩu
                        isPasswordVisible = !isPasswordVisible;

                        if (isPasswordVisible) {
                            // Hiển thị mật khẩu
                            newpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            newpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0);
                        } else {
                            // Ẩn mật khẩu
                            newpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            newpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_closed, 0);
                        }
                        // Đảm bảo con trỏ ở cuối văn bản
                        newpass.setSelection(newpass.getText().length());
                        return true;
                    }
                }

                return false;
            }
        });

    }
