package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class Login extends AppCompatActivity {

    EditText username,password;
    Button login;
    TextView dangky,qmk;

    private boolean isPasswordVisible = false;

    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        qmk=findViewById(R.id.forget);
        login=findViewById(R.id.btnLogin);
        CreateDatabase createDatabase= new CreateDatabase(this);
        database=createDatabase.open();
        dangky=findViewById(R.id.dangky);
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this, SignIn.class);//Chuyển màn
                startActivity(intent);//Khởi động
            }
        });
        qmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this, ForgetPassword.class);//Chuyển sang màn ForgetPassword
                startActivity(intent);//Khởi động
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("")||password.getText().toString().equals(""))//Nếu chưa nhập tài khoản & mật khẩu thì hiện thông báo
                {
                    Toast.makeText(Login.this, "Vui lòng nhập đủ thông tin!!!", Toast.LENGTH_SHORT).show();//Thông báo
                }else{
                    String KiemTraDangNhap = "SELECT * FROM KHACHHANG WHERE (EmailKH = ? OR SdtKH = ?) and Password=?";//Kiểm tra CSDL
                    Cursor cursor = database.rawQuery(KiemTraDangNhap, new String[]{username.getText().toString(), username.getText().toString(),password.getText().toString()});
                    //Nếu thông tin đăng nhập hợp lệ, người dùng sẽ được chuyển đến màn hình Home và idUser sẽ được truyền theo.
                    if(cursor.getCount()!=0)//Kiểm tra xem có hợp lệ không
                    {
                        cursor.moveToFirst();
                        Intent intent= new Intent(Login.this, Home.class);
                        Bundle bundle= new Bundle();
                        bundle.putInt("idUser",cursor.getInt(0));
                        intent.putExtra("bundle",bundle);
                        startActivityForResult(intent,100);

                    }else if(username.getText().toString().equals("admin")&&password.getText().toString().equals("admin")){
                        Intent intent= new Intent(Login.this, Admindoanhthu.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(Login.this, "Vui lòng kiểm tra lại thông tin!!!", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Kiểm tra nếu chạm vào drawableEnd (drawableRight)
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[2].getBounds().width())) {
                        // Đổi trạng thái hiển thị mật khẩu
                        isPasswordVisible = !isPasswordVisible;

                        if (isPasswordVisible) {
                            // Hiển thị mật khẩu
                            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0);
                        } else {
                            // Ẩn mật khẩu
                            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_closed, 0);
                        }
                        // Đảm bảo con trỏ ở cuối văn bản
                        password.setSelection(password.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
