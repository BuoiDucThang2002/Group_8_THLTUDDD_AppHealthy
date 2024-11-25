package com.example.quanlybanhang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoanhThuFRM#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoanhThuFRM extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoanhThuFRM() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoanhThuFRM.
     */
    // TODO: Rename and change types and number of parameters
    public static DoanhThuFRM newInstance(String param1, String param2) {
        DoanhThuFRM fragment = new DoanhThuFRM();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    TextView tongthu,danggiao,donhuy,dagiao;
    ListView listView;
    SQLiteDatabase database;
    String []md;
    String []tg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_doanh_thu_f_r_m, container, false);
         tongthu=view.findViewById(R.id.doanhthungay);
        danggiao=view.findViewById(R.id.danggiao);
        donhuy=view.findViewById(R.id.donhuy);
        dagiao=view.findViewById(R.id.dagiao);
        listView=view.findViewById(R.id.list);
        ArrayList<String> madonhang = new ArrayList<>();//Lưu trữ madonhang
        ArrayList<String> trangthai = new ArrayList<>();//Lưu trữ trang thai
        ArrayList<String> gia = new ArrayList<>();
        ArrayList<String> ngaydat = new ArrayList<>();
        ArrayList<String> hotenkh = new ArrayList<>();
        ArrayList<String> sdt = new ArrayList<>();
        ArrayList<String> diachi = new ArrayList<>();

        CreateDatabase createDatabase= new CreateDatabase(getActivity());
        database=createDatabase.open();
        // sử dụng phương thức rawQuery để truy vấn dữ liệu từ cơ sở dữ liệu.
        Cursor cursor=database.rawQuery("Select DONHANG.MaDH,DONHANG.Trangthai,DONHANG.GiaDH,DONHANG.Ngaydat,KHACHHANG.HotenKH,KHACHHANG.SdtKH,DONHANG.Diachi from DONHANG,KHACHHANG where DONHANG.MaKH=KHACHHANG.MaKH",null);

        if(cursor!=null&&cursor.moveToFirst())
        {
            do {
               madonhang.add(cursor.getString(0));//thêm giá trị vào cột 0
               trangthai.add(cursor.getString(1));//thêm vào cột1
               gia.add(cursor.getString(2));//Thêm vào cột 2
               ngaydat.add(cursor.getString(3));//Thêm vào cột 3
               hotenkh.add(cursor.getString(4));//Thêm vào cột 4
               sdt.add(cursor.getString(5));//Thêm vào cột 5
               diachi.add(cursor.getString(6));//Thêm vào cột 6
            } while (cursor.moveToNext());
        }
        CustomAdapterDoanhThu customAdapterDoanhThu= new CustomAdapterDoanhThu(madonhang.toArray(new String[0]),
                trangthai.toArray(new String[0]),
                gia.toArray(new String[0]),
                ngaydat.toArray(new String[0]),
                hotenkh.toArray(new String[0]),
                sdt.toArray(new String[0]),
                diachi.toArray(new String[0]),
                getActivity()
                );
        listView.setAdapter(customAdapterDoanhThu);
        md=customAdapterDoanhThu.getMaDon();
        tg=customAdapterDoanhThu.getTongDon();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor1=database.rawQuery("Select MaKH from DONHANG where MaDH=?",new String[]{md[position]});
                cursor1.moveToFirst();
                Intent intent= new Intent(getActivity(), DetailOrder.class);
                Bundle bundle= new Bundle();
                bundle.putString("IdDonHang",md[position]);
                bundle.putString("IdUser",""+cursor1.getString(0));
                bundle.putString("tongtien",""+tg[position]);
                cursor1.close();
                intent.putExtra("bundle",bundle);
                startActivity(intent);
                           }
        });
        Cursor cursor1=database.rawQuery("Select * from DONHANG where Trangthai='Tranferent'",null);
        //truy vấn csdl lấy tất cả dữ liệu từ cột donhang nếu null thì không cần tuyền mảng tham số
        danggiao.setText(""+cursor1.getCount());
        cursor1.close();//Đóng cursor1 sau khi hoàn tất việc xử lý để giải phóng tài nguyên.
        Cursor cursor2=database.rawQuery("Select * from DONHANG where Trangthai='Cancel'",null);
        donhuy.setText(""+cursor2.getCount());
        cursor2.close();
        Cursor cursor3=database.rawQuery("Select * from DONHANG where Trangthai='DONE'",null);
        dagiao.setText(""+cursor3.getCount());
        cursor3.close();
        Cursor cursor4=database.rawQuery("Select Sum(GiaDH) from DONHANG where Trangthai='Waiting'",null);
        if(cursor4!=null&&cursor4.moveToFirst())
        {
            if(cursor4.getString(0)!=null)
            {
                tongthu.setText(""+formatCurrency(Integer.parseInt(cursor4.getString(0))));
                cursor4.close();
            }else{
                tongthu.setText(""+formatCurrency(0));

            }

        }


        return view;
    }
    private String formatCurrency(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + "đ";
    }
}
