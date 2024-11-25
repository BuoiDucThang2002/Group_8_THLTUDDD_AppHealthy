package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {


    RelativeLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        frame=findViewById(R.id.frame);
        Intent intent= getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

           int id=bundle.getInt("idUser",0);

        BottomNavigationView bottomNavigationView= findViewById(R.id.menu_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                    Fragment selectedFragment = null;

            if (item.getItemId() == R.id.navigation_home) {//Kiểm tra xem ID của mục được chọn có phải là navigation_home (mục "Home" trong BottomNavigationView) hay không.
                selectedFragment =  ProductsPRM.newInstance(id);//Nếu mục "Home" được chọn, khởi tạo một đối tượng ProductsPRM (giả sử là Fragment) thông qua phương thức newInstance(id), truyền giá trị id vào như một tham số để có thể sử dụng trong Fragment.
            } else if (item.getItemId() == R.id.navigation_search) {
                selectedFragment =  ListProductLoveFRM.newInstance(id);
            } else if (item.getItemId() == R.id.navigation_notifications) {
                selectedFragment =  ListOrderFRM.newInstance(id);
            } else if (item.getItemId() == R.id.navigation_profile) {
                selectedFragment = ProfileFRM.newInstance(id);
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, selectedFragment)  // Thay đổi nội dung của FrameLayout
                        .commit();
            }

            return true;
                });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame,  ProductsPRM.newInstance(id))  // Fragment mặc định
                    .commit();
        }


    }

}
