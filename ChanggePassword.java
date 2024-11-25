package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

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

import java.net.CacheRequest;

public class ChangePassword extends AppCompatActivity {

    EditText password,newpassword,renewpassword;
    Button xacnhan;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        password=findViewById(R.id.password);
        newpassword=findViewById(R.id.newpass);
        renewpassword=findViewById(R.id.renewpass);
        xacnhan=findViewById(R.id.btnchinhsua);
        Intent intent=getIntent();
        Bundle bundle= intent.getBundleExtra("bundle");
        int Id=bundle.getInt("id");
        CreateDatabase createDatabase= new CreateDatabase(this);
        database=createDatabase.open();
        String LayPass="Select Password from KHACHHANG where MaKH=?";
        Cursor cursor= database.rawQuery(LayPass,new String[]{""+Id});
        cursor.moveToFirst();
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!password.getText().toString().equals(cursor.getString(0)))//nếu nhập sai mật khẩu thông báo
                {
                    Toast.makeText(ChangePassword.this, "Mật khẩu cũ không trùng khớp!", Toast.LENGTH_SHORT).show();//thông báo
                }else{
                    if(renewpassword.getText().toString().equals("")||newpassword.getText().toString().equals(""))//nếu nhập lại mật khẩu có vấn đề thì thông báo

                    {
                        Toast.makeText(ChangePassword.this, "Hãy nhập đủ mật khẩu mới", Toast.LENGTH_SHORT).show();
                    }else{
                        if(renewpassword.getText().toString().equals(newpassword.getText().toString()))
                        {
                            String ChinhSua = "Update KHACHHANG SET Password=? where MaKH=?";//câu lệnh cậu nhật
                            SQLiteStatement statement = database.compileStatement(ChinhSua);
                            statement.bindString(1, newpassword.getText().toString()); // Bind số điện thoại vào vị trí đầu tiên
                            statement.bindString(2, ""+Id); // Bind họ tên vào vị trí thứ hai
                            statement.executeUpdateDelete(); // Thực thi câu lệnh SQL
                            password.setText("");
                            newpassword.setText("");
                            renewpassword.setText("");
                            Toast.makeText(ChangePassword.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();                }//thông báo cập nhật thành công
                        else{
                            Toast.makeText(ChangePassword.this, "Mật khẩu mới không trùng khớp!", Toast.LENGTH_SHORT).show();//thông báo thất bại

                        }
                    }

                }
            }
        });


    }
}
