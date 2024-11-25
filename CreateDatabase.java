public class CreateDatabase extends SQLiteOpenHelper {
    public  static String TB_KHACHHANG="KHACHHANG";
    public  static String TB_SANPHAM="SANPHAM";
    public  static String TB_SANPHAMYEUTHICH="SANPHAMYEUTHICH";

    public  static String TB_GIOHANG="GIOHANG";
    public  static String TB_HANGTRONGGIO="HANGTRONGGIO";
    public  static String TB_DONHANG="DONHANG";
    public  static String TB_HANGTRONGDON="HANGTRONGDON";

//-------------------------------KHÁCH HÀNG--------------------------
    public static String TB_KHACHHANG_MAKHACHHANG="MaKH";
    public static String TB_KHACHHANG_HOTENKHACHHANG="HotenKH";
    public static String TB_KHACHHANG_SDT="SdtKH";
    public static String TB_KHACHHANG_EMAIL="EmailKH";
    public static String TB_KHACHHANG_PASSWORD="Password";


    //------------------------------SẢN PHẨM-----------------------------
    public  static String TB_SANPHAM_MASANPHAM="MaSP";
    public  static String TB_SANPHAM_TENSANPHAM="TenSP";
    public  static String TB_SANPHAM_THUONGHIEU="Thuonghieu";
    public  static String TB_SANPHAM_LOAISANPHAM="LoaiSP";
    public  static String TB_SANPHAM_GIA="Gia";
    public  static String TB_SANPHAM_DANHGIA="Danhgia";
    public  static String TB_SANPHAM_ANH="Anh";

//--------------------------SẢN PHẨM YÊU THÍCH----------------------------
public  static String TB_SANPHAMYEUTHICH_MASANPHAM="MaSP";
    public  static String TB_SANPHAMYEUTHICH_MAKHACHHANG="MaKH";



//-------------------------------HÀNG TRONG GIỎ--------------------
    public  static String TB_HANGTRONGGIO_MAGIOHANG="MaGH";
    public  static String TB_HANGTRONGGIO_MAKHACHHANG="MaKH";

    public  static String TB_HANGTRONGGIO_MASANPHAM="MaSP";
    public  static String TB_HANGTRONGGIO_SOLUONG="Soluong";

    //---------------------ĐƠN HÀNG-----------------
    public  static String TB_DONHANG_MADONHANG="MaDH";
    public  static String TB_DONHANG_MAKHACHHANG="MaKH";
    public  static String TB_DONHANG_GIATONGDON="GiaDH";
    public  static String TB_DONHANG_NGAYDAT="Ngaydat";
    public  static String TB_DONHANG_TRANGTHAI="Trangthai";
    public  static String TB_DONHANG_DIACHI="Diachi";

    //---------------------HÀNG TRONG ĐƠN HÀNG-----------------
    public  static String TB_HANGTRONGDON_MAHANGTRONGDON="MaHTD";
    public  static String TB_HANGTRONGDON_MADONHANG="MaDH";
    public  static String TB_HANGTRONGDON_MASANPHAM="MaSP";
    public  static String TB_HANGTRONGDON_SOLUONG="Soluong";
    public  static String TB_HANGTRONGDON_GIA="Gia";

    public CreateDatabase(Context context)
    {
        super(context,"OrderFood",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbKhachHang= "CREATE TABLE "+TB_KHACHHANG+" ( "+TB_KHACHHANG_MAKHACHHANG+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TB_KHACHHANG_HOTENKHACHHANG+" TEXT, "+TB_KHACHHANG_EMAIL+ " TEXT, "+TB_KHACHHANG_SDT+" TEXT, "+TB_KHACHHANG_PASSWORD+" TEXT "+  " )";
        String tbSanPham="CREATE TABLE "+TB_SANPHAM+" ( "+TB_SANPHAM_MASANPHAM+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TB_SANPHAM_TENSANPHAM+" TEXT, "+TB_SANPHAM_THUONGHIEU+" TEXT, "+TB_SANPHAM_LOAISANPHAM+" TEXT, "+TB_SANPHAM_GIA+" INTEGER, "+TB_SANPHAM_DANHGIA+" INTEGER, "+TB_SANPHAM_ANH+" TEXT "+" ) ";
        String tbYeuThich="CREATE TABLE "+TB_SANPHAMYEUTHICH+" ( "+TB_SANPHAMYEUTHICH_MASANPHAM+" INTEGER PRIMARY KEY, "+TB_SANPHAMYEUTHICH_MAKHACHHANG+" TEXT "+")";
        String tbHangTrongGio="CREATE TABLE "+TB_HANGTRONGGIO+" ( "+TB_HANGTRONGGIO_MAGIOHANG+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TB_HANGTRONGGIO_MAKHACHHANG+" INTEGER, "+TB_HANGTRONGGIO_MASANPHAM+" INTEGER "+" ) ";
        String tbDonHang="CREATE TABLE "+TB_DONHANG+" ( "+TB_DONHANG_MADONHANG+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ TB_DONHANG_MAKHACHHANG+" INTEGER, "+TB_DONHANG_GIATONGDON+" INTEGER, "+TB_DONHANG_NGAYDAT+" TEXT, "+TB_DONHANG_TRANGTHAI+" INTEGER, "+TB_DONHANG_DIACHI+" TEXT "+" ) ";
        String tbHangTrongDon="CREATE TABLE "+TB_HANGTRONGDON+ " ( "+TB_HANGTRONGDON_MAHANGTRONGDON+" INTEGER PRIMARY KEY AUTOINCREMENT,"+TB_HANGTRONGDON_MADONHANG+" INTEGER, "+TB_HANGTRONGDON_MASANPHAM+" INTEGER, "+TB_HANGTRONGDON_SOLUONG+" INTEGER, "+TB_HANGTRONGDON_GIA+" INTEGER "+" ) ";
         db.execSQL(tbKhachHang);
        db.execSQL(tbSanPham);
        db.execSQL(tbHangTrongGio);
        db.execSQL(tbDonHang);
        db.execSQL(tbHangTrongDon);
        db.execSQL(tbYeuThich);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 10000) {  // Nếu đang từ phiên bản cũ lên phiên bản 2
            // Thêm các cột mới vào bảng KHACHHANG

        }
    }
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
