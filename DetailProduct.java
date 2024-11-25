package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailProduct extends AppCompatActivity {

    ImageView Anhchinh,Anhphu,giohang;
    TextView Tensanpham1,Gia,Thuonghieu,Tensanpham2,Loaisanpham;
    SQLiteDatabase database;
    ImageButton back;
    int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        Anhchinh=findViewById(R.id.imageChinh);
        Anhphu=findViewById(R.id.AnhPhu);
        back=findViewById(R.id.back);


        Tensanpham1=findViewById(R.id.tenSanpham);
        Tensanpham2=findViewById(R.id.tenSanPham);
        giohang=findViewById(R.id.buttondathang);
        Gia=findViewById(R.id.gia);
        Thuonghieu=findViewById(R.id.thuonghieu);
        Loaisanpham=findViewById(R.id.loaisanpham);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });
        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        int a = bundle.getInt("Id", 0);
        Id=bundle.getInt("userId",0);
        String A=a+"";
        String LayDuLieuChiTiet="Select * from SANPHAM where MaSP=?";
        CreateDatabase createDatabase= new CreateDatabase(this);
        database=createDatabase.open();
        Cursor cursor=database.rawQuery(LayDuLieuChiTiet,new String[]{A});
        String LoaiSanPham="";

        if (cursor != null && cursor.moveToFirst()) {

            Tensanpham1.setText(""+cursor.getString(1));
            Gia.setText(""+formatCurrency(Integer.parseInt(cursor.getString(4)))+" / 1 kg");
            Thuonghieu.setText(""+cursor.getString(2));
            int imageResId = getResources().getIdentifier(cursor.getString(6), "drawable", getPackageName());
            if (imageResId != 0) {
                Anhchinh.setImageResource(imageResId);
            } else {
                // Nếu không tìm thấy tài nguyên, có thể đặt ảnh mặc định
                Anhchinh.setImageResource(R.drawable.thitlon);  // default_image là ảnh mặc định nếu không tìm thấy
            }
            if(cursor.getString(3).equals("1"))
            {
             LoaiSanPham="Thịt bò";
            }
            if(cursor.getString(3).equals("2"))
            {
                LoaiSanPham="Thịt lợn";
            }
            if(cursor.getString(3).equals("3"))
            {
                LoaiSanPham="Gia cầm";
            }
            if(cursor.getString(3).equals("4"))
            {
                LoaiSanPham="Cá sông";
            }
            if(cursor.getString(3).equals("5"))
            {
                LoaiSanPham="Rau xanh";
            }
            if(cursor.getString(3).equals("6"))
            {
                LoaiSanPham="Củ quả";
            }
            Loaisanpham.setText(LoaiSanPham);
            Tensanpham2.setText(cursor.getString(1));
            cursor.close();
        }
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql="Select * from HANGTRONGGIO where MaSP=? and MaKH=?";
                Cursor cursor = database.rawQuery(sql,new String[]{A,""+Id});
                if(cursor.getCount()!=0)
                {
                    Toast.makeText(DetailProduct.this, "Sản phẩm này đã tồn tại trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                }else{
                    //thêm dữ liệu vào csdl
                    ContentValues contentValues= new ContentValues();
                    contentValues.put(CreateDatabase.TB_HANGTRONGGIO_MAKHACHHANG,Id);
                    contentValues.put(CreateDatabase.TB_HANGTRONGGIO_MASANPHAM,A);
                    database.insert(CreateDatabase.TB_HANGTRONGGIO,null,contentValues);
                    Toast.makeText(DetailProduct.this, "Thêm giỏ hàng thành công! "+Id, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String formatCurrency(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + " đ";
    }
}
