package com.example.quanlybanhang;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFRM#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFRM extends Fragment {

    private static final String ARG_USER_ID = "userId";
    private int userId;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int id;
    public static ProfileFRM newInstance(int id) {
        ProfileFRM fragment = new ProfileFRM();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, id);
        fragment.setArguments(args);
        return fragment;
    }    public ProfileFRM() {
        // Required empty public constructor
    }


    ListView listView;
    TextView hoten,sdt;
    SQLiteDatabase database;
    public static ProfileFRM newInstance(String param1, String param2) {
        ProfileFRM fragment = new ProfileFRM();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {//dữ liệu được truyền vào Fragment thông qua getArguments và gắn vào các biến tương ứng để sử dụng trong Fragment
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {//kiểm tra xem có truyền đúng không
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_f_r_m, container, false);
        hoten=view.findViewById(R.id.hoten);
        sdt=view.findViewById(R.id.sdt);
        CreateDatabase createDatabase= new CreateDatabase(getActivity());
        database=createDatabase.open();
        String LayDuLieu="Select * from KHACHHANG where MaKH=?";
        Cursor cursor=database.rawQuery(LayDuLieu,new String[]{""+userId});
        cursor.moveToFirst();
        hoten.setText(cursor.getString(1));
        sdt.setText(cursor.getString(3));
        listView=view.findViewById(R.id.list);
        String []name={"Thông tin tài khoản","Đổi mật khẩu","Hướng dẫn sử dụng","Liên hệ hotline hỗ trợ","Đăng xuất"};
        CustomAdapterProfile customAdapterProfile=new CustomAdapterProfile(name,getActivity());
        listView.setAdapter(customAdapterProfile);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    Intent intent= new Intent(getActivity(),Information.class);//chuyển màn
                    Bundle bundle= new Bundle();//Tạo một đối tượng Bundle mới, dùng để lưu trữ các giá trị cần truyền giữa các Activity hoặc Fragment
                    bundle.putInt("Id",userId);//Lưu một giá trị int vào Bundle với khóa là "Id" và giá trị là userId.
                    intent.putExtra("bundle",bundle);
                    startActivityForResult(intent,100);
                }
                if(position==1)
                {
                    Intent intent= new Intent(getActivity(),ChangePassword.class);//
                    Bundle bundle= new Bundle();
                    bundle.putInt("id",userId);
                    intent.putExtra("bundle",bundle);
                    startActivityForResult(intent,100);
                }
                if(position==2)
                {
                    Intent intent= new Intent(getActivity(), dieukhoansudung.class);//chuyển màn
                    startActivity(intent);//Khởi động
                }
                if(position==3)
                {
                    Uri uri=Uri.parse("tel:"+"19006789");
                    Intent intent=new Intent(Intent.ACTION_DIAL,uri);
                    startActivity(intent);
                }
                if(position==4)
                {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear(); // hoặc editor.remove("key") nếu bạn chỉ muốn xóa một giá trị cụ thể
                    editor.apply();

                    // Thông báo cho người dùng
                    Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

                    // Chuyển đến màn hình đăng nhập
                    Intent intent = new Intent(getActivity(), Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish(); // Đóng tất cả các Activity hiện tại
                }

            }
        });
        return view;
    }
}
