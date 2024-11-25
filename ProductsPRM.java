package com.example.quanlybanhang;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsPRM#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsPRM extends Fragment implements CustomAdapterListProduct.OnBuyClickListener,CustomAdapterListProduct.OnLoveClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_USER_ID = "userId";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ProductsPRM(){

    }
    public static ProductsPRM newInstance(int id) {
        ProductsPRM fragment = new ProductsPRM();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, id);
        fragment.setArguments(args);
        return fragment;
    }
    public static ProductsPRM newInstance(String param1, String param2) {
        ProductsPRM fragment = new ProductsPRM();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    ImageButton thitbo,thitlon,rauxanh,giacam,casong,cuqua,giohang;
    ListView listView;
    EditText timkiem;
    SQLiteDatabase database;
    int userId;
    int malist[];
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_p_r_m, container, false);
        thitbo = view.findViewById(R.id.thitbo);
        thitlon = view.findViewById(R.id.thitlon);
        rauxanh = view.findViewById(R.id.rauxanh);
        giacam = view.findViewById(R.id.giacam);
        casong = view.findViewById(R.id.casong);
        cuqua = view.findViewById(R.id.cuqua);
        giohang=view.findViewById(R.id.giohang);
        timkiem=view.findViewById(R.id.Timkiem);
        listView = view.findViewById(R.id.list);
        CreateDatabase createDatabase= new CreateDatabase(getActivity());
        database=createDatabase.open();
        String LayTuongTu="Select * from SANPHAM";
        Cursor cursor=database.rawQuery(LayTuongTu,null);
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
                String gia=cursor.getString(4);
                giaList.add(gia);
                Anh.add(cursor.getString(6));
            } while (cursor.moveToNext());
            cursor.close();
        }
        CustomAdapterListProduct customAdapterListProduct= new CustomAdapterListProduct(getActivity(),tenList.toArray(new String[0]),giaList.toArray(new String[0]),Anh.toArray(new String[0]),this,this,userId);
        listView.setAdapter(customAdapterListProduct);
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), SearchProduct.class);//chuyển màn hình đến SearchProduct
                Bundle bundle= new Bundle();//Tạo đối tượng để lưu trữ dữ liệu cần truyền
                bundle.putInt("id",userId);//Lưu giá trị userId vào Bundle với khóa là "id".dữ liệu chuyển tới SearchProduct
                intent.putExtra("type",bundle);//Thêm Bundle vào Intent với khóa "type", để dữ liệu này có thể được truy xuất trong SearchProduct khi Intent được nhận.
                startActivityForResult(intent,100);
            }
        });
        giohang.setOnClickListener(new View.OnClickListener() {//Tương tự cái ở trên
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), ProductInCart.class);
                Bundle bundle= new Bundle();
                bundle.putInt("type",1);
                bundle.putInt("id",userId);
                intent.putExtra("type",bundle);
                startActivityForResult(intent,100);            }
        });
        thitbo.setOnClickListener(new View.OnClickListener() {//Tương tự cái ở trên
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ListProduct.class);
                Bundle bundle= new Bundle();
                bundle.putInt("type",1);
                bundle.putInt("id",userId);
                intent.putExtra("type",bundle);
                startActivityForResult(intent,100);
            }
        });
        thitlon.setOnClickListener(new View.OnClickListener() {//Tương tự cái ở trên
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ListProduct.class);
                Bundle bundle= new Bundle();
                bundle.putInt("type",2);
                bundle.putInt("id",userId);

                intent.putExtra("type",bundle);
                startActivityForResult(intent,100);
            }
        });
        giacam.setOnClickListener(new View.OnClickListener() {//Tương tự ở trên
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ListProduct.class);
                Bundle bundle= new Bundle();
                bundle.putInt("type",3);
                bundle.putInt("id",userId);

                intent.putExtra("type",bundle);
                startActivityForResult(intent,100);
            }
        });
        casong.setOnClickListener(new View.OnClickListener() {//Tương tự ở trên
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ListProduct.class);
                Bundle bundle= new Bundle();
                bundle.putInt("type",4);
                bundle.putInt("id",userId);

                intent.putExtra("type",bundle);
                startActivityForResult(intent,100);
            }
        });
        rauxanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ListProduct.class);
                Bundle bundle= new Bundle();
                bundle.putInt("type",5);
                bundle.putInt("id",userId);

                intent.putExtra("type",bundle);
                startActivityForResult(intent,100);
            }
        });
        cuqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ListProduct.class);
                Bundle bundle= new Bundle();
                bundle.putInt("type",6);
                bundle.putInt("id",userId);

                intent.putExtra("type",bundle);
                startActivityForResult(intent,100);
            }
        });

        int[] maArray = maList.stream().mapToInt(Integer::intValue).toArray();
        malist=maList.stream().mapToInt(Integer::intValue).toArray();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < maArray.length) { // Kiểm tra giới hạn vị trí
                    Intent detailIntent = new Intent(getContext(), DetailProduct.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("Id", maArray[position]);
                    bundle1.putInt("userId",userId);
                    detailIntent.putExtra("bundle", bundle1);
                    startActivity(detailIntent); // Chỉ cần startActivity nếu không cần kết quả trả về
                }
                Toast.makeText(getContext(), "Ahiiiii", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    @Override
    public void onBuyClicked(int position) {
        CreateDatabase createDatabase= new CreateDatabase(getActivity());
        database=createDatabase.open();
        String sql="Select * from HANGTRONGGIO where MaSP=? and MaKH=?";//Câu lệnh kiểm tra
      Cursor cursor = database.rawQuery(sql,new String[]{""+malist[position],""+userId});
      if(cursor.getCount()!=0)
      {
          Toast.makeText(getActivity(), "Sản phẩm này đã tồn tại trong giỏ hàng!", Toast.LENGTH_SHORT).show();
      }else{
          //thêm dữ liệu vào csdl
          ContentValues contentValues= new ContentValues();
          contentValues.put(CreateDatabase.TB_HANGTRONGGIO_MAKHACHHANG,userId);
          contentValues.put(CreateDatabase.TB_HANGTRONGGIO_MASANPHAM,malist[position]);
          database.insert(CreateDatabase.TB_HANGTRONGGIO,null,contentValues);
          Toast.makeText(getActivity(), "Thêm giỏ hàng thành công! "+userId, Toast.LENGTH_SHORT).show();
      }

    }
    @Override
    public void onLoveClicked(int position,boolean isLoved){
        if(!isLoved)
        {
            //Khởi tạo và mở kết nối đến cơ sở dữ liệu
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

                if (rowsAffected > 0) {//Điều kiện
                    Toast.makeText(getActivity(), "Xóa sản phẩm yêu thích thành công!", Toast.LENGTH_SHORT).show();//Thông báo
                } else {
                    Toast.makeText(getActivity(), "Không tìm thấy sản phẩm để xóa.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Lỗi khi xóa sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }



    }
  }
