package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

import java.util.ArrayList;

public class ListProduct extends AppCompatActivity implements CustomAdapterListProduct.OnBuyClickListener,CustomAdapterListProduct.OnLoveClickListener  {

    SQLiteDatabase database;
    ListView listView;
    ImageView emty;
    EditText timkiem;
    ImageButton giohang,back;
    int malist[];
    int Id;
    int loaiSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        listView=findViewById(R.id.list);
        emty=findViewById(R.id.emptyImage);
        timkiem=findViewById(R.id.timkiem);
        giohang=findViewById(R.id.giohang);
        back=findViewById(R.id.back);
        CreateDatabase createDatabase= new CreateDatabase(this);
        database=createDatabase.open();
        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("type");
        int a = bundle.getInt("type", 0);
        loaiSP=a;
        int id=bundle.getInt("id", 0);
        Id=id;
        String A=a+"";
        String LayDuLieuTheoLoai="Select * from SANPHAM where LoaiSP=?";
        Cursor cursor= database.rawQuery(LayDuLieuTheoLoai,new String[]{A});
        ArrayList<String> tenList = new ArrayList<>();//Lưu trữ chuỗi....
        ArrayList<String> giaList = new ArrayList<>();
        ArrayList<Integer> maList = new ArrayList<>();
        ArrayList<String> Anh = new ArrayList<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });

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
        Toast.makeText(ListProduct.this, tenList.get(0).toString(), Toast.LENGTH_SHORT).show();
        CustomAdapterListProduct customAdapterListProduct= new CustomAdapterListProduct(this,tenList.toArray(new String[0]),giaList.toArray(new String[0]),Anh.toArray(new String[0]), this,this,id);
        listView.setAdapter(customAdapterListProduct);
        int[] maArray = maList.stream().mapToInt(Integer::intValue).toArray();
         malist=maList.stream().mapToInt(Integer::intValue).toArray();
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ListProduct.this, ProductInCart.class);
                Bundle bundle= new Bundle();
                bundle.putInt("type",1);
                bundle.putInt("id",Id);
                intent.putExtra("type",bundle);
                startActivityForResult(intent,100);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < maArray.length) { // Kiểm tra giới hạn vị trí
                    Intent detailIntent = new Intent(ListProduct.this, DetailProduct.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("Id", maArray[position]);
                    detailIntent.putExtra("bundle", bundle1);
                    startActivity(detailIntent); // Chỉ cần startActivity nếu không cần kết quả trả về
                } else {
                    Toast.makeText(ListProduct.this, "Không tìm thấy sản phẩm.", Toast.LENGTH_SHORT).show();//Thông báo
                }
                Toast.makeText(ListProduct.this, "Ahiiiii", Toast.LENGTH_SHORT).show();
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
    }
    public void Search(String query)
    {
        CreateDatabase createDatabase= new CreateDatabase(this);
        database=createDatabase.open();
        String LayDuLieuTheoLoai="Select * from SANPHAM where TenSP LIKE ? and LoaiSP=?";
        Cursor cursor= database.rawQuery(LayDuLieuTheoLoai,new String[]{"%" + query + "%",""+loaiSP});
        ArrayList<String> tenList = new ArrayList<>();//Lưu trữ chuỗi
        ArrayList<String> giaList = new ArrayList<>();
        ArrayList<Integer> maList = new ArrayList<>();
        ArrayList<String> Anh = new ArrayList<>();
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
        CustomAdapterListProduct customAdapterListProduct= new CustomAdapterListProduct(this,tenList.toArray(new String[0]),giaList.toArray(new String[0]),Anh.toArray(new String[0]), this,this,Id);
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
        CreateDatabase createDatabase= new CreateDatabase(ListProduct.this);
        database=createDatabase.open();
        String sql="Select * from HANGTRONGGIO where MaSP=? and MaKH=?";
        Cursor cursor = database.rawQuery(sql,new String[]{""+malist[position],""+Id});
        if(cursor.getCount()!=0)
        {
            Toast.makeText(ListProduct.this, "Sản phẩm này đã tồn tại trong giỏ hàng!", Toast.LENGTH_SHORT).show();
        }else{
            //thêm dữ liệu vào csdl
            ContentValues contentValues= new ContentValues();
            contentValues.put(CreateDatabase.TB_HANGTRONGGIO_MAKHACHHANG,Id);
            contentValues.put(CreateDatabase.TB_HANGTRONGGIO_MASANPHAM,malist[position]);
            database.insert(CreateDatabase.TB_HANGTRONGGIO,null,contentValues);
            Toast.makeText(ListProduct.this, "Thêm giỏ hàng thành công! "+Id, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onLoveClicked(int position,boolean isLoved){
        if(!isLoved)
        {
            CreateDatabase createDatabase= new CreateDatabase(this); //Làm việc với csdl
            database=createDatabase.open();//nở cssdl
            ContentValues contentValues= new ContentValues();//thêm dữ liệu vào csdl
            contentValues.put(CreateDatabase.TB_SANPHAMYEUTHICH_MASANPHAM,malist[position]);
            contentValues.put(CreateDatabase.TB_SANPHAMYEUTHICH_MAKHACHHANG,Id);
            database.insert(CreateDatabase.TB_SANPHAMYEUTHICH,null,contentValues);
            Toast.makeText(ListProduct.this, "Yêu thích thành công! "+malist[position], Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ListProduct.this, "Xóa sản phẩm yêu thích thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListProduct.this, "Không tìm thấy sản phẩm để xóa.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ListProduct.this, "Lỗi khi xóa sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
