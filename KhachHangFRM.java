package com.example.quanlybanhang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KhachHangFRM#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KhachHangFRM extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KhachHangFRM() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KhachHangFRM.
     */
    // TODO: Rename and change types and number of parameters
    public static KhachHangFRM newInstance(String param1, String param2) {
        KhachHangFRM fragment = new KhachHangFRM();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ListView listView;
    SQLiteDatabase database;
    TextView soluongKH;
    EditText timkiem;
    ImageView emty;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view=inflater.inflate(R.layout.fragment_khach_hang_f_r_m, container, false);
      soluongKH=view.findViewById(R.id.khachhang);
      timkiem=view.findViewById(R.id.Timkiem);
        listView=view.findViewById(R.id.list);
        emty=view.findViewById(R.id.emty);
        CreateDatabase createDatabase= new CreateDatabase(getActivity());
        database=createDatabase.open();
        ArrayList<String> ten =new ArrayList<>();
        ArrayList<String> Sdt = new ArrayList<>();
        ArrayList<String> Diachi = new ArrayList<>();
        ArrayList<String> makh = new ArrayList<>();

        int dem=0;
        Cursor cursor=database.rawQuery("Select KHACHHANG.HotenKH,KHACHHANG.SdtKH,DONHANG.Diachi,KHACHHANG.MaKH from KHACHHANG,DONHANG where KHACHHANG.MaKH=DONHANG.MaKH and KHACHHANG.HotenKH LIKE ?",new String[]{"%"+timkiem.getText().toString()+"%"});
        if(cursor!=null&&cursor.moveToFirst())
        {
            do {
                ten.add(cursor.getString(0));
                Sdt.add(cursor.getString(1));
                Diachi.add(cursor.getString(2));
                makh.add(cursor.getString(3));
                dem++;
            } while (cursor.moveToNext());
        }
        soluongKH.setText("Tất cả "+dem+" khách hàng");
        CustomerAdapterKhachhang customerAdapterKhachhang= new CustomerAdapterKhachhang(ten.toArray(new String[0]),Sdt.toArray(new String[0]),Diachi.toArray(new String[0]),getActivity());
        listView.setAdapter(customerAdapterKhachhang);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getActivity(), DetailCustomer.class);
                Bundle bundle= new Bundle();
                bundle.putString("Id",makh.toArray(new String[0])[position]);
                intent.putExtra("bundle",bundle);
                startActivityForResult(intent,100);
            }
        });
        timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
Search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

return view;
    }
    public void Search(String query)
    {
        ArrayList<String> ten =new ArrayList<>();//Lưu trữ....
        ArrayList<String> Sdt = new ArrayList<>();
        ArrayList<String> Diachi = new ArrayList<>();
        ArrayList<String> makh = new ArrayList<>();

        int dem=0;
        Cursor cursor=database.rawQuery("Select KHACHHANG.HotenKH,KHACHHANG.SdtKH,DONHANG.Diachi,KHACHHANG.MaKH from KHACHHANG,DONHANG where KHACHHANG.MaKH=DONHANG.MaKH and KHACHHANG.HotenKH LIKE ?",new String[]{"%"+timkiem.getText().toString()+"%"});
        if(cursor!=null&&cursor.moveToFirst())
        {
            do {
                ten.add(cursor.getString(0));
                Sdt.add(cursor.getString(1));
                Diachi.add(cursor.getString(2));
                makh.add(cursor.getString(3));
                dem++;
            } while (cursor.moveToNext());
        }
        soluongKH.setText("Tất cả "+dem+" khách hàng");
        CustomerAdapterKhachhang customerAdapterKhachhang= new CustomerAdapterKhachhang(ten.toArray(new String[0]),Sdt.toArray(new String[0]),Diachi.toArray(new String[0]),getActivity());
        listView.setAdapter(customerAdapterKhachhang);

        if(ten.isEmpty())
        {
            emty.setVisibility(View.VISIBLE);  // Hiển thị hình ảnh trống
            listView.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.VISIBLE);  // Hiển thị hình ảnh trống
            emty.setVisibility(View.GONE);
        }
    }
}
