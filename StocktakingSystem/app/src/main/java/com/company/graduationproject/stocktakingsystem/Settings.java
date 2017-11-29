package com.company.graduationproject.stocktakingsystem;

/**
 * Created by Nizam on 9/28/2017.
 */

public class Settings {
    public static int VoucherCopiesCount = 1;
    public static String CloseDeliveryAdminPassword = "";
    public static String DEVICEID = null;
    public static int ORDER_CATALOG_IMAGE_ROTATE_DEGREE = 0;
    public static int ORDER_CATALOG_COLUMN = 300;
    //public static String uri="http://192.168.1.24/FirstWebService/WebForm1.aspx";
    public static String uri="http://liveupdate.iscosoft.com:8089/webform1.aspx";
    public static String uri_internal="http://192.168.0.167/shmobwebservice/androidpage.aspx";
    public static String GOOGLE_SENDER_ID = "601532591066";
    public static String REG_ID = "";
    public static boolean USE_INTERNAL_URL=false;
    public static boolean USE_INOICE2=false;
    public static boolean USE_GRAPHICS_PRINT=false;
    public static boolean SHOW_NOTE2=false;
    public static int USERID;
    public static String VERSION;
    public static String USERCODE;
    public static String USERNAME;
    public static String PASSWORD;
    public static String SALESMAN;
    public static String LAST_INVOICE_CODE;
    public static String LAST_RECEIPT_CODE;
    public static String LAST_RETURNED_INVOICE_CODE;
    public static String LAST_PAYMENT_CODE;
    public static String LAST_ORDER_CODE;
    public static int DEFAULT_STORE=-1;
    public static int DELIVARY_ID=-1;
    public static int ROW_HEIGHT;
    public static int DELETE_ORDERS_DAYS=0;
    public static String PRINTER_MAC;
    public static String COMPANY_NAME;
    public static String COMPANY_ADDRESS;
    public static String VAT_REG;
    public static float VAT_RATE;
    public static int GRID_FONT_SIZE;
    public static int FONT_SIZE_PRINT_HEADER;
    public static int FONT_SIZE_PRINT_DETAIL;
    public static int FONT_SIZE_PRINT_SECONDARY;
    public static String CATALOG_IMAGES_PATH;
    public static String SIGNATURES_PATH;
    public static String PhoneID;
    public static String ActivationCode;
    public static boolean LOGIN_ONLINE=false;

    public static String BookCode1;
    public static String BookCode2;

    public static long	Permissions1;
    public static long	Permissions2;
    public static long	Permissions3;

    public static int PERMISSION_INVOICE_DELIVERY=1;
    public static int PERMISSION_RECEIPT=2;
    public static int PERMISSION_ACCOUNTSHEET=4;
    public static int PERMISSION_RETURN_INVOICE=8;
    public static int PERMISSION_ORDERS=16;
    public static int PERMISSION_REPORT=32;
    public static int PERMISSION_INVOICE=64;
    public static int PERMISSION_CHEQS=128;
    public static int PERMISSION_CHANGE_CURRENCY=256;
    public static int PERMISSION_PRICES_CLOSED=512;
    public static int PERMISSION_UNIT_CLOSED=1024;
    public static int PERMISSION_CAR_AS_STORE=2048;
    public static int PERMISSION_CAR_AS_STORE_IN_MINUS=4096;
    public static int PERMISSION_Payment=8192;
    public static int PERMISSION_LAST_PURCHASE_PRICE_REPORT=16384;
    public static int PERMISSION_CHANGE_STORE=32768;
    public static int PERMISSION_PRINT_BALANCE=65536;
    public static int PERMISSION_VISITS=131072;
    public static int PERMISSION_QNTYS_QUERY=262144;
    public static int PERMISSION_ENABLE_GPS=524288;
    public static int PERMISSION_ADD_CUSTOMER=1048576;
    public static int PERMISSION_STOCK_TAKING=2097152;


    public static double DEFAULT_SERVER_DATE = -1;//200001010000.0;

    public static String PrinterType="Zebra";
    public static int PaperWidth=580;
    public static int PrintingLineHeight=25;

    public static boolean VISIT_IS_COMPULSORY;
    public static boolean SaveStockLastGetDate;
    public static boolean SaveStockStoreLastGetDate;
    public static boolean SaveCustomerLastGetDate;
    //public static double StockLastGetDate;
    //public static double CustomerLastGetDate;

    public static int CUSTOMER_OF_VISIT; //ID of Customer Code

    public static boolean CURRENTLY_TRACKING = false;
    public static int INTERVAL_IN_MINUTES = 1;

    public static boolean SaveCustomerPricesLastGetDate;
    public static boolean SetPasswordOnSettings = false;
    public static boolean AllowPrintAccountSheet = true;
}
