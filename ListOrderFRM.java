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
