package com.example.quanlybanhang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOrderFRM#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOrderFRM extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_USER_ID = "userId";
    int userId;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListOrderFRM() {
        // Required empty public constructor
    }
    public static ListOrderFRM newInstance(int id) {
        ListOrderFRM fragment = new ListOrderFRM();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListOrderFRM.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOrderFRM newInstance(String param1, String param2) {
        ListOrderFRM fragment = new ListOrderFRM();
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
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID, 0);
        }

    }
    ListView listView;
    EditText timkiem;
    ImageView emty;
    SQLiteDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list_order_f_r_m, container, false);
        listView=view.findViewById(R.id.listview);
        timkiem=view.findViewById(R.id.timkiem);
        emty=view.findViewById(R.id.emty);
        CreateDatabase createDatabase= new CreateDatabase(getActivity());
        database=createDatabase.open();
        Cursor cursor = database.rawQuery("Select * from DONHANG where MaKH=? ",new String[]{""+userId});
        ArrayList<String> maList = new ArrayList<>();//Lưu trữ
        ArrayList<String> ngaydat = new ArrayList<>();
        ArrayList<String> trangthai = new ArrayList<>();
        ArrayList<String> tongtien = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String ma=cursor.getString(0);
                maList.add(ma);
                String ten = cursor.getString(3); // Lấy giá trị cột đầu tiên
                ngaydat.add(ten);
                String gia=cursor.getString(4);
                trangthai.add(gia);
                String tt=cursor.getString(2);
                tongtien.add(tt);
            } while (cursor.moveToNext());
            cursor.close();
        }

        CustomAdapterOrder customAdapterOrder= new CustomAdapterOrder(maList.toArray(new String[0]),ngaydat.toArray(new String[0]),trangthai.toArray(new String[0]),tongtien.toArray(new String[0]),getActivity());
        String []maDon=customAdapterOrder.getMadon();
        String []tongDon=customAdapterOrder.getTongdon();

        listView.setAdapter(customAdapterOrder);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent= new Intent(getActivity(), DetailOrder.class);
                Bundle bundle= new Bundle();
                bundle.putString("IdDonHang",maDon[position]);
                bundle.putString("IdUser",""+userId);
                bundle.putString("tongtien",""+tongDon[position]);

                intent.putExtra("bundle",bundle);
                startActivity(intent);
                Toast.makeText(getActivity(), "Mã đơn: "+maDon[position], Toast.LENGTH_SHORT).show();
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
        CreateDatabase createDatabase= new CreateDatabase(getActivity());
        database=createDatabase.open();
        Cursor cursor = database.rawQuery("Select * from DONHANG where MaKH=? and MaDH LIKE ? ",new String[]{""+userId,"%"+timkiem.getText().toString()+"%"});
        ArrayList<String> maList = new ArrayList<>();
        ArrayList<String> ngaydat = new ArrayList<>();
        ArrayList<String> trangthai = new ArrayList<>();
        ArrayList<String> tongtien = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String ma=cursor.getString(0);
                maList.add(ma);
                String ten = cursor.getString(3); // Lấy giá trị cột đầu tiên
                ngaydat.add(ten);
                String gia=cursor.getString(4);
                trangthai.add(gia);
                String tt=cursor.getString(2);
                tongtien.add(tt);
            } while (cursor.moveToNext());
            cursor.close();
        }
        CustomAdapterOrder customAdapterOrder= new CustomAdapterOrder(maList.toArray(new String[0]),ngaydat.toArray(new String[0]),trangthai.toArray(new String[0]),tongtien.toArray(new String[0]),getActivity());
        listView.setAdapter(customAdapterOrder);
        if(maList.isEmpty())
        {
            emty.setVisibility(View.VISIBLE);  // Hiển thị hình ảnh trống
            listView.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.VISIBLE);  // Hiển thị hình ảnh trống
            emty.setVisibility(View.GONE);
        }
    }
}
