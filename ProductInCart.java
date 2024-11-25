package com.example.quanlybanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.database.CreateDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductInCart extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase database;
    Button dathang;
    TextView tongtien;
    ImageButton back;
    boolean []select;
    int []soluong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_in_cart);
        listView=findViewById(R.id.list);
        dathang=findViewById(R.id.Dathang);
        tongtien=findViewById(R.id.tongtien);
        back=findViewById(R.id.back);
        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("type");
        int id=bundle.getInt("id", 0);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });
        CreateDatabase createDatabase= new CreateDatabase(this);//Khởi tạo csdl
        database=createDatabase.open();
        String LayDuLieu="Select SANPHAM.MaSP,SANPHAM.TenSP,SANPHAM.Gia,SANPHAM.Anh from HANGTRONGGIO,SANPHAM Where HANGTRONGGIO.MaSP=SANPHAM.MaSP and MaKH=?";

        Cursor cursor=database.rawQuery(LayDuLieu, new String[]{String.valueOf(id)});
        ArrayList<String> tenList = new ArrayList<>();
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
        CustomAdapterCart customAdapterCart= new CustomAdapterCart(tenList.toArray(new String[0]), giaList.toArray(new String[0]),Anh.toArray(new String[0]), this, new CustomAdapterCart.OnTotalAmountChangeListener() {
            @Override
            public void onTotalAmountChanged(int totalAmount, boolean[] selectItems, int[]quantitys) {
                select=selectItems;
                soluong=quantitys;
                tongtien.setText(""+formatCurrency(totalAmount));
            }
        });

    listView.setAdapter(customAdapterCart);
        dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               database.execSQL("Delete from DONHANG");
//                database.execSQL("Delete from HANGTRONGDON");
//                Intent intent1= new Intent(ProductInCart.this,CartProduct.class);
//                startActivity(intent1);
                Cursor cursor2=database.rawQuery("Select * from DONHANG where Trangthai='Pending'",null);
                if(cursor2.getCount()!=0)
                {
                    Toast.makeText(ProductInCart.this, "Not ok", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProductInCart.this, "ok", Toast.LENGTH_SHORT).show();//Thông báo
                    //Thêm dữ liệu vào cơ sở dữ liệu
                    ContentValues contentValues= new ContentValues();
                    contentValues.put(CreateDatabase.TB_DONHANG_MAKHACHHANG,id);
                    contentValues.put(CreateDatabase.TB_DONHANG_TRANGTHAI,"Pending");
                    long result=database.insert(CreateDatabase.TB_DONHANG,null,contentValues);

                    if(result!=-1)
                    {
                        String laymadon="Select MaDH from DONHANG where MaKH=? and Trangthai='Pending' ";
                        Cursor cursor1=database.rawQuery(laymadon,new String[]{""+id});
                        cursor1.moveToFirst();
                        if(cursor1.getCount()!=0)
                        {
                            for(int i=0;i<select.length;i++)
                            {
                                if(select[i]==true){
                                    Toast.makeText(ProductInCart.this, "Mã đơn: "+cursor1.getInt(0), Toast.LENGTH_SHORT).show();
                                    //Thêm dữ liệu vào csdl
                                    ContentValues contentValues1= new ContentValues();
                                    contentValues1.put(CreateDatabase.TB_HANGTRONGDON_MADONHANG,cursor1.getInt(0));
                                    contentValues1.put(CreateDatabase.TB_HANGTRONGDON_MASANPHAM,maList.get(i));
                                    contentValues1.put(CreateDatabase.TB_HANGTRONGDON_GIA,giaList.get(i));
                                    contentValues1.put(CreateDatabase.TB_HANGTRONGDON_SOLUONG,soluong[i]);
                                    database.insert(CreateDatabase.TB_HANGTRONGDON,null,contentValues1);
                                }
                            }
                            Intent intent1= new Intent(ProductInCart.this,CartProduct.class);//Chuyển màn
                            Bundle bundle1= new Bundle();//Tạo một đối tượng Bundle mới, dùng để lưu trữ các giá trị cần truyền giữa các Activity hoặc Fragment.
                            bundle1.putInt("MaDH",cursor1.getInt(0));
                            intent1.putExtra("Bundle",bundle1);//Đưa Bundle vào Intent dưới tên khóa "bundle", để truyền dữ liệu đến Activity hoặc Fragment khác.
                            startActivityForResult(intent1,100);

                        }

                    }
                }

            }
        });
    }
    private String formatCurrency(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));//Định dạng theo  Việt Nam
        return formatter.format(amount) + "đ";// giá tiền VND
    }
}
