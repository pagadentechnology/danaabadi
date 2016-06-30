package ui.tech.sahabatmakara.util;

import ui.co.veritrans.android.api.VTUtil.VTConfig;

public class ParameterCollections {

//	public final static String PAYMENT_API_SANDBOX = "https://api.sandbox.veritrans.co.id/v2/token";
	public final static String PAYMENT_API_SANDBOX = "https://api.veritrans.co.id/v2/token";

    public static String getPaymentApiUrl(){
        if(VTConfig.VT_IsProduction){
            return PAYMENT_API_SANDBOX;
        }
        return PAYMENT_API_SANDBOX;
    }

	//VT client coba
//	public static final String VT_CLIENT = "VT-client-ioHS3qhTIndG-Nkk";

	//VT LIVE
	public static final String VT_CLIENT = "VT-client-V10BH4kj9UVCbQgQ";


//	public static final String URL_GET_VA = "http://128.199.176.5/UI/admin/api/va.php?";
//	public static final String URL_GET = "http://128.199.176.5/UI/admin/api/get.php?";
//	public static final String URL_INSERT = "http://128.199.176.5/UI/admin/api/insert.php?";
//	public static final String URL_LOGIN = "http://128.199.176.5/UI/admin/api/login.php?";
//	public static final String URL_REGIS = "http://128.199.176.5/UI/admin/api/regis.php?";
//	public static final String URL_FB = "http://128.199.176.5/UI/admin/api/fb.php?";
//	public static final String URL_UPDATE = "http://128.199.176.5/UI/admin/api/update.php?";
//	public static final String URL_IMG_ORIGINAL = "http://128.199.176.5/UI/admin/files/original/";
//	public static final String URL_IMG_THUMBNAIL= "http://128.199.176.5/UI/admin/files/thumbnail/";
//	public static final String URL_SEND_TOKEN= "http://128.199.176.5/UI/admin/veritrans/cc.php?";
//	public static final String URL_TRANSFER= "http://128.199.176.5/UI/admin/veritrans/transfer.php?";

	public static final String URL_GET_VA = "http://sahabatmakara.ui.ac.id/admin/api/va.php?";
	public static final String URL_GET = "http://sahabatmakara.ui.ac.id/admin/api/get.php?";
	public static final String URL_INSERT = "http://sahabatmakara.ui.ac.id/admin/api/insert.php?";
	public static final String URL_LOGIN = "http://sahabatmakara.ui.ac.id/admin/api/login.php?";
	public static final String URL_REGIS = "http://sahabatmakara.ui.ac.id/admin/api/regis.php?";
	public static final String URL_FB = "http://sahabatmakara.ui.ac.id/admin/api/fb.php?";
	public static final String URL_UPDATE = "http://sahabatmakara.ui.ac.id/admin/api/update.php?";
	public static final String URL_IMG_ORIGINAL = "http://sahabatmakara.ui.ac.id/admin/files/original/";
	public static final String URL_IMG_THUMBNAIL= "http://sahabatmakara.ui.ac.id/admin/files/thumbnail/";
	public static final String URL_SEND_TOKEN= "http://sahabatmakara.ui.ac.id/admin/veritrans/cc.php?";
	public static final String URL_TRANSFER= "http://sahabatmakara.ui.ac.id/admin/veritrans/transfer.php?";

	public static final String URL_UPDATE_PHOTO = "http://sahabatmakara.ui.ac.id/admin/api/photo.php";

	public static final String SH_LOGGED = "logged";
	public static final String SH_NAME = "sahabat_makara";

	public static final String SH_USER_ID= "user_id";
	public static final String SH_NAMA_DONATUR = "nama";
	public static final String SH_PHONE_DONATUR = "phone";
	public static final String SH_EMAIL_DONATUR = "email";
	public static final String SH_USER_PHOTO = "user_photo";
	public static final String SH_ALAMAT_DONATUR = "alamat";
	public static final String SH_NAMA_PROGRAM= "nama_program";
	public static final String SH_TUJUAN_FAKULTAS= "tujuan_fakultas";
	public static final String SH_PROGRAM_ID= "program_id";
	public static final String SH_JUMLAH_DONASI = "jmh_donasi";
	public static final String SH_DONASI_DESC = "donation_desc";

	public static final String SH_NAMA_FAKULTAS = "nama_fakultas";
	public static final String SH_PUBLISHED = "published";
	
	public static final String ARGS_ID = "id";
	public static final String ARGS_PAGENUMBER = "id_PageNumber";
	public static final String ARGS_ARRAYLIST = "arraydata";
	public static final String ARGS_DONATUR_NAME = "nama_donatur";
	public static final String ARGS_DONATUR_NAME2 = "nama_donatur2";
	public static final String ARGS_DONATUR_JUMLAH_DONASI = "jumlah_donasi";
	public static final String ARGS_EMAIL = "email";
	public static final String ARGS_NOTLP = "nohp";
	public static final String ARGS_NAMA_IMAGE = "image_name";

	public static final String ARGS_LAT= "latitude";
	public static final String ARGS_LONG = "longitude";
	public static final String ARGS_ALAMAT = "alamat";
	public static final String ARGS_TITLE = "title";
	public static final String ARGS_DATE = "date";
	public static final String ARGS_DESC = "desc";
	public static final String ARGS_URLIMG = "url_img";
	public static final String ARGS_URLIMG2 = "url_img2";

	public static final String ARGS_FAKULTAS = "fakultas";
	public static final String ARGS_FAKULTAS2 = "fakultas2";

	public static final String ARGS_NAMA_REK = "nama_rek";
	public static final String ARGS_NO_REK = "no_rek";

	public static final String KIND = "kind";


	public static final String KIND_TOKEN = "token";
	public static final String KIND_BANK = "bank";

	public static final String KIND_JENIS = "kind";
	public static final String KIND_PROGRAM = "program";
	public static final String KIND_NEWS = "news";
	public static final String KIND_EVENT = "event";
	public static final String KIND_EVENT_UPCOMING = "upcomingevent";
	public static final String KIND_GALLERY = "gallery";
	public static final String KIND_ACCOUNT_USER = "user";
	public static final String KIND_TOPNEWS = "top4news";
	public static final String KIND_TOP3PROGRAM = "top3program";
	public static final String KIND_TESTIMONY= "testimoni";
	public static final String KIND_DONASI= "top8donaters";
	public static final String KIND_FB= "FB";
	public static final String KIND_EDIT_USER= "user_edit";
	public static final String KIND_EDIT_PASSWORD= "user_edit_username";
	public static final String KIND_DONASI_JEMPUTDANA= "donation";
	public static final String KIND_DONASI_RIWAYAT= "donation";

	public static final String KIND_LOGIN_USERNAME = "username";
	public static final String KIND_LOGIN_PASSWORD = "password";
	public static final String KIND_LOGIN_EMAIL = "user_email";
	public static final String KIND_LOGIN_FULLNAME = "user_fullname";

	public static final String KIND_CHARGE_DONATION = "";

	public static final String JSON_CHARGE_STATUS = "status";
	public static final String JSON_CHARGE_STATUS_CODE = "status_code";
	public static final String VA_NUMBER = "permata_va_number";

	public static final String JSON_CHARGE_BODY = "body";
	public static final String JSON_CHARGE_PROGRAM_ID = "program_id";
	public static final String JSON_CHARGE_USER_ID = "user_id";
	public static final String JSON_CHARGE_FACULTY = "donation_faculty";
	public static final String JSON_CHARGE_PUBLISHED = "donation_published";
	public static final String JSON_CHARGE_NAME = "donation_name";
	public static final String JSON_CHARGE_AMOUNT = "donation_amount";
	public static final String JSON_CHARGE_DESC = "donation_desc";
	public static final String JSON_CHARGE_EMAIL = "donation_email";
	public static final String JSON_CHARGE_PHONE = "donation_phone";
	public static final String JSON_CHARGE_PICTURE = "donation_picture";
	public static final String JSON_CHARGE_PROGRAM_NAME = "program_name";
	public static final String JSON_METODE_PEMBAYARAN = "donation_metodepembayaran";

	public static final String KIND_HOME = "home";
	public static final String KIND_EVENT_ID = "event_id";
	public static final String KIND_NEWS_ID = "news_id";
	public static final String KIND_PROGRAM_ID = "program_id";
	public static final String KIND_GALLERY_ID = "gallery_id";

	public static final String KIND_TYPE = "type";
	public static final String TAG_TYPE_MOBILE = "mobile";

	public static final String TAG_PROGRAM_ID = "program_id";
	public static final String TAG_PROGRAM_TITLE = "program_title";
	public static final String TAG_PROGRAM_GOAL= "program_goal";
	public static final String TAG_PROGRAM_RAISED = "program_raised";
	public static final String TAG_PROGRAM_DONATOR = "program_donators";
	public static final String TAG_PROGRAM_DESC = "program_desc";
	
	public static final String TAG_EVENT_ID = "event_id";
	public static final String TAG_EVENT_TITLE = "event_title";
	public static final String TAG_EVENT_DATE= "event_date";
//	public static final String TAG_EVENT_TIME= "event_time";
	public static final String TAG_EVENT_TIME= "event_starttime";
	public static final String TAG_EVENT_PLACE= "event_place";
	public static final String TAG_EVENT_DESC = "event_desc";
	public static final String TAG_EVENT_LAT = "event_lat";
	public static final String TAG_EVENT_LONG = "event_long";
	
	public static final String TAG_NEWS_ID = "news_id";
	public static final String TAG_NEWS_TITLE = "news_title";
	public static final String TAG_NEWS_CREATOR= "news_creatorname";
	public static final String TAG_NEWS_DATE= "created_date";
	public static final String TAG_NEWS_DESC = "news_desc";

	public static final String TAG_TESTIMONI_ID = "testimoni_id";
	public static final String TAG_TESTIMONI_CONTENT = "testimoni_content";
	public static final String TAG_TESTIMONI_CREATOR= "testimoni_by";
	public static final String TAG_TESTIMONI_DATE= "url";

	public static final String TAG_DONATION_ID = "donation_id";
	public static final String TAG_DONATION_NAME = "donation_name";
	public static final String TAG_DONATION_TITLE = "program_title";
	public static final String TAG_DONATION_AMOUNT = "donation_amount";
	public static final String TAG_DONATION_FACULTY = "user_faculty";
	public static final String TAG_DONATION_TO_FACULTY = "donation_faculty";
	public static final String TAG_DONATION_IMG = "donation_picture";


	public static final String TAG_ID = "id";
	public static final String TAG_USER_ID = "user_id";
	public static final String TAG_USER_EMAIL = "user_email";
	public static final String TAG_USER_ALAMAT = "user_alamat";
	public static final String TAG_USER_FULLNAME = "user_fullname";
	public static final String TAG_USER_KELAMIN = "user_gender";
	public static final String TAG_USER_IMG_URL = "user_photo";
	public static final String TAG_USER_FAKULTAS = "user_faculty";
	public static final String TAG_USER_TELEPON = "user_telp";

	public static final String TAG_GALLERY_ID = "gallery_id";
	public static final String TAG_GALLERY_TITLE = "gallery_title";
	public static final String TAG_GALLERY_CREATOR= "news_creatorname";
	public static final String TAG_GALLERY_DATE= "created_date";
	public static final String TAG_GALLERY_DESC = "gallery_desc";
	
	public static final String TAG_ARRAY_IMAGES_NAMA = "nama_image";
	public static final String TAG_ARRAY_IMAGES= "images";
	public static final String TAG_ROWCOUNT= "row_count";

	public static final String TAG_FB_NAME= "name";
	public static final String TAG_FB_EMAIL= "email";
	public static final String TAG_FB_PIC= "picture";
	public static final String TAG_FB_GENDER= "gender";

	public static final String TAG_DATA= "data";
	public static final String TAG_JSON_CODE= "json_code";
	public static final String TAG_JSON_ERROR_MESSAGE= "error_message";
	public static final String TAG_JSON_MESSAGE= "message";
	


	public static final String URL_IMG_PROFILE = "http://sahabatmakara.ui.ac.id/admin/photo/default.png";
	public static final String URL_EVENT_NO_IMG = "http://sahabatmakara.ui.ac.id/images/noevent.png";
	public static final String URL_FOLDER_IMG = "/sahabatmakara/images";
	public static String URL_01 = "<html><body><img src=\"";
	public static String URL_02 = "\"></img></body></html>";
}
