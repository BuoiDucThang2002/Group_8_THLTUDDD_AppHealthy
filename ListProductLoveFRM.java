package com.example.quanlybanhang;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
 * Use the {@link ListProductLoveFRM#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListProductLoveFRM extends Fragment implements CustomAdapterListProduct.OnBuyClickListener,CustomAdapterListProduct.OnLoveClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_USER_ID = "userId";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int userId;

    public ListProductLoveFRM() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListProductLoveFRM.
     */
    // TODO: Rename and change types and number of parameters
    public static ListProductLoveFRM newInstance(String param1, String param2) {
        ListProductLoveFRM fragment = new ListProductLoveFRM();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static ListProductLoveFRM newInstance(int id) {
        ListProductLoveFRM fragment = new ListProductLoveFRM();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, id);
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
    SQLiteDatabase database;
    ImageView emty;
    EditText timkiem;
    int malist[];


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_list_product_love_f_r_m, container, false);
        CreateDatabase createDatabase= new CreateDatabase(getActivity());
        database=createDatabase.open();
        String LayTuongTu="Select SANPHAM.MaSP,SANPHAM.TenSP,SANPHAM.Gia,SANPHAM.Anh from SANPHAM,SANPHAMYEUTHICH where SANPHAM.MaSP=SANPHAMYEUTHICH.MaSP and SANPHAMYEUTHICH.MaKH=?";
        Cursor cursor=database.rawQuery(LayTuongTu,new String[]{""+userId});
        ArrayList<String> tenList = new ArrayList<>();
        ArrayList<String> giaList = new ArrayList<>();
        ArrayList<Integer> maList = new ArrayList<>();
        ArrayList<String> Anh = new ArrayList<>();
        Log.d("Cursor", "Row count: " + cursor.getCount());
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int ma=cursor.getInt(0);
                maList.add(ma);
                String ten = cursor.getString(1); // Lấy giá trị cột đầu tiên
                tenList.add(ten);
                String gia=cursor.getString(2);
                giaList.add(gia);
                Anh.add(cursor.getString(3));

            } while (cursor.moveToNext());
            cursor.close();
        }

        CustomAdapterListProduct customAdapterListProduct= new CustomAdapterListProduct(getActivity(),tenList.toArray(new String[0]),giaList.toArray(new String[0]),Anh.toArray(new String[0]), this,this,userId);
        listView=view.findViewById(R.id.list);
        emty=view.findViewById(R.id.emty);
        timkiem=view.findViewById(R.id.timkiem);
        if(tenList.isEmpty())
        {
            emty.setVisibility(view.VISIBLE);  // Hiển thị hình ảnh trống
            listView.setVisibility(view.GONE);
        }else{
            listView.setVisibility(view.VISIBLE);  // Hiển thị hình ảnh trống
            emty.setVisibility(view.GONE);
        }
        listView.setAdapter(customAdapterListProduct);
        int[] maArray = maList.stream().mapToInt(Integer::intValue).toArray();
        malist=maList.stream().mapToInt(Integer::intValue).toArray();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < maArray.length) { // Kiểm tra giới hạn vị trí
                    Intent detailIntent = new Intent(getContext(), DetailProduct.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("Id", maArray[position]);
                    detailIntent.putExtra("bundle", bundle1);
                    startActivity(detailIntent); // Chỉ cần startActivity nếu không cần kết quả trả về
                }
                Toast.makeText(getContext(), "Ahiiiii", Toast.LENGTH_SHORT).show();
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
        String LayTuongTu="Select SANPHAM.MaSP,SANPHAM.TenSP,SANPHAM.Gia,SANPHAM.Anh from SANPHAM,SANPHAMYEUTHICH where SANPHAM.MaSP=SANPHAMYEUTHICH.MaSP and SANPHAMYEUTHICH.MaKH=? and SANPHAM.TenSP LIKE ?";
        Cursor cursor= database.rawQuery(LayTuongTu,new String[]{""+userId ,"%"+ query + "%"});
        ArrayList<String> tenList = new ArrayList<>();//Lưu trữ chuỗi tenList
        ArrayList<String> giaList = new ArrayList<>();
        ArrayList<Integer> maList = new ArrayList<>();
        ArrayList<String> Anh = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int ma=cursor.getInt(0);
                maList.add(ma);
                String ten = cursor.getString(1); // Lấy giá trị cột đầu tiên
                tenList.add(ten);
                String gia=cursor.getString(2);
                giaList.add(gia);
                Anh.add(cursor.getString(3));
            } while (cursor.moveToNext());
            cursor.close();
        }
        CustomAdapterListProduct customAdapterListProduct= new CustomAdapterListProduct(getActivity(),tenList.toArray(new String[0]),giaList.toArray(new String[0]),Anh.toArray(new String[0]), this,this,userId);
        listView.setAdapter(customAdapterListProduct);
        if(tenList.isEmpty())
        {
            emty.setVisibility(View.VISIBLE);  // Hiển thị hình ảnh trống
            listView.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.VISIBLE);  // Hiển thị hình ảnh trống
            emty.setVisibility(View.GONE);
        }
    }
    @Override
    public void onBuyClicked(int position) {
        // Xử lý sự kiện khi người dùng nhấn nút "Buy"
        CreateDatabase createDatabase= new CreateDatabase(getActivity());
        database=createDatabase.open();
        String sql="Select * from HANGTRONGGIO where MaSP=? and MaKH=?";
        Cursor cursor = database.rawQuery(sql,new String[]{""+malist[position],""+userId});
        if(cursor.getCount()!=0)
        {
            Toast.makeText(getActivity(), "Sản phẩm này đã tồn tại trong giỏ hàng!", Toast.LENGTH_SHORT).show();
        }else{
            //Thêm dữ liệu vào csdl
            ContentValues contentValues= new ContentValues();
            contentValues.put(CreateDatabase.TB_HANGTRONGGIO_MAKHACHHANG,userId);
            contentValues.put(CreateDatabase.TB_HANGTRONGGIO_MASANPHAM,malist[position]);
            contentValues.put(CreateDatabase.TB_HANGTRONGGIO_SOLUONG,0);
            database.insert(CreateDatabase.TB_HANGTRONGGIO,null,contentValues);
            Toast.makeText(getActivity(), "Thêm giỏ hàng thành công! "+userId, Toast.LENGTH_SHORT).show();
        }

        // Mở Activity chi tiết sản phẩm
    }
    @Override
    public void onLoveClicked(int position,boolean isLoved){
        if(!isLoved)
        {
           // Tạo đối tượng làm việc với csdl và mở kết nối và thêm csdl

            CreateDatabase createDatabase= new CreateDatabase(getActivity());
            database=createDatabase.open();
            ContentValues contentValues= new ContentValues();
            contentValues.put(CreateDatabase.TB_SANPHAMYEUTHICH_MASANPHAM,malist[position]);
            contentValues.put(CreateDatabase.TB_SANPHAMYEUTHICH_MAKHACHHANG,userId);

            database.insert(CreateDatabase.TB_SANPHAMYEUTHICH,null,contentValues);
            Toast.makeText(getActivity(), "Yêu thích thành công! "+malist[position], Toast.LENGTH_SHORT).show();
        }else{
            try {
                // Chuẩn bị câu lệnh DELETE
                String deleteQuery = "DELETE FROM SANPHAMYEUTHICH WHERE MaSP=?";
                SQLiteStatement statement = database.compileStatement(deleteQuery);

                // Gán giá trị cho tham số (MaSP)
                statement.bindString(1, ""+malist[position]);

                // Thực thi câu lệnh DELETE
                int rowsAffected = statement.executeUpdateDelete();

                if (rowsAffected > 0) {
                    Toast.makeText(getActivity(), "Xóa sản phẩm yêu thích thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Không tìm thấy sản phẩm để xóa.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Lỗi khi xóa sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }



    }
