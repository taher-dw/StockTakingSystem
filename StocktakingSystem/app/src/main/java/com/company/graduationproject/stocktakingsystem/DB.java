package com.company.graduationproject.stocktakingsystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;



public class DB {

    public enum VoucherType {
        Invoice,
        Receipt,
        Payment,
        Order,
        Returned
    }

    private DBHelper dbHelper;
    private final Context context;
    private SQLiteDatabase shamelDB;

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Printer
    private static final String DATABASE_NAME = "ShamelMobile";
    // users table name
    private static final String TABLE_XXX = "XXX";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CURRENCIES = "currencies";
    private static final String TABLE_RATES = "rates";
    private static final String TABLE_BANK = "banks";
    private static final String TABLE_BRANCH = "branch";
    private static final String TABLE_CHEQ = "cheq";
    private static final String TABLE_CUSTOMER = "customer";
    private static final String TABLE_STOCK = "stock";
    private static final String TABLE_STORE = "store";
    private static final String TABLE_MEASURE = "measure";
    private static final String TABLE_COLOR = "color";
    private static final String TABLE_VOUCHER = "voucher";
    private static final String TABLE_VOUCHERDETAIL = "vch_detail";
    private static final String TABLE_STOCKSTORE = "stock_store";
    private static final String TABLE_CUSTOMERBALANCE = "customer_balance";
    private static final String TABLE_SALESMAN_TRACK_VISIT = "salesman_track_visit";
    private static final String TABLE_SALESMAN_TRACK = "salesman_track";
    private static final String TABLE_VISITS = "visits";
    private static final String TABLE_STOCKBONUS = "stock_bonus";
    private static final String TABLE_LOGGING = "logging";
    private static final String TABLE_INVENTORY_STOCK = "inventory_stock";
    private static final String TABLE_STOCK_TAKING = "stock_taking";
    private static final String TABLE_STOCK_TAKING_SESSIONS = "stock_taking_sessions";
    private static final String TABLE_STOCK_TAKING_TRANSACTIONS = "stock_taking_transactions";
    private static final String TABLE_UNDEFINED_STOCK_TAKING_TRANSACTIONS = "undefined_stock_taking_transactions";
    private static final String TABLE_UNITS = "units";
    private static Context c;

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            c = context;
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {
            UserEntity user = new UserEntity();
            user.createTable(db);
            /*
            CurrencyEntity curr = new CurrencyEntity();
            curr.createTable(db);
            RateEntity rate = new RateEntity();
            rate.createTable(db);
            BankEntity bank = new BankEntity();
            bank.createTable(db);
            BranchEntity branch = new BranchEntity();
            branch.createTable(db);
            CheqEntity cheq = new CheqEntity();
            cheq.createTable(db);

            CustomerEntity customer = new CustomerEntity();
            customer.createTable(db);
            customer.createIndices(db);*/

            StockEntity stock = new StockEntity();
            stock.createTable(db);
            stock.createIndices(db);

            StoreEntity store = new StoreEntity();
            store.createTable(db);
            /*
            StoreEntity store = new StoreEntity();
            store.createTable(db);
            MeasureEntity measure = new MeasureEntity();
            measure.createTable(db);
            ColorEntity color = new ColorEntity();
            color.createTable(db);
            VoucherEntity voucher = new VoucherEntity();
            voucher.createTable(db);
            VchDetailEntity vchDetail = new VchDetailEntity();
            vchDetail.createTable(db);*/

            /*
            StockStoreEntity stockStore = new StockStoreEntity();
            stockStore.createTable(db);
            stockStore.createIndices(db);*/

            /*
            CustomerBalanceEntity custBalance = new CustomerBalanceEntity();
            custBalance.createTable(db);
            SalesmanTrackEntity salesmanTrack = new SalesmanTrackEntity();
            salesmanTrack.createTable(db);
            SalesmanTrackVisitEntity salesmanTrackVisit = new SalesmanTrackVisitEntity();
            salesmanTrackVisit.createTable(db);
            VisitEntity visit = new VisitEntity();
            visit.createTable(db);
            StockBonusEntity stkBons = new StockBonusEntity();
            stkBons.createTable(db);

            GpsRecord.createTable(db);

            CustomerPrice.createTable(db);
            CustomerPrice.createIndices(db);

            Multifl.createTable(db, context);
            Multifl.createIndices(db, context);*/

            LoggingEntity logging = new LoggingEntity();
            logging.createTable(db);

//            InventoryStockEntity inventoyStock = new InventoryStockEntity();
//            inventoyStock.createTable(db);

            StockTakingTransactionsEntity stockTakingTransactionsE = new StockTakingTransactionsEntity();
            stockTakingTransactionsE.createTable(db);

            UndefinedStockTakingTransactionsEntity undefinedStockTakingTransactionsE = new UndefinedStockTakingTransactionsEntity();
            undefinedStockTakingTransactionsE.createTable(db);

            UnitsEntity unitsEntity = new UnitsEntity();
            unitsEntity.createTable(db);

            StockTakingEntity stockTakingEntity = new StockTakingEntity();
            stockTakingEntity.createTable(db);

            StockTakingSessionsEntity stockTakingSessionsEntity = new StockTakingSessionsEntity();
            stockTakingSessionsEntity.createTable(db);


        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {

                /*
                if (oldVersion <= 2) {
                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

                    // create user table
                    UserEntity user = new UserEntity();
                    user.createTable(db);
                }

                if (oldVersion <= 3) {
                    String query = "ALTER TABLE " + TABLE_VOUCHER + " add column " + VoucherEntity.KEY_TIME + " integer default " + Util.ZeroTime;
                    db.execSQL(query);
                    query = "ALTER TABLE " + TABLE_VOUCHER + " add column " + VoucherEntity.KEY_DATE_TO_DELIVER + " integer default " + Util.ZeroDate;
                    db.execSQL(query);
                    query = "ALTER TABLE " + TABLE_VOUCHER + " add column " + VoucherEntity.KEY_TIME_TO_DELIVER + " integer default " + Util.ZeroTime;
                    db.execSQL(query);
                }

                if (oldVersion <= 4) {
                    String query = "ALTER TABLE " + TABLE_STOCK + " add column " + StockEntity.KEY_UNIT_RELATION_2 + " double default "
                            + StockEntity.KEY_UNIT_REL_2_NotUsed;
                    db.execSQL(query);
                    query = "ALTER TABLE " + TABLE_STOCK + " add column " + StockEntity.KEY_UNIT_RELATION_3 + " double default "
                            + StockEntity.KEY_UNIT_REL_3_NotUsed;
                    db.execSQL(query);
                    query = "ALTER TABLE " + TABLE_STOCK + " add column " + StockEntity.KEY_UNIT_RELATION_4 + " double default "
                            + StockEntity.KEY_UNIT_REL_4_NotUsed;
                    db.execSQL(query);
                    query = "ALTER TABLE " + TABLE_STOCK + " add column " + StockEntity.KEY_UNIT_RELATION_5 + " double default "
                            + StockEntity.KEY_UNIT_REL_5_NotUsed;
                    db.execSQL(query);
                    query = "ALTER TABLE " + TABLE_STOCK + " add column " + StockEntity.KEY_UNIT_RELATION_6 + " double default "
                            + StockEntity.KEY_UNIT_REL_6_NotUsed;
                    db.execSQL(query);
                }

                if (oldVersion <= 5) {
                    String query = "ALTER TABLE " + TABLE_STOCK + " add column " + StockEntity.KEY_STOPPED + " integer default 0";
                    db.execSQL(query);
                }

                if (oldVersion <= 6) {
                    String query = "ALTER TABLE " + TABLE_VOUCHER + " add column " + VoucherEntity.KEY_PAYMENT_TYPE + " integer default 0";
                    db.execSQL(query);
                }

                if (oldVersion <= 7) {
                    String query = "ALTER TABLE " + TABLE_VOUCHER + " add column " + VoucherEntity.KEY_NOTE2 + " text";
                    db.execSQL(query);
                }

                if (oldVersion <= 8) {
                    String query = "ALTER TABLE " + TABLE_USERS + " add column " + UserEntity.KEY_BOOK1 + " text default " + Settings.BookCode1;
                    db.execSQL(query);
                    query = "ALTER TABLE " + TABLE_USERS + " add column " + UserEntity.KEY_BOOK2 + " text default " + Settings.BookCode2;
                    db.execSQL(query);
                    query = "ALTER TABLE " + TABLE_USERS + " add column " + UserEntity.KEY_PERMISSION1 + " integer default " + Settings.Permissions1;
                    db.execSQL(query);
                    query = "ALTER TABLE " + TABLE_USERS + " add column " + UserEntity.KEY_PERMISSION2 + " integer default " + Settings.Permissions2;
                    db.execSQL(query);
                    query = "ALTER TABLE " + TABLE_USERS + " add column " + UserEntity.KEY_PERMISSION3 + " integer default " + Settings.Permissions3;
                    db.execSQL(query);

                    StockStoreEntity stockStore = new StockStoreEntity();
                    stockStore.createTable(db);
                }

                if (oldVersion == 9) {
                    String query = "ALTER TABLE " + TABLE_STOCKSTORE + " add column " + StockStoreEntity.KEY_START_QUANTITY + " double";
                    try {
                        db.execSQL(query);
                    } catch (Exception e) {
                        Util.logException(c, e);

                    }
                }

                if (oldVersion <= 10) {
                    String query = "ALTER TABLE " + TABLE_USERS + " add column " + UserEntity.KEY_LAST_PAYMENT + " text";
                    try {
                        db.execSQL(query);
                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 11) {
                    try {
                        String query = "ALTER TABLE " + TABLE_CUSTOMER + " add column " + CustomerEntity.KEY_BALANCE + " real";
                        db.execSQL(query);

                        CustomerBalanceEntity custBalance = new CustomerBalanceEntity();
                        custBalance.createTable(db);
                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 12) {
                    try {
                        String query = "ALTER TABLE " + TABLE_CUSTOMER + " add column " + CustomerEntity.KEY_CREDIT_LIMIT + " real";
                        db.execSQL(query);

                        query = "ALTER TABLE " + TABLE_CUSTOMER + " add column " + CustomerEntity.KEY_CREDIT_LIMIT_TYPE + " integer";
                        db.execSQL(query);

                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 13) {
                    try {
                        String query = "ALTER TABLE " + TABLE_CUSTOMER + " add column " + CustomerEntity.KEY_BALANCE_DATE + " integer default " + Util.ZeroDate;
                        db.execSQL(query);

                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 14) {
                    try {
                        String query = "ALTER TABLE " + TABLE_CUSTOMERBALANCE + " add column " + CustomerBalanceEntity.KEY_RATE + " real";
                        db.execSQL(query);

                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 15) {
                    try {
                        SalesmanTrackEntity salesmanTrack = new SalesmanTrackEntity();
                        salesmanTrack.createTable(db);
                        SalesmanTrackVisitEntity salesmanTrackVisit = new SalesmanTrackVisitEntity();
                        salesmanTrackVisit.createTable(db);
                        VisitEntity visit = new VisitEntity();
                        visit.createTable(db);

                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 16) {
                    try {
                        String query = "ALTER TABLE " + TABLE_VOUCHERDETAIL + " add column " + VchDetailEntity.KEY_NOTE + " text";
                        db.execSQL(query);

                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 16) {
                    try {
                        StockBonusEntity stkBons = new StockBonusEntity();
                        stkBons.createTable(db);
                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 17) {
                    try {
                        String query = "ALTER TABLE " + TABLE_USERS + " add column " + UserEntity.KEY_STOCK_LAST_UPDATE_DATE + " real";
                        db.execSQL(query);
                        query = "ALTER TABLE " + TABLE_USERS + " add column " + UserEntity.KEY_CUST_LAST_UPDATE_DATE + " real";
                        db.execSQL(query);
                        query = "ALTER TABLE " + TABLE_USERS + " add column " + UserEntity.KEY_STOCK_STORE_LAST_UPDATE_DATE + " real";
                        db.execSQL(query);
                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 18) {
                    try {
                        String query = "ALTER TABLE " + TABLE_STOCK + " add column " + StockEntity.KEY_FACTORYNO + " text";
                        db.execSQL(query);
                        query = "ALTER TABLE " + TABLE_STOCK + " add column " + StockEntity.KEY_ORIGNALNO + " text";
                        db.execSQL(query);
                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 19) {
                    try {
                        GpsRecord.createTable(db);

                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 20) {
                    try {
                        CustomerPrice.createTable(db);
                        String query = "ALTER TABLE " + TABLE_USERS + " add column " + UserEntity.KEY_CUST_PRICES_LAST_UPDATE_DATE + " REAL";
                        db.execSQL(query);
                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 21) {
                    try {
                        new StockEntity().createIndices(db);
                        new StockStoreEntity().createIndices(db);
                        new CustomerEntity().createIndices(db);
                        CustomerPrice.createIndices(db);
                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 22) {
                    try {
                        String query = "ALTER TABLE " + TABLE_VOUCHERDETAIL + " add column " + VchDetailEntity.KEY_STOCKNAME + " text";
                        db.execSQL(query);
                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }

                if (oldVersion <= 23) {
                    try {
                        Multifl.createTable(db, context);
                        Multifl.createIndices(db, context);

                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                }*/

                //if (oldVersion <= 24) {

                    try {
                        LoggingEntity logE = new LoggingEntity();
                        logE.createTable(db);
                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                //}
                //if (oldVersion <= 25) {

                /*
                    try {
                        String query = "ALTER TABLE " + TABLE_VOUCHER + " add column " + VoucherEntity.KEY_SIGNATURE_STATUS + " integer DEFAULT 1 ";
                        db.execSQL(query);
                    } catch (Exception e) {
                        Util.logException(c, e);
                    }*/
                //}


                //if (oldVersion <= 28) {

                    try {
                        StockTakingTransactionsEntity stockTakingTransactionsE = new StockTakingTransactionsEntity();
                        stockTakingTransactionsE.createTable(db);

                        StoreEntity store = new StoreEntity();
                        store.createTable(db);

                        UndefinedStockTakingTransactionsEntity undefinedStockTakingTransactionsE = new UndefinedStockTakingTransactionsEntity();
                        undefinedStockTakingTransactionsE.createTable(db);

                        UnitsEntity unitsEntity = new UnitsEntity();
                        unitsEntity.createTable(db);

                        StockTakingEntity stockTakingEntity = new StockTakingEntity();
                        stockTakingEntity.createTable(db);

                        StockTakingSessionsEntity stockTakingSessionsEntity = new StockTakingSessionsEntity();
                        stockTakingSessionsEntity.createTable(db);

                    } catch (Exception e) {
                        Util.logException(c, e);
                    }
                //}
                // */


            } catch (SQLException e) {
                Util.logException(c, e);
                throw e;
            }
        }
    }

    // constructor
    public DB(Context c) {
        context = c;
    }

    // open database
    public void open(boolean isWritable) {
        if (shamelDB != null && shamelDB.isOpen())
            return;

        dbHelper = new DBHelper(context);
        shamelDB = dbHelper.getWritableDatabase();
    }

    // close database
    public void close() {
        if (shamelDB != null && shamelDB.isOpen())
            shamelDB.close();
    }

    // begin transaction
    public void beginTransaction() {
        shamelDB.beginTransaction();
    }

    // commit the transaction
    public void setTransactionSuccessful() {
        shamelDB.setTransactionSuccessful();
    }

    // end transaction
    public void endTransaction() {
        shamelDB.endTransaction();
    }

    public SQLiteDatabase getShamelDB() {
        return shamelDB;
    }

    public static int SUCCEEDED = 0;
    public static int FAILED_QUANTITY_OVERFLOW = 1;
    public static int FAILED_SEELOGFILE = 2;
    public static int FAILED_CODEDUPLICATE = 3;

    public static String quantityOverFlowMessageBox;
    public static String codeDublicateMessageBox;


    // ///////////////////////////////USER/////////////////////
    public class User {
        public int id = -1, default_store;
        public String code, name, password, salesman, last_invoice, last_receipt, last_returned_invoice, last_payment;
        public String book1, book2;
        public long permission1, permission2, permission3;
        public double stockLastUpdateDate, custLastUpdateDate, stockStoreLastUpdateDate, custPricesLastUpdateDate;

        public User() {
            id = -1;
            permission1 = permission2 = permission3 = default_store = 0;
            code = name = password = salesman = last_invoice = last_receipt = last_payment = last_returned_invoice = "";
            book1 = book2 = "";
            stockLastUpdateDate = custLastUpdateDate = custPricesLastUpdateDate = stockStoreLastUpdateDate = -1;//200001010000.0;//Double.parseDouble("200001010000");
        }
    }

    public class UserEntity {

        // Users Table Columns names
        public static final String KEY_ID = "id";
        public static final String KEY_CODE = "code";
        public static final String KEY_NAME = "name";
        public static final String KEY_PASSWORD = "password";
        public static final String KEY_SALESMANCODE = "salesman";
        public static final String KEY_LAST_INVOICE = "last_invoice";
        public static final String KEY_LAST_RECEIPT = "last_receipt";
        public static final String KEY_LAST_RETURNED_INVOICE = "last_returned_invoice";
        public static final String KEY_LAST_PAYMENT = "last_payment";
        public static final String KEY_DEFAULT_STORE = "default_store";
        public static final String KEY_BOOK1 = "book1";
        public static final String KEY_BOOK2 = "book2";
        public static final String KEY_PERMISSION1 = "permission1";
        public static final String KEY_PERMISSION2 = "permission2";
        public static final String KEY_PERMISSION3 = "permission3";
        public static final String KEY_STOCK_LAST_UPDATE_DATE = "stock_last_update_date";
        public static final String KEY_CUST_LAST_UPDATE_DATE = "cust_last_update_date";
        public static final String KEY_STOCK_STORE_LAST_UPDATE_DATE = "stock_store_last_update_date";
        public static final String KEY_CUST_PRICES_LAST_UPDATE_DATE = "cust_prices_last_update_date";
        // used to make the copy paste more easy
        private final String TABLENAME = TABLE_USERS;

        String[] AllColumns = {KEY_ID, KEY_CODE, KEY_NAME, KEY_PASSWORD, KEY_SALESMANCODE, KEY_LAST_INVOICE, KEY_LAST_RECEIPT, KEY_LAST_PAYMENT,
                KEY_LAST_RETURNED_INVOICE, KEY_DEFAULT_STORE, KEY_BOOK1, KEY_BOOK2, KEY_PERMISSION1, KEY_PERMISSION2, KEY_PERMISSION3,
                KEY_STOCK_LAST_UPDATE_DATE, KEY_CUST_LAST_UPDATE_DATE, KEY_STOCK_STORE_LAST_UPDATE_DATE, KEY_CUST_PRICES_LAST_UPDATE_DATE};

        public void createTable(SQLiteDatabase db) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "("
                        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CODE
                        + " TEXT," + KEY_NAME + " TEXT," + KEY_PASSWORD
                        + " TEXT," + KEY_SALESMANCODE + " TEXT,"
                        + KEY_LAST_INVOICE + " TEXT," + KEY_LAST_RECEIPT
                        + " TEXT," + KEY_LAST_PAYMENT + " TEXT,"
                        + KEY_LAST_RETURNED_INVOICE + " TEXT,"
                        + KEY_DEFAULT_STORE + " INTEGER," + KEY_BOOK1
                        + " TEXT," + KEY_BOOK2 + " TEXT," + KEY_PERMISSION1
                        + " INTEGER," + KEY_PERMISSION2 + " INTEGER,"
                        + KEY_PERMISSION3 + " INTEGER,"
                        + KEY_STOCK_LAST_UPDATE_DATE + " REAL,"
                        + KEY_CUST_LAST_UPDATE_DATE + " REAL,"
                        + KEY_STOCK_STORE_LAST_UPDATE_DATE + " REAL,"
                        + KEY_CUST_PRICES_LAST_UPDATE_DATE + " REAL"
                        + ")";
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                Util.logException(c, e);
            }
        }

        // the default object
        // you can use this object to minimize the code
        public User obj;

        public UserEntity() {
            obj = new User();
        }

        public boolean insert(User _obj) {
            try {
                // insert in the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_ID, _obj.id);
                cv.put(KEY_CODE, _obj.code);
                cv.put(KEY_NAME, _obj.name);
                cv.put(KEY_PASSWORD, _obj.password);
                cv.put(KEY_SALESMANCODE, _obj.salesman);
                cv.put(KEY_LAST_INVOICE, _obj.last_invoice);
                cv.put(KEY_LAST_RECEIPT, _obj.last_receipt);
                cv.put(KEY_LAST_PAYMENT, _obj.last_payment);
                cv.put(KEY_LAST_RETURNED_INVOICE, _obj.last_returned_invoice);
                cv.put(KEY_DEFAULT_STORE, _obj.default_store);
                cv.put(KEY_BOOK1, _obj.book1);
                cv.put(KEY_BOOK2, _obj.book2);
                cv.put(KEY_PERMISSION1, _obj.permission1);
                cv.put(KEY_PERMISSION2, _obj.permission2);
                cv.put(KEY_PERMISSION3, _obj.permission3);
                cv.put(KEY_STOCK_LAST_UPDATE_DATE, _obj.stockLastUpdateDate);
                cv.put(KEY_CUST_LAST_UPDATE_DATE, _obj.custLastUpdateDate);
                cv.put(KEY_STOCK_STORE_LAST_UPDATE_DATE, _obj.stockStoreLastUpdateDate);
                cv.put(KEY_CUST_PRICES_LAST_UPDATE_DATE, _obj.custPricesLastUpdateDate);

                long rowID = shamelDB.insert(TABLENAME, null, cv);
                if (rowID == -1) {
                    Util.logInformation(c, "insert into " + TABLENAME + " return -1 key=" + _obj.id);
                    return false;
                }
                return true;

            } catch (Exception e) {
                Util.logInformation(c, "insert method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean update(User _obj) {
            try {
                // update the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_CODE, _obj.code);
                cv.put(KEY_NAME, _obj.name);
                cv.put(KEY_PASSWORD, _obj.password);
                cv.put(KEY_SALESMANCODE, _obj.salesman);
                cv.put(KEY_LAST_INVOICE, _obj.last_invoice);
                cv.put(KEY_LAST_RECEIPT, _obj.last_receipt);
                cv.put(KEY_LAST_PAYMENT, _obj.last_payment);
                cv.put(KEY_LAST_RETURNED_INVOICE, _obj.last_returned_invoice);
                cv.put(KEY_DEFAULT_STORE, _obj.default_store);
                cv.put(KEY_BOOK1, _obj.book1);
                cv.put(KEY_BOOK2, _obj.book2);
                cv.put(KEY_PERMISSION1, _obj.permission1);
                cv.put(KEY_PERMISSION2, _obj.permission2);
                cv.put(KEY_PERMISSION3, _obj.permission3);
                cv.put(KEY_STOCK_LAST_UPDATE_DATE, _obj.stockLastUpdateDate);
                cv.put(KEY_CUST_LAST_UPDATE_DATE, _obj.custLastUpdateDate);
                cv.put(KEY_STOCK_STORE_LAST_UPDATE_DATE, _obj.stockStoreLastUpdateDate);
                cv.put(KEY_CUST_PRICES_LAST_UPDATE_DATE, _obj.custPricesLastUpdateDate);
                int recordCount = shamelDB.update(TABLENAME, cv, KEY_ID + "=" + _obj.id, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "update more than one record from " + TABLENAME + " key=" + _obj.id);
                    return true;
                } else {
                    Util.logInformation(c, "update from " + TABLENAME + " has no row to update" + " key=" + _obj.id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "update method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean delete(int _id) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, KEY_ID + "=" + _id, null);

                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "delete more than one row from " + TABLENAME + " key=" + _id);
                    return true;
                } else {
                    Util.logInformation(c, "delete from " + TABLENAME + " has no row to delete " + " key=" + _id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean get(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.code = c.getString(c.getColumnIndex(KEY_CODE));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));
                    obj.password = c.getString(c.getColumnIndex(KEY_PASSWORD));
                    obj.salesman = c.getString(c.getColumnIndex(KEY_SALESMANCODE));
                    obj.last_invoice = c.getString(c.getColumnIndex(KEY_LAST_INVOICE));
                    obj.last_receipt = c.getString(c.getColumnIndex(KEY_LAST_RECEIPT));
                    obj.last_payment = c.getString(c.getColumnIndex(KEY_LAST_PAYMENT));
                    obj.last_returned_invoice = c.getString(c.getColumnIndex(KEY_LAST_RETURNED_INVOICE));
                    obj.default_store = c.getInt(c.getColumnIndex(KEY_DEFAULT_STORE));
                    obj.book1 = c.getString(c.getColumnIndex(KEY_BOOK1));
                    obj.book2 = c.getString(c.getColumnIndex(KEY_BOOK2));
                    obj.permission1 = c.getLong(c.getColumnIndex(KEY_PERMISSION1));
                    obj.permission2 = c.getLong(c.getColumnIndex(KEY_PERMISSION2));
                    obj.permission3 = c.getLong(c.getColumnIndex(KEY_PERMISSION3));
                    obj.stockLastUpdateDate = c.getDouble(c.getColumnIndex(KEY_STOCK_LAST_UPDATE_DATE));
                    obj.custLastUpdateDate = c.getDouble(c.getColumnIndex(KEY_CUST_LAST_UPDATE_DATE));
                    obj.stockStoreLastUpdateDate = c.getDouble(c.getColumnIndex(KEY_STOCK_STORE_LAST_UPDATE_DATE));
                    obj.custPricesLastUpdateDate = c.getDouble(c.getColumnIndex(KEY_CUST_PRICES_LAST_UPDATE_DATE));
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public List<User> get(String where) {
            List<User> arr = new ArrayList<User>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                Cursor c = shamelDB.query(TABLENAME, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    User obj = new User();
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.code = c.getString(c.getColumnIndex(KEY_CODE));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));
                    obj.password = c.getString(c.getColumnIndex(KEY_PASSWORD));
                    obj.salesman = c.getString(c.getColumnIndex(KEY_SALESMANCODE));
                    obj.last_invoice = c.getString(c.getColumnIndex(KEY_LAST_INVOICE));
                    obj.last_receipt = c.getString(c.getColumnIndex(KEY_LAST_RECEIPT));
                    obj.last_payment = c.getString(c.getColumnIndex(KEY_LAST_PAYMENT));
                    obj.last_returned_invoice = c.getString(c.getColumnIndex(KEY_LAST_RETURNED_INVOICE));
                    obj.default_store = c.getInt(c.getColumnIndex(KEY_DEFAULT_STORE));
                    obj.book1 = c.getString(c.getColumnIndex(KEY_BOOK1));
                    obj.book2 = c.getString(c.getColumnIndex(KEY_BOOK2));
                    obj.permission1 = c.getLong(c.getColumnIndex(KEY_PERMISSION1));
                    obj.permission2 = c.getLong(c.getColumnIndex(KEY_PERMISSION2));
                    obj.permission3 = c.getLong(c.getColumnIndex(KEY_PERMISSION3));
                    obj.stockLastUpdateDate = c.getDouble(c.getColumnIndex(KEY_STOCK_LAST_UPDATE_DATE));
                    obj.custLastUpdateDate = c.getDouble(c.getColumnIndex(KEY_CUST_LAST_UPDATE_DATE));
                    obj.stockStoreLastUpdateDate = c.getDouble(c.getColumnIndex(KEY_STOCK_STORE_LAST_UPDATE_DATE));
                    obj.custPricesLastUpdateDate = c.getDouble(c.getColumnIndex(KEY_CUST_PRICES_LAST_UPDATE_DATE));
                    arr.add(obj);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }

        public boolean checkExisting(int _id) {
            try {
                String[] Columns = {KEY_ID};
                Cursor c = shamelDB.query(TABLENAME, Columns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public int checkExisting(String where) {
            int _id = -1;
            try {
                String[] Columns = {KEY_ID};
                Cursor c = shamelDB.query(TABLENAME, Columns, where, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    _id = c.getInt(c.getColumnIndex(KEY_ID));
                }
                c.close();
                return _id;
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return -1;
            }
        }
    }


    // ///////////////////////////////Stock/////////////////////
    public class Stock {
        public int id, stopped;
        public double unit_rel_2, unit_rel_3, unit_rel_4, unit_rel_5, unit_rel_6;
        public String code, name, barcode, unit_1, unit_2, unit_3, unit_4, unit_5, unit_6, factoryNo, orignalNo;
        public double price_1, price_2, price_3, price_4, price_5, price_6;

        public Stock() {
            id = -1;
            unit_rel_2 = unit_rel_3 = unit_rel_4 = unit_rel_5 = unit_rel_6 = 1.0;
            code = name = barcode = unit_1 = unit_2 = unit_3 = unit_4 = unit_5 = unit_6 = factoryNo = orignalNo = "";
            price_1 = price_2 = price_3 = price_4 = price_5 = price_6 = 0.0;
            stopped = 0;
        }
    }

    public class StockEntity {
        // Users Table Columns names
        public static final String KEY_ID = "id";
        public static final String KEY_CODE = "code";
        public static final String KEY_NAME = "name";
        public static final String KEY_BARCODE = "barcode";
        public static final String KEY_FACTORYNO = "factory_no";
        public static final String KEY_ORIGNALNO = "orignal_no";
        public static final String KEY_UNIT_1 = "unit_1";
        public static final String KEY_UNIT_2 = "unit_2";
        public static final String KEY_UNIT_3 = "unit_3";
        public static final String KEY_UNIT_4 = "unit_4";
        public static final String KEY_UNIT_5 = "unit_5";
        public static final String KEY_UNIT_6 = "unit_6";
        public static final String KEY_UNIT_REL_2_NotUsed = "unit_rel_2";
        public static final String KEY_UNIT_REL_3_NotUsed = "unit_rel_3";
        public static final String KEY_UNIT_REL_4_NotUsed = "unit_rel_4";
        public static final String KEY_UNIT_REL_5_NotUsed = "unit_rel_5";
        public static final String KEY_UNIT_REL_6_NotUsed = "unit_rel_6";
        public static final String KEY_PRICE_1 = "price_1";
        public static final String KEY_PRICE_2 = "price_2";
        public static final String KEY_PRICE_3 = "price_3";
        public static final String KEY_PRICE_4 = "price_4";
        public static final String KEY_PRICE_5 = "price_5";
        public static final String KEY_PRICE_6 = "price_6";
        public static final String KEY_UNIT_RELATION_2 = "unit_relation_2";
        public static final String KEY_UNIT_RELATION_3 = "unit_relation_3";
        public static final String KEY_UNIT_RELATION_4 = "unit_relation_4";
        public static final String KEY_UNIT_RELATION_5 = "unit_relation_5";
        public static final String KEY_UNIT_RELATION_6 = "unit_relation_6";
        public static final String KEY_STOPPED = "stopped";

        String[] AllColumns = {KEY_ID, KEY_CODE, KEY_NAME, KEY_BARCODE, KEY_UNIT_1, KEY_UNIT_2, KEY_UNIT_3, KEY_UNIT_4, KEY_UNIT_5, KEY_UNIT_6,
                KEY_UNIT_REL_2_NotUsed, KEY_UNIT_REL_3_NotUsed, KEY_UNIT_REL_4_NotUsed, KEY_UNIT_REL_5_NotUsed, KEY_UNIT_REL_6_NotUsed, KEY_PRICE_1,
                KEY_PRICE_2, KEY_PRICE_3, KEY_PRICE_4, KEY_PRICE_5, KEY_PRICE_6, KEY_UNIT_RELATION_2, KEY_UNIT_RELATION_3, KEY_UNIT_RELATION_4,
                KEY_UNIT_RELATION_5, KEY_UNIT_RELATION_6, KEY_STOPPED, KEY_FACTORYNO, KEY_ORIGNALNO};

        // used to make the copy paste more easy
        private final String TABLENAME = TABLE_STOCK;

        public void createTable(SQLiteDatabase db) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CODE + " TEXT," + KEY_NAME + " TEXT,"
                        + KEY_BARCODE + " TEXT," + KEY_UNIT_1 + " TEXT," + KEY_UNIT_2 + " TEXT," + KEY_UNIT_3 + " TEXT," + KEY_UNIT_4 + " TEXT," + KEY_UNIT_5
                        + " TEXT," + KEY_UNIT_6 + " TEXT," + KEY_UNIT_REL_2_NotUsed + " INTEGER," + KEY_UNIT_REL_3_NotUsed + " INTEGER,"
                        + KEY_UNIT_REL_4_NotUsed + " INTEGER," + KEY_UNIT_REL_5_NotUsed + " INTEGER," + KEY_UNIT_REL_6_NotUsed + " INTEGER," + KEY_PRICE_1
                        + " DOUBLE," + KEY_PRICE_2 + " DOUBLE," + KEY_PRICE_3 + " DOUBLE," + KEY_PRICE_4 + " DOUBLE," + KEY_PRICE_5 + " DOUBLE," + KEY_PRICE_6
                        + " DOUBLE," + KEY_UNIT_RELATION_2 + " DOUBLE," + KEY_UNIT_RELATION_3 + " DOUBLE," + KEY_UNIT_RELATION_4 + " DOUBLE,"
                        + KEY_UNIT_RELATION_5 + " DOUBLE," + KEY_UNIT_RELATION_6 + " DOUBLE," + KEY_STOPPED + " INTEGER,"
                        + KEY_FACTORYNO + " TEXT," + KEY_ORIGNALNO + " TEXT"
                        + ")";
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                Util.logException(c, e);
            }
        }

        public void createIndices(SQLiteDatabase db) {
            try {
                StringBuilder sql = new StringBuilder("");
                sql.append("CREATE INDEX index_").append(TABLENAME).append("_").append(KEY_CODE)
                        .append(" on ").append(TABLENAME)
                        .append(" (").append(KEY_CODE).append(")");

                db.execSQL(sql.toString());
            } catch (Exception ex) {
                Util.logException(c, ex);
            }
        }

        // the default object
        // you can use this object to minimize the code
        public Stock obj;

        public StockEntity() {
            obj = new Stock();
        }

        public boolean insert(Stock _obj) {
            try {
                // insert in the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_CODE, _obj.code);
                cv.put(KEY_NAME, _obj.name);
                cv.put(KEY_BARCODE, _obj.barcode);
                cv.put(KEY_FACTORYNO, _obj.factoryNo);
                cv.put(KEY_ORIGNALNO, _obj.orignalNo);
                cv.put(KEY_UNIT_1, _obj.unit_1);
                cv.put(KEY_UNIT_2, _obj.unit_2);
                cv.put(KEY_UNIT_3, _obj.unit_3);
                cv.put(KEY_UNIT_4, _obj.unit_4);
                cv.put(KEY_UNIT_5, _obj.unit_5);
                cv.put(KEY_UNIT_6, _obj.unit_6);
                cv.put(KEY_UNIT_RELATION_2, _obj.unit_rel_2);
                cv.put(KEY_UNIT_RELATION_3, _obj.unit_rel_3);
                cv.put(KEY_UNIT_RELATION_4, _obj.unit_rel_4);
                cv.put(KEY_UNIT_RELATION_5, _obj.unit_rel_5);
                cv.put(KEY_UNIT_RELATION_6, _obj.unit_rel_6);
                cv.put(KEY_PRICE_1, _obj.price_1);
                cv.put(KEY_PRICE_2, _obj.price_2);
                cv.put(KEY_PRICE_3, _obj.price_3);
                cv.put(KEY_PRICE_4, _obj.price_4);
                cv.put(KEY_PRICE_5, _obj.price_5);
                cv.put(KEY_PRICE_6, _obj.price_6);
                cv.put(KEY_STOPPED, _obj.stopped);
                long rowID = shamelDB.insert(TABLENAME, null, cv);
                if (rowID == -1) {
                    Util.logInformation(c, "insert into " + TABLENAME + " return -1 key=" + _obj.id);
                    return false;
                }
                return true;

            } catch (Exception e) {
                Util.logInformation(c, "insert method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean update(Stock _obj) {
            try {
                // update the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_CODE, _obj.code);
                cv.put(KEY_NAME, _obj.name);
                cv.put(KEY_BARCODE, _obj.barcode);
                cv.put(KEY_FACTORYNO, _obj.factoryNo);
                cv.put(KEY_ORIGNALNO, _obj.orignalNo);
                cv.put(KEY_UNIT_1, _obj.unit_1);
                cv.put(KEY_UNIT_2, _obj.unit_2);
                cv.put(KEY_UNIT_3, _obj.unit_3);
                cv.put(KEY_UNIT_4, _obj.unit_4);
                cv.put(KEY_UNIT_5, _obj.unit_5);
                cv.put(KEY_UNIT_6, _obj.unit_6);
                cv.put(KEY_UNIT_RELATION_2, _obj.unit_rel_2);
                cv.put(KEY_UNIT_RELATION_3, _obj.unit_rel_3);
                cv.put(KEY_UNIT_RELATION_4, _obj.unit_rel_4);
                cv.put(KEY_UNIT_RELATION_5, _obj.unit_rel_5);
                cv.put(KEY_UNIT_RELATION_6, _obj.unit_rel_6);
                cv.put(KEY_PRICE_1, _obj.price_1);
                cv.put(KEY_PRICE_2, _obj.price_2);
                cv.put(KEY_PRICE_3, _obj.price_3);
                cv.put(KEY_PRICE_4, _obj.price_4);
                cv.put(KEY_PRICE_5, _obj.price_5);
                cv.put(KEY_PRICE_6, _obj.price_6);
                cv.put(KEY_STOPPED, _obj.stopped);
                int recordCount = shamelDB.update(TABLENAME, cv, KEY_ID + "=" + _obj.id, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "update more than one record from " + TABLENAME + " key=" + _obj.id);
                    return true;
                } else {
                    Util.logInformation(c, "update from " + TABLENAME + " has no row to update" + " key=" + _obj.id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "update method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean disableAllStock(String[] codes) {
            try {/*
                String where = "";
                for (String str : codes
                        ) {
                    if (!where.isEmpty()) where += ",";
                    where += "'";
                    where += str;
                    where += "'";
                }
                if (!where.isEmpty()) {
                    where = KEY_CODE + " not in (" + where + ")";
                }
                // update the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_STOPPED, 2);
                int recordCount = shamelDB.update(TABLENAME, cv, where, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        ;

                } else {
                    Util.logInformation(c, "disableAllStock has no row to update");
                }*/
                return true;
            } catch (Exception e) {
                Util.logInformation(c, "disableAllStock method in " + TABLENAME + " throw an exception ");
                Util.logException(c, e);
                return false;
            }
        }

        public boolean delete(int _id) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, KEY_ID + "=" + _id, null);

                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "delete more than one row from " + TABLENAME + " key=" + _id);
                    return true;
                } else {
                    Util.logInformation(c, "delete from " + TABLENAME + " has no row to delete " + " key=" + _id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean delete(String where) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, where, null);
                return true;
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean get(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.code = c.getString(c.getColumnIndex(KEY_CODE));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));
                    obj.barcode = c.getString(c.getColumnIndex(KEY_BARCODE));
                    obj.factoryNo = c.getString(c.getColumnIndex(KEY_FACTORYNO));
                    obj.orignalNo = c.getString(c.getColumnIndex(KEY_ORIGNALNO));
                    obj.unit_1 = c.getString(c.getColumnIndex(KEY_UNIT_1));
                    obj.unit_2 = c.getString(c.getColumnIndex(KEY_UNIT_2));
                    obj.unit_3 = c.getString(c.getColumnIndex(KEY_UNIT_3));
                    obj.unit_4 = c.getString(c.getColumnIndex(KEY_UNIT_4));
                    obj.unit_5 = c.getString(c.getColumnIndex(KEY_UNIT_5));
                    obj.unit_6 = c.getString(c.getColumnIndex(KEY_UNIT_6));
                    obj.unit_rel_2 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_2));
                    obj.unit_rel_3 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_3));
                    obj.unit_rel_4 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_4));
                    obj.unit_rel_5 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_5));
                    obj.unit_rel_6 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_6));
                    obj.price_1 = c.getDouble(c.getColumnIndex(KEY_PRICE_1));
                    obj.price_2 = c.getDouble(c.getColumnIndex(KEY_PRICE_2));
                    obj.price_3 = c.getDouble(c.getColumnIndex(KEY_PRICE_3));
                    obj.price_4 = c.getDouble(c.getColumnIndex(KEY_PRICE_4));
                    obj.price_5 = c.getDouble(c.getColumnIndex(KEY_PRICE_5));
                    obj.price_6 = c.getDouble(c.getColumnIndex(KEY_PRICE_6));
                    obj.stopped = c.getInt(c.getColumnIndex(KEY_STOPPED));

                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public Stock getItem(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.code = c.getString(c.getColumnIndex(KEY_CODE));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));
                    obj.barcode = c.getString(c.getColumnIndex(KEY_BARCODE));
                    obj.factoryNo = c.getString(c.getColumnIndex(KEY_FACTORYNO));
                    obj.orignalNo = c.getString(c.getColumnIndex(KEY_ORIGNALNO));
                    obj.unit_1 = c.getString(c.getColumnIndex(KEY_UNIT_1));
                    obj.unit_2 = c.getString(c.getColumnIndex(KEY_UNIT_2));
                    obj.unit_3 = c.getString(c.getColumnIndex(KEY_UNIT_3));
                    obj.unit_4 = c.getString(c.getColumnIndex(KEY_UNIT_4));
                    obj.unit_5 = c.getString(c.getColumnIndex(KEY_UNIT_5));
                    obj.unit_6 = c.getString(c.getColumnIndex(KEY_UNIT_6));
                    obj.unit_rel_2 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_2));
                    obj.unit_rel_3 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_3));
                    obj.unit_rel_4 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_4));
                    obj.unit_rel_5 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_5));
                    obj.unit_rel_6 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_6));
                    obj.price_1 = c.getDouble(c.getColumnIndex(KEY_PRICE_1));
                    obj.price_2 = c.getDouble(c.getColumnIndex(KEY_PRICE_2));
                    obj.price_3 = c.getDouble(c.getColumnIndex(KEY_PRICE_3));
                    obj.price_4 = c.getDouble(c.getColumnIndex(KEY_PRICE_4));
                    obj.price_5 = c.getDouble(c.getColumnIndex(KEY_PRICE_5));
                    obj.price_6 = c.getDouble(c.getColumnIndex(KEY_PRICE_6));
                    obj.stopped = c.getInt(c.getColumnIndex(KEY_STOPPED));

                    c.close();
                    return obj;
                } else {
                    c.close();
                    return null;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return null;
            }
        }
        public Stock getItemByCode(String code) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_CODE + "= '" + code + "'", null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.code = c.getString(c.getColumnIndex(KEY_CODE));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));
                    obj.barcode = c.getString(c.getColumnIndex(KEY_BARCODE));
                    obj.factoryNo = c.getString(c.getColumnIndex(KEY_FACTORYNO));
                    obj.orignalNo = c.getString(c.getColumnIndex(KEY_ORIGNALNO));
                    obj.unit_1 = c.getString(c.getColumnIndex(KEY_UNIT_1));
                    obj.unit_2 = c.getString(c.getColumnIndex(KEY_UNIT_2));
                    obj.unit_3 = c.getString(c.getColumnIndex(KEY_UNIT_3));
                    obj.unit_4 = c.getString(c.getColumnIndex(KEY_UNIT_4));
                    obj.unit_5 = c.getString(c.getColumnIndex(KEY_UNIT_5));
                    obj.unit_6 = c.getString(c.getColumnIndex(KEY_UNIT_6));
                    obj.unit_rel_2 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_2));
                    obj.unit_rel_3 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_3));
                    obj.unit_rel_4 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_4));
                    obj.unit_rel_5 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_5));
                    obj.unit_rel_6 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_6));
                    obj.price_1 = c.getDouble(c.getColumnIndex(KEY_PRICE_1));
                    obj.price_2 = c.getDouble(c.getColumnIndex(KEY_PRICE_2));
                    obj.price_3 = c.getDouble(c.getColumnIndex(KEY_PRICE_3));
                    obj.price_4 = c.getDouble(c.getColumnIndex(KEY_PRICE_4));
                    obj.price_5 = c.getDouble(c.getColumnIndex(KEY_PRICE_5));
                    obj.price_6 = c.getDouble(c.getColumnIndex(KEY_PRICE_6));
                    obj.stopped = c.getInt(c.getColumnIndex(KEY_STOPPED));

                    c.close();
                    return obj;
                } else {
                    c.close();
                    return null;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + code);
                Util.logException(c, e);
                return null;
            }
        }

        public double getUnitRelation(int _id, int unitNum) {
            try {

                double unitRelation = 1.0;
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));

                    switch (unitNum) {
                        case 1:
                            unitRelation = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_2));
                            break;
                        case 2:
                            unitRelation = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_3));
                            break;
                        case 3:
                            unitRelation = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_4));
                            break;
                        case 4:
                            unitRelation = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_5));
                            break;
                        case 5:
                            unitRelation = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_6));
                            break;
                    }

                    c.close();
                    return unitRelation;
                } else {
                    c.close();
                    return 1.0;
                }
            } catch (Exception e) {
                Util.logInformation(c, "getUnitRelation by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return 1.0;
            }
        }

        public List<Stock> get(String where, boolean evenStopped) {
            List<Stock> arr = new ArrayList<Stock>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0) {
                    whereCol = where;
                    if (!evenStopped) {
                        whereCol += " and ";
                        whereCol += KEY_STOPPED + "<>2";
                    }
                } else {
                    if (!evenStopped)
                        whereCol = KEY_STOPPED + "<>2";
                }

                Cursor c = shamelDB.query(TABLENAME, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    Stock obj = new Stock();
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.code = c.getString(c.getColumnIndex(KEY_CODE));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));
                    obj.barcode = c.getString(c.getColumnIndex(KEY_BARCODE));
                    obj.factoryNo = c.getString(c.getColumnIndex(KEY_FACTORYNO));
                    obj.orignalNo = c.getString(c.getColumnIndex(KEY_ORIGNALNO));
                    obj.unit_1 = c.getString(c.getColumnIndex(KEY_UNIT_1));
                    obj.unit_2 = c.getString(c.getColumnIndex(KEY_UNIT_2));
                    obj.unit_3 = c.getString(c.getColumnIndex(KEY_UNIT_3));
                    obj.unit_4 = c.getString(c.getColumnIndex(KEY_UNIT_4));
                    obj.unit_5 = c.getString(c.getColumnIndex(KEY_UNIT_5));
                    obj.unit_6 = c.getString(c.getColumnIndex(KEY_UNIT_6));
                    obj.unit_rel_2 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_2));
                    obj.unit_rel_3 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_3));
                    obj.unit_rel_4 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_4));
                    obj.unit_rel_5 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_5));
                    obj.unit_rel_6 = c.getDouble(c.getColumnIndex(KEY_UNIT_RELATION_6));
                    obj.price_1 = c.getDouble(c.getColumnIndex(KEY_PRICE_1));
                    obj.price_2 = c.getDouble(c.getColumnIndex(KEY_PRICE_2));
                    obj.price_3 = c.getDouble(c.getColumnIndex(KEY_PRICE_3));
                    obj.price_4 = c.getDouble(c.getColumnIndex(KEY_PRICE_4));
                    obj.price_5 = c.getDouble(c.getColumnIndex(KEY_PRICE_5));
                    obj.price_6 = c.getDouble(c.getColumnIndex(KEY_PRICE_6));
                    obj.stopped = c.getInt(c.getColumnIndex(KEY_STOPPED));

                    arr.add(obj);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }


        public ArrayList<String> getSearchByAnyFieldValue(String fieldType, String value) {
            ArrayList<String> arr = new ArrayList<String>();
            try {


                String tmpstr = "";
                String selectQuery = "SELECT "+KEY_ID+","+fieldType+ " FROM " + TABLENAME + " where UPPER( " + fieldType + ") like '%" + value + "%'";

                shamelDB = dbHelper.getReadableDatabase();

                Cursor c = shamelDB.rawQuery(selectQuery, null);

                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                    tmpstr = c.getInt(c.getColumnIndex(KEY_ID)) + "#";
                    tmpstr += c.getString(c.getColumnIndex(fieldType));

                    arr.add(tmpstr);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get_search by where method in " + TABLENAME + " throw an exception ");
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }


        public ArrayList<String> getSearch(String where, boolean evenStopped) {
            ArrayList<String> arr = new ArrayList<String>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0) {
                    whereCol = where;
                    if (!evenStopped) {
                        whereCol += " and ";
                        whereCol += KEY_STOPPED + "<>2";
                    }
                } else {
                    if (!evenStopped)
                        whereCol = KEY_STOPPED + "<>2";
                }

                String tmpstr;
                Cursor c = shamelDB.query(TABLENAME, new String[]{KEY_ID, KEY_NAME, KEY_FACTORYNO}, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                    Stock obj = new Stock();

                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));
                    obj.factoryNo = c.getString(c.getColumnIndex(KEY_FACTORYNO));

                    tmpstr = obj.id + System.getProperty("line.separator");
                    tmpstr += obj.name;
                    if (obj.factoryNo != null)
                        tmpstr += System.getProperty("line.separator") + obj.factoryNo;
                    arr.add(tmpstr);

//					arr.add(obj.name);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get_search by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }


        public boolean checkExisting(int _id) {
            try {
                String[] Columns = {KEY_ID};
                Cursor c = shamelDB.query(TABLENAME, Columns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public int checkExisting(String where) {
            int _id = -1;
            try {

                String selectQuery = "SELECT "+" LTRIM(RTRIM("+KEY_ID+")) as "+ KEY_ID  + "  from " + TABLENAME + " where  "+where;
                shamelDB = dbHelper.getReadableDatabase();
                Cursor c = shamelDB.rawQuery(selectQuery, null);

                //String[] Columns = {KEY_ID};
                //Cursor c = shamelDB.query(TABLENAME, Columns, where, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    _id = c.getInt(c.getColumnIndex(KEY_ID));
                }
                c.close();
                return _id;
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return -1;
            }
        }

        public void deleteTemp(int _id) {
            try {
                // delete from the table
                shamelDB.execSQL(" delete from "+TABLENAME);


            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);

            }
        }

    }

    // ///////////////////////////////Store/////////////////////
    public class Store {
        public int id;
        public String name;

        public Store() {
            id = -1;
            name = "";
        }
    }

    public class StoreEntity {
        // Users Table Columns names
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";

        String[] AllColumns = {KEY_ID, KEY_NAME};

        // used to make the copy paste more easy
        private final String TABLENAME = TABLE_STORE;

        public void createTable(SQLiteDatabase db) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                Util.logException(c, e);
            }
        }

        // the default object
        // you can use this object to minimize the code
        public Store obj;

        public StoreEntity() {
            obj = new Store();
        }

        public boolean insert(Store _obj) {
            try {
                // insert in the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_ID, _obj.id);
                cv.put(KEY_NAME, _obj.name);
                long rowID = shamelDB.insert(TABLENAME, null, cv);
                if (rowID == -1) {
                    Util.logInformation(c, "insert into " + TABLENAME + " return -1 key=" + _obj.id);
                    return false;
                }
                return true;

            } catch (Exception e) {
                Util.logInformation(c, "insert method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean update(Store _obj) {
            try {
                // update the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_NAME, _obj.name);
                int recordCount = shamelDB.update(TABLENAME, cv, KEY_ID + "=" + _obj.id, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "update more than one record from " + TABLENAME + " key=" + _obj.id);
                    return true;
                } else {
                    Util.logInformation(c, "update from " + TABLENAME + " has no row to update" + " key=" + _obj.id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "update method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean delete(int _id) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, KEY_ID + "=" + _id, null);

                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "delete more than one row from " + TABLENAME + " key=" + _id);
                    return true;
                } else {
                    Util.logInformation(c, "delete from " + TABLENAME + " has no row to delete " + " key=" + _id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean get(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public List<Store> get(String where) {
            List<Store> arr = new ArrayList<Store>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                Cursor c = shamelDB.query(TABLENAME, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    Store obj = new Store();
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));

                    arr.add(obj);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }

        public boolean checkExisting(int _id) {
            try {
                String[] Columns = {KEY_ID};
                Cursor c = shamelDB.query(TABLENAME, Columns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public int checkExisting(String where) {
            int _id = -1;
            try {
                String[] Columns = {KEY_ID};
                Cursor c = shamelDB.query(TABLENAME, Columns, where, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    _id = c.getInt(c.getColumnIndex(KEY_ID));
                }
                c.close();
                return _id;
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return -1;
            }
        }
        public void deleteTemp(int _id) {
            try {
                // delete from the table
                shamelDB.execSQL(" delete from "+TABLENAME);


            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);

            }
        }
    }

//////////////////////////////////Logging/////////////////////////////////
    public class Logging {
        public int id, userID;
        long date, time;
        String desc, processName, processType;

        public Logging() {
            id = userID = -1;
            desc = processName = processType = "";
        }
    }

    public class LoggingEntity {
        // Users Table Columns names
        public static final String KEY_ID = "id";
        public static final String KEY_USER_ID = "user_id";
        public static final String KEY_DATE = "date";
        public static final String KEY_TIME = "time";
        public static final String KEY_DESC = "desc";
        public static final String KEY_PROCESS_NAME = "processName";
        public static final String KEY_PROCESS_TYPE = "processType";

        String[] AllColumns = {KEY_ID, KEY_USER_ID, KEY_DATE, KEY_TIME, KEY_DESC, KEY_PROCESS_NAME, KEY_PROCESS_TYPE};

        // used to make the copy paste more easy
        private final String TABLENAME = TABLE_LOGGING;

        public void createTable(SQLiteDatabase db) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        KEY_USER_ID + " INTEGER," + KEY_DATE + " INTEGER," + KEY_TIME + " INTEGER," + KEY_DESC + " TEXT," +
                        KEY_PROCESS_NAME + " TEXT," + KEY_PROCESS_TYPE + " TEXT" + ")";
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                Util.logException(c, e);
            }
        }

        // the default object
        // you can use this object to minimize the code
        public Logging obj;

        public LoggingEntity() {
            obj = new Logging();
        }


        public boolean insert(Logging _obj) {
            try {
                // insert in the table
                ContentValues cv = new ContentValues();
                // cv.put(KEY_ID, _obj.id);
                cv.put(KEY_USER_ID, _obj.userID);
                cv.put(KEY_DATE, _obj.date);
                cv.put(KEY_TIME, _obj.time);
                cv.put(KEY_DESC, _obj.desc);
                cv.put(KEY_PROCESS_NAME, _obj.processName);
                cv.put(KEY_PROCESS_TYPE, _obj.processType);
                long rowID = shamelDB.insert(TABLE_LOGGING, null, cv);
                if (rowID == -1) {
                    Util.logInformation(c, "insert into " + TABLE_LOGGING + " return -1 key=" + _obj.id);
                    return false;
                }
                return true;

            } catch (Exception e) {
                Util.logInformation(c, "insert method in " + TABLE_LOGGING + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }


        public boolean get(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.date = c.getLong(c.getColumnIndex(KEY_DATE));
                    obj.time = c.getLong(c.getColumnIndex(KEY_TIME));
                    obj.desc = c.getString(c.getColumnIndex(KEY_DESC));
                    obj.processName = c.getString(c.getColumnIndex(KEY_PROCESS_NAME));
                    obj.processType = c.getString(c.getColumnIndex(KEY_PROCESS_TYPE));
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public List<Logging> getLogs(String where) {
            List<Logging> arr = new ArrayList<Logging>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                Cursor c = shamelDB.query(TABLE_LOGGING, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    Logging obj = new Logging();
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.userID = c.getInt(c.getColumnIndex(KEY_USER_ID));
                    obj.date = c.getLong(c.getColumnIndex(KEY_DATE));
                    obj.time = c.getLong(c.getColumnIndex(KEY_TIME));
                    obj.desc = c.getString(c.getColumnIndex(KEY_DESC));
                    obj.processName = c.getString(c.getColumnIndex(KEY_PROCESS_NAME));
                    obj.processType = c.getString(c.getColumnIndex(KEY_PROCESS_TYPE));

                    arr.add(obj);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLE_LOGGING + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }

    }
///////////////////////////////Undefined Stocktaking Transactions

    public class UndefinedStockTakingTransactions {
        public int id, stockTatkingID, sessionID, unitID,stockTakingDate;
        public double quantity;
        public String itemBarcode, unitName ,undefinedBarcodeName;

        public UndefinedStockTakingTransactions() {
            id = stockTatkingID = sessionID = unitID = -1;
            quantity=0.0;
            itemBarcode= unitName =undefinedBarcodeName ="";
            stockTakingDate = Util.ZeroDate;
        }
    }

    public class UndefinedStockTakingTransactionsEntity {
        // Users Table Columns names

        public static final String KEY_UNDEFINED_ID_TRANSACTIONS = "undefined_id_transactions";
        public static final String KEY_UNDEFINED_QUANTITY_TRANSACTIONS = "undefined_quantity_transactions";
        public static final String KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS = "undefined_item_barcode_transactions";
        public static final String KEY_UNDEFINED_STOCK_TAKING_ID_TRANSACTIONS = "undefined_stock_taking_id_transactions";
        public static final String KEY_UNDEFINED_SESSION_ID_TRANSACTIONS = "undefined_session_id_transactions";
        public static final String KEY_UNDEFINED_STOCKTAKING_DATE = "undefined_stock_taking_date_transactions";
        public static final String KEY_UNDEFINED_BARCODE_NAME = "undefined_barcode_name";



        String[] AllColumns = {KEY_UNDEFINED_ID_TRANSACTIONS, KEY_UNDEFINED_QUANTITY_TRANSACTIONS, KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS, KEY_UNDEFINED_STOCK_TAKING_ID_TRANSACTIONS,
                KEY_UNDEFINED_SESSION_ID_TRANSACTIONS,KEY_UNDEFINED_STOCKTAKING_DATE,KEY_UNDEFINED_BARCODE_NAME};

        // used to make the copy paste more easy
        private final String TABLENAME = TABLE_UNDEFINED_STOCK_TAKING_TRANSACTIONS;

        public void createTable(SQLiteDatabase db) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "(" + KEY_UNDEFINED_ID_TRANSACTIONS + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_UNDEFINED_QUANTITY_TRANSACTIONS + " DOUBLE ,"
                        + KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS + " TEXT , " + KEY_UNDEFINED_STOCK_TAKING_ID_TRANSACTIONS + " INTEGER , " + KEY_UNDEFINED_SESSION_ID_TRANSACTIONS + " INTEGER , "
                        +KEY_UNDEFINED_STOCKTAKING_DATE + " INTEGER , "+KEY_UNDEFINED_BARCODE_NAME + " TEXT "  + ")";
                db.execSQL(CREATE_TABLE);
              //  Toast.makeText(context, "Created Undefined StockTaking Transactions", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Util.logException(c, e);
            }
        }

        // the default object
        // you can use this object to minimize the code
        public UndefinedStockTakingTransactions obj;

        public UndefinedStockTakingTransactionsEntity() {
            obj = new UndefinedStockTakingTransactions();
        }

        public boolean insert(UndefinedStockTakingTransactions _obj) {
            try {
                // insert in the table


                ContentValues cv = new ContentValues();

                cv.put(KEY_UNDEFINED_QUANTITY_TRANSACTIONS, _obj.quantity);
                cv.put(KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS, _obj.itemBarcode);
                cv.put(KEY_UNDEFINED_STOCK_TAKING_ID_TRANSACTIONS, _obj.stockTatkingID);
                cv.put(KEY_UNDEFINED_SESSION_ID_TRANSACTIONS, _obj.sessionID);
                cv.put(KEY_UNDEFINED_STOCKTAKING_DATE, _obj.stockTakingDate);
                cv.put(KEY_UNDEFINED_BARCODE_NAME, _obj.undefinedBarcodeName);

                long rowID = shamelDB.insert(TABLENAME, null, cv);
                if (rowID == -1) {
                    Util.logInformation(c, "insert into " + TABLENAME + " return -1 key=" + _obj.id);
                    return false;
                }
                return true;

            } catch (Exception e) {
                Util.logInformation(c, "insert method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean update(double quantityToUpdate ,String where) {
            try {
                // update the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_UNDEFINED_QUANTITY_TRANSACTIONS, quantityToUpdate);



                int recordCount = shamelDB.update(TABLENAME, cv, where, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "update more than one record from " + TABLENAME + " key="  );
                    return true;
                } else {
                    Util.logInformation(c, "update from " + TABLENAME + " has no row to update" + " key=" );
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "update method in " + TABLENAME + " throw an exception " + " key=" );
                Util.logException(c, e);
                return false;
            }
        }

        public boolean delete(int _id) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, KEY_UNDEFINED_ID_TRANSACTIONS + "=" + _id, null);

                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "delete more than one row from " + TABLENAME + " key=" + _id);
                    return true;
                } else {
                    Util.logInformation(c, "delete from " + TABLENAME + " has no row to delete " + " key=" + _id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean get(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_UNDEFINED_ID_TRANSACTIONS + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_UNDEFINED_ID_TRANSACTIONS));

                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }


        public List<UndefinedStockTakingTransactions> get(String where) {
            List<UndefinedStockTakingTransactions> arr = new ArrayList<UndefinedStockTakingTransactions>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                Cursor c = shamelDB.query(TABLENAME, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    UndefinedStockTakingTransactions obj = new UndefinedStockTakingTransactions();
                    // get id
                    obj.itemBarcode = c.getString(c.getColumnIndex(KEY_UNDEFINED_ITEM_BARCODE_TRANSACTIONS));
                    obj.quantity = c.getDouble(c.getColumnIndex(KEY_UNDEFINED_QUANTITY_TRANSACTIONS));
                    obj.stockTatkingID = c.getInt(c.getColumnIndex(KEY_UNDEFINED_STOCK_TAKING_ID_TRANSACTIONS));
                    obj.sessionID = c.getInt(c.getColumnIndex(KEY_UNDEFINED_SESSION_ID_TRANSACTIONS));
                    obj.stockTakingDate = c.getInt(c.getColumnIndex(KEY_UNDEFINED_STOCKTAKING_DATE));
                    obj.undefinedBarcodeName = c.getString(c.getColumnIndex(KEY_UNDEFINED_BARCODE_NAME));


                    arr.add(obj);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }


        public boolean checkExisting(int _id) {
            try {
                String[] Columns = {KEY_UNDEFINED_ID_TRANSACTIONS};
                Cursor c = shamelDB.query(TABLENAME, Columns, KEY_UNDEFINED_ID_TRANSACTIONS + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public int checkExisting(String where) {
            int _id = -1;
            try {
                String[] Columns = {KEY_UNDEFINED_ID_TRANSACTIONS};
                Cursor c = shamelDB.query(TABLENAME, Columns, where, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    _id = c.getInt(c.getColumnIndex(KEY_UNDEFINED_ID_TRANSACTIONS));
                }
                c.close();
                return _id;
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return -1;
            }
        }

        public boolean deleteUndefinedStockTakingTransaction(String where) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, where , null);

                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "delete more than one row from " + TABLENAME + " key=" );
                    return true;
                } else {
                    Util.logInformation(c, "delete from " + TABLENAME + " has no row to delete " + " key=" );
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" );
                Util.logException(c, e);
                return false;
            }
        }

    }


    // ///////////////////////////////Units////////////////////
    public class Units {
        public int id,numUnits , shamelUnitID;
        public String itemCodeUnits,itemBarcodeUnits;

        public Units() {
            id = numUnits = shamelUnitID = -1;
            itemCodeUnits = itemBarcodeUnits = "";
        }
    }

    public class UnitsEntity {
        // Users Table Columns names

        public static final String KEY_ID_UNITS = "id_units";
        public static final String KEY_ITEM_CODE_UNITS = "item_code_units";
        public static final String KEY_ITEM_BARCODE_UNITS = "item_barcode_units";
        public static final String KEY_NUM_UNITS = "num_units";
        public static final String KEY_SHAMEL_UNIT_ID_UNITS = "shamel_unit_id_units";




        String[] AllColumns = {KEY_ID_UNITS, KEY_ITEM_CODE_UNITS, KEY_ITEM_BARCODE_UNITS, KEY_NUM_UNITS,
                KEY_SHAMEL_UNIT_ID_UNITS};

        // used to make the copy paste more easy
        private final String TABLENAME = TABLE_UNITS;

        public void createTable(SQLiteDatabase db) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "(" + KEY_ID_UNITS + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_ITEM_CODE_UNITS + " TEXT ,"
                        + KEY_ITEM_BARCODE_UNITS + " TEXT , " + KEY_NUM_UNITS + " INTEGER , " + KEY_SHAMEL_UNIT_ID_UNITS + " INTEGER " + ")";
                db.execSQL(CREATE_TABLE);
                //Toast.makeText(context, "Created Table Units", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Util.logException(c, e);
            }
        }

        // the default object
        // you can use this object to minimize the code
        public Units obj;

        public UnitsEntity() {
            obj = new Units();
        }

        public boolean insert(Units _obj) {
            try {
                // insert in the table


                ContentValues cv = new ContentValues();
                cv.put(KEY_ITEM_CODE_UNITS, _obj.itemCodeUnits);
                cv.put(KEY_ITEM_BARCODE_UNITS, _obj.itemBarcodeUnits);
                cv.put(KEY_NUM_UNITS, _obj.numUnits);
                cv.put(KEY_SHAMEL_UNIT_ID_UNITS, _obj.shamelUnitID);


                long rowID = shamelDB.insert(TABLENAME, null, cv);
                if (rowID == -1) {
                    Util.logInformation(c, "insert into " + TABLENAME + " return -1 key=" + _obj.id);
                    return false;
                }
                return true;

            } catch (Exception e) {
                Util.logInformation(c, "insert method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean update(Units _obj) {
            try {
                // update the table
                ContentValues cv = new ContentValues();
                // cv.put( KEY_ID ,_obj.id);
                cv.put(KEY_ITEM_CODE_UNITS, _obj.itemCodeUnits);
                cv.put(KEY_ITEM_BARCODE_UNITS, _obj.itemBarcodeUnits);
                cv.put(KEY_NUM_UNITS, _obj.numUnits);
                cv.put(KEY_SHAMEL_UNIT_ID_UNITS, _obj.shamelUnitID);

                int recordCount = shamelDB.update(TABLENAME, cv, KEY_ID_UNITS + "=" + _obj.id, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "update more than one record from " + TABLENAME + " key=" + _obj.id);
                    return true;
                } else {
                    Util.logInformation(c, "update from " + TABLENAME + " has no row to update" + " key=" + _obj.id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "update method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean delete(int _id) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, KEY_ID_UNITS + "=" + _id, null);

                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "delete more than one row from " + TABLENAME + " key=" + _id);
                    return true;
                } else {
                    Util.logInformation(c, "delete from " + TABLENAME + " has no row to delete " + " key=" + _id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean get(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID_UNITS + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID_UNITS));

                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public List<Units> get(String where) {
            List<Units> arr = new ArrayList<Units>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;





                Cursor c = shamelDB.query(TABLENAME, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    Units obj = new Units();
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID_UNITS));
                    obj.itemCodeUnits = c.getString(c.getColumnIndex(KEY_ITEM_CODE_UNITS));
                    obj.itemBarcodeUnits = c.getString(c.getColumnIndex(KEY_ITEM_BARCODE_UNITS));
                    obj.numUnits = c.getInt(c.getColumnIndex(KEY_NUM_UNITS));
                    obj.shamelUnitID = c.getInt(c.getColumnIndex(KEY_SHAMEL_UNIT_ID_UNITS));
                    arr.add(obj);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }

        public boolean checkExisting(int _id) {
            try {
                String[] Columns = {KEY_ID_UNITS};
                Cursor c = shamelDB.query(TABLENAME, Columns, KEY_ID_UNITS + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public String checkExisting(String where) {
            String _code = "-1";
            try {



                  //String selectQuery = "SELECT "+" LTRIM(RTRIM("+KEY_ITEM_CODE_UNITS+")) as "+KEY_ITEM_CODE_UNITS+"  from " + TABLENAME + " where "+where;
                //String selectQuery = "SELECT "+" LTRIM(RTRIM("+KEY_ITEM_CODE_UNITS+")) as "+ KEY_ITEM_CODE_UNITS  + "  from " + TABLENAME + " where LTRIM(RTRIM(item_barcode_units)) = '3333' ";
                String selectQuery = "SELECT "+" LTRIM(RTRIM("+KEY_ITEM_CODE_UNITS+")) as "+ KEY_ITEM_CODE_UNITS  + "  from " + TABLENAME + " where  "+where;

                shamelDB = dbHelper.getReadableDatabase();
                Cursor c = shamelDB.rawQuery(selectQuery, null);


                /*
                 String columnName = " RTRIM("+KEY_ITEM_CODE_UNITS+") ";
                String[] Columns = {columnName};
                Cursor c = shamelDB.query(TABLENAME, Columns, where, null, null, null, null);*/
                //for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                if (c.moveToFirst() && !c.isAfterLast()) {
                    _code = c.getString(c.getColumnIndex(KEY_ITEM_CODE_UNITS));
                }
                c.close();
                return _code;
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return "-1";
            }
        }

        public int checkExistingByID(String where) {
            int _id = -1;
            try {


                String selectQuery = "SELECT "+" LTRIM(RTRIM("+KEY_ID_UNITS+")) as "+ KEY_ID_UNITS  + "  from " + TABLENAME + " where  "+where;
                shamelDB = dbHelper.getReadableDatabase();
                Cursor c = shamelDB.rawQuery(selectQuery, null);
                /*
                String[] Columns = {KEY_ID_UNITS};
                Cursor c = shamelDB.query(TABLENAME, Columns, where, null, null, null, null);*/

                if (c.moveToFirst() && !c.isAfterLast()) {
                    _id = c.getInt(c.getColumnIndex(KEY_ID_UNITS));
                }
                c.close();
                return _id;
            } catch (Exception e) {
                Util.logInformation(c, "checkExistingByID by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return _id;
            }
        }

        public boolean deleteStockTakingTransaction(String where) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, where , null);

                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "delete more than one row from " + TABLENAME + " key=" );
                    return true;
                } else {
                    Util.logInformation(c, "delete from " + TABLENAME + " has no row to delete " + " key=" );
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" );
                Util.logException(c, e);
                return false;
            }
        }


        public boolean delete(String where) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, where, null);
                return true;
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return false;
            }
        }

        public void deleteTemp(int _id) {
            try {
                // delete from the table
                shamelDB.execSQL(" delete from "+TABLENAME);


            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);

            }
        }
    }
//////////////////////// StockTaking////////////////////////////////////
        public class StockTaking {
            public int id ,date,status;
            String stockTakingCode , name ;

            public StockTaking() {
                id = -1;
                status = 0;
                stockTakingCode = "";
                date=Util.ZeroDate;
            }
        }

    public class StockTakingEntity {
        // Users Table Columns names
        public static final String KEY_ID = "id";
        public static final String KEY_STOCK_TAKING_CODE = "stock_taking_code";
        public static final String KEY_STOCK_TAKING_DATE = "stock_taking_date";
        public static final String KEY_STOCK_TAKING_STATUS = "stock_taking_status";
        public static final String KEY_STOCK_TAKING_NAME = "stock_taking_name";


        String[] AllColumns = {KEY_ID, KEY_STOCK_TAKING_CODE, KEY_STOCK_TAKING_DATE, KEY_STOCK_TAKING_STATUS, KEY_STOCK_TAKING_NAME};

        private final String TABLENAME = TABLE_STOCK_TAKING;

        public void createTable(SQLiteDatabase db) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        KEY_STOCK_TAKING_CODE + " TEXT , " + KEY_STOCK_TAKING_DATE + " INTEGER , " + KEY_STOCK_TAKING_STATUS + " INTEGER , " + KEY_STOCK_TAKING_NAME + " TEXT " + ")";
                db.execSQL(CREATE_TABLE);
               // Toast.makeText(context, "Created StockTaking ", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Util.logException(c, e);
            }
        }

        public StockTaking obj;

        public StockTakingEntity() {
            obj = new StockTaking();
        }


        public boolean insert(StockTaking _obj) {
            try {
                // insert in the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_STOCK_TAKING_CODE, _obj.stockTakingCode);
                cv.put(KEY_STOCK_TAKING_DATE, _obj.date);
                cv.put(KEY_STOCK_TAKING_STATUS, _obj.status);
                cv.put(KEY_STOCK_TAKING_NAME, _obj.name);
                long rowID = shamelDB.insert(TABLENAME, null, cv);
                if (rowID == -1) {
                    Util.logInformation(c, "insert into " + TABLE_LOGGING + " return -1 key=" + _obj.id);
                    return false;
                }
                return true;

            } catch (Exception e) {
                Util.logInformation(c, "insert method in " + TABLE_LOGGING + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean update(StockTaking _obj) {
            try {
                // update the table
                ContentValues cv = new ContentValues();
                // cv.put( KEY_ID ,_obj.id);
                cv.put(KEY_STOCK_TAKING_CODE, _obj.stockTakingCode);
                cv.put(KEY_STOCK_TAKING_DATE, _obj.date);
                cv.put(KEY_STOCK_TAKING_STATUS, _obj.status);
                cv.put(KEY_STOCK_TAKING_NAME, _obj.name);

                int recordCount = shamelDB.update(TABLENAME, cv, KEY_ID + "=" + _obj.id, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "update more than one record from " + TABLENAME + " key=" + _obj.id);
                    return true;
                } else {
                    Util.logInformation(c, "update from " + TABLENAME + " has no row to update" + " key=" + _obj.id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "update method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean get(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.stockTakingCode = c.getString(c.getColumnIndex(KEY_STOCK_TAKING_CODE));
                    obj.date = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_DATE));
                    obj.status = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_STATUS));
                    obj.name = c.getString(c.getColumnIndex(KEY_STOCK_TAKING_NAME));
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public int checkExistingByID(String where) {
            int _id = -1;
            try {
                String[] Columns = {KEY_ID};
                Cursor c = shamelDB.query(TABLENAME, Columns, where, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    _id = c.getInt(c.getColumnIndex(KEY_ID));
                }
                c.close();
                return _id;
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return _id;
            }
        }

        public List<StockTaking> getStockTakingRecords(String where) {
            List<StockTaking> arr = new ArrayList<StockTaking>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                Cursor c = shamelDB.query(TABLENAME, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    StockTaking obj = new StockTaking();
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.stockTakingCode = c.getString(c.getColumnIndex(KEY_STOCK_TAKING_CODE));
                    obj.date = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_DATE));
                    obj.status = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_STATUS));
                    obj.name = c.getString(c.getColumnIndex(KEY_STOCK_TAKING_NAME));


                    arr.add(obj);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLE_LOGGING + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }
        public int getNumberOfAvailableStockTakingRecords(String where) {
          int count =0;
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                String[]ids={KEY_ID};
                Cursor c = shamelDB.query(TABLENAME, ids, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                   count++;

                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLE_LOGGING + " throw an exception " + " where=" + where);
                Util.logException(c, e);
               count=-1;
            }
            return count;
        }
        public int getStockTakingStatus(String where) {
            int count =0;
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                String[]status={KEY_STOCK_TAKING_STATUS};
                Cursor c = shamelDB.query(TABLENAME, status, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                    count++;

                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLE_LOGGING + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                count=-1;
            }
            return count;
        }
        public void deleteTemp(int _id) {
            try {
                // delete from the table
                shamelDB.execSQL(" delete from "+TABLENAME);


            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);

            }
        }
    }

    // ///////////////////////////////Stocktaking Sessions////////////////////
    public class StockTakingSessions {
        public int id ,date,lastInvoiceDate,storeID , session , status;
        String stockTakingSessionsCode , adminName ;

        public StockTakingSessions() {
            id =storeID=session=status=-1;
            stockTakingSessionsCode =adminName = "";
            date=lastInvoiceDate =Util.ZeroDate;
        }
    }

    public class StockTakingSessionsEntity {
        // Users Table Columns names
        public static final String KEY_ID = "id";
        public static final String KEY_STOCK_TAKING_SESSIONS_CODE = "stock_taking_sessions_code";
        public static final String  KEY_STOCK_TAKING_SESSIONS_DATE = "stock_taking_sessions_date";
        public static final String  KEY_STOCK_TAKING_SESSIONS_LAST_INVOICE_DATE = "stock_taking_sessions_last_invoice_date";
        public static final String  KEY_STOCK_TAKING_SESSIONS_STORE_ID = "stock_taking_sessions_store_id";
        public static final String  KEY_STOCK_TAKING_SESSIONS_ADMIN_NAME = "stock_taking_sessions_admin_name";
        public static final String KEY_STOCK_TAKING_SESSIONS_SESSION = "stock_taking_sessions_session";
        public static final String KEY_STOCK_TAKING_SESSIONS_STATUS = "stock_taking_sessions_status";


        String[] AllColumns = {KEY_ID, KEY_STOCK_TAKING_SESSIONS_CODE ,KEY_STOCK_TAKING_SESSIONS_DATE ,KEY_STOCK_TAKING_SESSIONS_LAST_INVOICE_DATE,KEY_STOCK_TAKING_SESSIONS_STORE_ID
                ,KEY_STOCK_TAKING_SESSIONS_ADMIN_NAME,KEY_STOCK_TAKING_SESSIONS_SESSION,KEY_STOCK_TAKING_SESSIONS_STATUS};

        // used to make the copy paste more easy
        private final String TABLENAME = TABLE_STOCK_TAKING_SESSIONS;

        public void createTable(SQLiteDatabase db) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        KEY_STOCK_TAKING_SESSIONS_CODE + " TEXT , "+ KEY_STOCK_TAKING_SESSIONS_DATE + " INTEGER , "+ KEY_STOCK_TAKING_SESSIONS_LAST_INVOICE_DATE + " INTEGER , "+KEY_STOCK_TAKING_SESSIONS_STORE_ID + " INTEGER , "
                        + KEY_STOCK_TAKING_SESSIONS_ADMIN_NAME + " TEXT , "+KEY_STOCK_TAKING_SESSIONS_SESSION+" INTEGER , "+KEY_STOCK_TAKING_SESSIONS_STATUS+" INTEGER " + ")";
                db.execSQL(CREATE_TABLE);
               // Toast.makeText(context, "Created StockTaking Sessions  ", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Util.logException(c, e);
            }
        }

        // the default object
        // you can use this object to minimize the code
        public StockTakingSessions obj;

        public StockTakingSessionsEntity() {
            obj = new StockTakingSessions();
        }


        public boolean insert(StockTakingSessions _obj) {
            try {
                // insert in the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_STOCK_TAKING_SESSIONS_CODE, _obj.stockTakingSessionsCode);
                cv.put(KEY_STOCK_TAKING_SESSIONS_DATE, _obj.date);
                cv.put(KEY_STOCK_TAKING_SESSIONS_LAST_INVOICE_DATE, _obj.lastInvoiceDate);
                cv.put(KEY_STOCK_TAKING_SESSIONS_STORE_ID, _obj.storeID);
                cv.put(KEY_STOCK_TAKING_SESSIONS_ADMIN_NAME, _obj.adminName);
                cv.put(KEY_STOCK_TAKING_SESSIONS_SESSION, _obj.session);
                cv.put(KEY_STOCK_TAKING_SESSIONS_STATUS, _obj.status);
                long rowID = shamelDB.insert(TABLENAME, null, cv);
                if (rowID == -1) {
                    Util.logInformation(c, "insert into " + TABLE_LOGGING + " return -1 key=" + _obj.id);
                    return false;
                }
                return true;

            } catch (Exception e) {
                Util.logInformation(c, "insert method in " + TABLE_LOGGING + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean update(StockTakingSessions _obj) {
            try {
                // update the table
                ContentValues cv = new ContentValues();
                // cv.put( KEY_ID ,_obj.id);
                cv.put(KEY_STOCK_TAKING_SESSIONS_CODE, _obj.stockTakingSessionsCode);
                cv.put(KEY_STOCK_TAKING_SESSIONS_DATE, _obj.date);
                cv.put(KEY_STOCK_TAKING_SESSIONS_LAST_INVOICE_DATE, _obj.lastInvoiceDate);
                cv.put(KEY_STOCK_TAKING_SESSIONS_STORE_ID, _obj.storeID);
                cv.put(KEY_STOCK_TAKING_SESSIONS_ADMIN_NAME, _obj.adminName);
                cv.put(KEY_STOCK_TAKING_SESSIONS_SESSION, _obj.session);
                cv.put(KEY_STOCK_TAKING_SESSIONS_STATUS, _obj.status);

                int recordCount = shamelDB.update(TABLENAME, cv, KEY_ID + "=" + _obj.id, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "update more than one record from " + TABLENAME + " key=" + _obj.id);
                    return true;
                } else {
                    Util.logInformation(c, "update from " + TABLENAME + " has no row to update" + " key=" + _obj.id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "update method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }
        public boolean updateSessionStatus(int status,String where) {
            try {
                // update the table
                ContentValues cv = new ContentValues();
                // cv.put( KEY_ID ,_obj.id);
                cv.put(KEY_STOCK_TAKING_SESSIONS_STATUS, status);

                int recordCount = shamelDB.update(TABLENAME, cv, where, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "update more than one record from " + TABLENAME + " where=" + where);
                    return true;
                } else {
                    Util.logInformation(c, "update from " + TABLENAME + " has no row to update" + " where=" + where);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "update method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return false;
            }
        }

        public int checkExistingByID(String where) {
            int _id = -1;
            try {
                String[] Columns = {KEY_ID};
                Cursor c = shamelDB.query(TABLENAME, Columns, where, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    _id = c.getInt(c.getColumnIndex(KEY_ID));
                }
                c.close();
                return _id;
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return _id;
            }
        }


        public boolean get(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.stockTakingSessionsCode = c.getString(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_CODE));
                    obj.date = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_DATE));
                    obj.lastInvoiceDate = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_LAST_INVOICE_DATE));
                    obj.storeID = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_STORE_ID));
                    obj.adminName = c.getString(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_ADMIN_NAME));
                    obj.session = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_SESSION));
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public List<StockTakingSessions> getStockTakingSessions(String where) {
            List<StockTakingSessions> arr = new ArrayList<StockTakingSessions>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                Cursor c = shamelDB.query(TABLENAME, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    StockTakingSessions obj = new StockTakingSessions();
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.stockTakingSessionsCode = c.getString(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_CODE));
                    obj.date = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_DATE));
                    obj.lastInvoiceDate = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_LAST_INVOICE_DATE));
                    obj.storeID = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_STORE_ID));
                    obj.adminName = c.getString(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_ADMIN_NAME));
                    obj.session = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_SESSION));
                    obj.status = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_STATUS));


                    arr.add(obj);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLE_LOGGING + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }

        public int getSessionStatus(String where) {
          int status = -1 ;
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                Cursor c = shamelDB.query(TABLENAME, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                   status= obj.status = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_SESSIONS_STATUS));



                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLE_LOGGING + " throw an exception " + " where=" + where);
                Util.logException(c, e);
               status =-1;
            }
            return status;
        }
        public void deleteTemp(int _id) {
            try {
                // delete from the table
                shamelDB.execSQL(" delete from "+TABLENAME);


            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);

            }
        }

    }


    // ///////////////////////////////Stocktaking Transactions////////////////////
    public class StockTakingTransactions {
        public int id, itemID, stockTatkingID, sessionID, unitID , stockTakingDate , isPosted;
        public double quantity;
        public String itemName, unitName;

        public StockTakingTransactions() {
            id = itemID = stockTatkingID = sessionID = unitID = isPosted=-1;
            quantity=0.0;
            itemName = unitName = "";
            stockTakingDate = Util.ZeroDate;
        }
    }

    public class StockTakingTransactionsEntity {
        // Users Table Columns names

        public static final String KEY_ID_TRANSACTIONS = "id_transactions";
        public static final String KEY_QUANTITY_TRANSACTIONS = "quantity_transactions";
        public static final String KEY_ITEM_ID_TRANSACTIONS = "item_id_transactions";
        public static final String KEY_STOCK_TAKING_ID_TRANSACTIONS = "stock_taking_id_transactions";
        public static final String KEY_SESSION_ID_TRANSACTIONS = "session_id_transactions";
        public static final String KEY_UNIT_ID_TRANSACTIONS = "unit_id_transactions";
        public static final String KEY_ITEM_NAME_TRANSACTIONS = "item_name_transactions";
        public static final String KEY_UNIT_NAME_TRANSACTIONS = "unit_name_transactions";
        public static final String KEY_STOCKTAKING_DATE = "stock_taking_date";
        public static final String KEY_IS_POSTED = "stock_taking_transactions_is_posted";



        String[] AllColumns = {KEY_ID_TRANSACTIONS, KEY_QUANTITY_TRANSACTIONS, KEY_ITEM_ID_TRANSACTIONS, KEY_STOCK_TAKING_ID_TRANSACTIONS,
                KEY_SESSION_ID_TRANSACTIONS, KEY_UNIT_ID_TRANSACTIONS, KEY_ITEM_NAME_TRANSACTIONS, KEY_UNIT_NAME_TRANSACTIONS,KEY_STOCKTAKING_DATE,KEY_IS_POSTED};

        // used to make the copy paste more easy
        private final String TABLENAME = TABLE_STOCK_TAKING_TRANSACTIONS;

        public void createTable(SQLiteDatabase db) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "(" + KEY_ID_TRANSACTIONS + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_QUANTITY_TRANSACTIONS + " INTEGER ,"
                        + KEY_ITEM_ID_TRANSACTIONS + " INTEGER , " + KEY_STOCK_TAKING_ID_TRANSACTIONS + " INTEGER , " + KEY_SESSION_ID_TRANSACTIONS + " INTEGER ," +
                        KEY_UNIT_ID_TRANSACTIONS + " INTEGER , " + KEY_ITEM_NAME_TRANSACTIONS + " TEXT , " + KEY_UNIT_NAME_TRANSACTIONS + " TEXT , "+ KEY_STOCKTAKING_DATE +" INTEGER , "+KEY_IS_POSTED +" INTEGER " + ")";
                db.execSQL(CREATE_TABLE);
               // Toast.makeText(context, "Created StockTakingTransactions", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Util.logException(c, e);
            }
        }

        // the default object
        // you can use this object to minimize the code
        public StockTakingTransactions obj;

        public StockTakingTransactionsEntity() {
            obj = new StockTakingTransactions();
        }

        public boolean insert(StockTakingTransactions _obj) {
            try {
                // insert in the table


                ContentValues cv = new ContentValues();

                cv.put(KEY_QUANTITY_TRANSACTIONS, _obj.quantity);
                cv.put(KEY_ITEM_ID_TRANSACTIONS, _obj.itemID);
                cv.put(KEY_STOCK_TAKING_ID_TRANSACTIONS, _obj.stockTatkingID);
                cv.put(KEY_SESSION_ID_TRANSACTIONS, _obj.sessionID);
                cv.put(KEY_UNIT_ID_TRANSACTIONS, _obj.unitID);
                cv.put(KEY_ITEM_NAME_TRANSACTIONS, _obj.itemName);
                cv.put(KEY_UNIT_NAME_TRANSACTIONS, _obj.unitName);
                cv.put(KEY_STOCKTAKING_DATE, _obj.stockTakingDate);
                cv.put(KEY_IS_POSTED, _obj.isPosted);

                long rowID = shamelDB.insert(TABLENAME, null, cv);
                if (rowID == -1) {
                    Util.logInformation(c, "insert into " + TABLENAME + " return -1 key=" + _obj.id);
                    return false;
                }
                return true;

            } catch (Exception e) {
                Util.logInformation(c, "insert method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean update(double quantityToUpdate ,String where) {
            try {
                // update the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_QUANTITY_TRANSACTIONS, quantityToUpdate);

                int recordCount = shamelDB.update(TABLENAME, cv, where, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "update more than one record from " + TABLENAME + " key="  );
                    return true;
                } else {
                    Util.logInformation(c, "update from " + TABLENAME + " has no row to update" + " key=" );
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "update method in " + TABLENAME + " throw an exception " + " key=" );
                Util.logException(c, e);
                return false;
            }
        }

        public boolean delete(int _id) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, KEY_ID_TRANSACTIONS + "=" + _id, null);

                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "delete more than one row from " + TABLENAME + " key=" + _id);
                    return true;
                } else {
                    Util.logInformation(c, "delete from " + TABLENAME + " has no row to delete " + " key=" + _id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean get(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID_TRANSACTIONS + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID_TRANSACTIONS));

                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public List<StockTakingTransactions> get(String where) {
            List<StockTakingTransactions> arr = new ArrayList<StockTakingTransactions>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                Cursor c = shamelDB.query(TABLENAME, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    StockTakingTransactions obj = new StockTakingTransactions();
                    // get id
                    obj.itemID = c.getInt(c.getColumnIndex(KEY_ITEM_ID_TRANSACTIONS));
                    obj.itemName = c.getString(c.getColumnIndex(KEY_ITEM_NAME_TRANSACTIONS));
                    obj.quantity = c.getDouble(c.getColumnIndex(KEY_QUANTITY_TRANSACTIONS));
                    obj.stockTatkingID = c.getInt(c.getColumnIndex(KEY_STOCK_TAKING_ID_TRANSACTIONS));
                    obj.sessionID = c.getInt(c.getColumnIndex(KEY_SESSION_ID_TRANSACTIONS));
                    obj.unitID = c.getInt(c.getColumnIndex(KEY_UNIT_ID_TRANSACTIONS));
                    obj.unitName = c.getString(c.getColumnIndex(KEY_UNIT_NAME_TRANSACTIONS));
                    obj.stockTakingDate = c.getInt(c.getColumnIndex(KEY_STOCKTAKING_DATE));
                    obj.isPosted = c.getInt(c.getColumnIndex(KEY_IS_POSTED));


                    arr.add(obj);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }

        public boolean checkExisting(int _id) {
            try {
                String[] Columns = {KEY_ID_TRANSACTIONS};
                Cursor c = shamelDB.query(TABLENAME, Columns, KEY_ID_TRANSACTIONS + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public int checkExisting(String where) {
            int _id = -1;
            try {
                String[] Columns = {KEY_ID_TRANSACTIONS};
                Cursor c = shamelDB.query(TABLENAME, Columns, where, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    _id = c.getInt(c.getColumnIndex(KEY_ID_TRANSACTIONS));
                }
                c.close();
                return _id;
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return -1;
            }
        }

        public boolean deleteStockTakingTransaction(String where) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, where , null);

                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "delete more than one row from " + TABLENAME + " key=" );
                    return true;
                } else {
                    Util.logInformation(c, "delete from " + TABLENAME + " has no row to delete " + " key=" );
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" );
                Util.logException(c, e);
                return false;
            }
        }

    }

    // ///////////////////////////////XXX/////////////////////
    public class XXX {
        public int id;
        public String code, name;

        public XXX() {
            id = -1;
            code = name = "";
        }
    }

    public class XXXEntity {
        // Users Table Columns names
        public static final String KEY_ID = "id";
        public static final String KEY_CODE = "code";
        public static final String KEY_NAME = "name";

        String[] AllColumns = {KEY_ID, KEY_CODE, KEY_NAME};

        // used to make the copy paste more easy
        private final String TABLENAME = TABLE_XXX;

        public void createTable(SQLiteDatabase db) {
            try {
                String CREATE_TABLE = "CREATE TABLE " + TABLENAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CODE + " TEXT," + KEY_NAME + " TEXT" + ")";
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                Util.logException(c, e);
            }
        }

        // the default object
        // you can use this object to minimize the code
        public XXX obj;

        public XXXEntity() {
            obj = new XXX();
        }

        public boolean insert(XXX _obj) {
            try {
                // insert in the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_CODE, _obj.code);
                cv.put(KEY_NAME, _obj.name);
                long rowID = shamelDB.insert(TABLENAME, null, cv);
                if (rowID == -1) {
                    Util.logInformation(c, "insert into " + TABLENAME + " return -1 key=" + _obj.id);
                    return false;
                }
                return true;

            } catch (Exception e) {
                Util.logInformation(c, "insert method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean update(XXX _obj) {
            try {
                // update the table
                ContentValues cv = new ContentValues();
                cv.put(KEY_CODE, _obj.code);
                cv.put(KEY_NAME, _obj.name);
                int recordCount = shamelDB.update(TABLENAME, cv, KEY_ID + "=" + _obj.id, null);
                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "update more than one record from " + TABLENAME + " key=" + _obj.id);
                    return true;
                } else {
                    Util.logInformation(c, "update from " + TABLENAME + " has no row to update" + " key=" + _obj.id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "update method in " + TABLENAME + " throw an exception " + " key=" + _obj.id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean delete(int _id) {
            try {
                // delete from the table
                int recordCount = shamelDB.delete(TABLENAME, KEY_ID + "=" + _id, null);

                if (recordCount >= 1) {
                    if (recordCount > 1)
                        Util.logInformation(c, "delete more than one row from " + TABLENAME + " key=" + _id);
                    return true;
                } else {
                    Util.logInformation(c, "delete from " + TABLENAME + " has no row to delete " + " key=" + _id);
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "delete method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public boolean get(int _id) {
            try {
                Cursor c = shamelDB.query(TABLENAME, AllColumns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.code = c.getString(c.getColumnIndex(KEY_CODE));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "get by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public List<XXX> get(String where) {
            List<XXX> arr = new ArrayList<XXX>();
            try {

                String whereCol = null;
                if (where != null && where.trim().length() != 0)
                    whereCol = where;

                Cursor c = shamelDB.query(TABLENAME, AllColumns, whereCol, null, null, null, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    XXX obj = new XXX();
                    // get id
                    obj.id = c.getInt(c.getColumnIndex(KEY_ID));
                    obj.code = c.getString(c.getColumnIndex(KEY_CODE));
                    obj.name = c.getString(c.getColumnIndex(KEY_NAME));

                    arr.add(obj);
                }
                c.close();
            } catch (Exception e) {
                Util.logInformation(c, "get by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                arr = null;
            }
            return arr;
        }

        public boolean checkExisting(int _id) {
            try {
                String[] Columns = {KEY_ID};
                Cursor c = shamelDB.query(TABLENAME, Columns, KEY_ID + "=" + _id, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by id method in " + TABLENAME + " throw an exception " + " key=" + _id);
                Util.logException(c, e);
                return false;
            }
        }

        public int checkExisting(String where) {
            int _id = -1;
            try {
                String[] Columns = {KEY_ID};
                Cursor c = shamelDB.query(TABLENAME, Columns, where, null, null, null, null);
                if (c.moveToFirst() && !c.isAfterLast()) {
                    _id = c.getInt(c.getColumnIndex(KEY_ID));
                }
                c.close();
                return _id;
            } catch (Exception e) {
                Util.logInformation(c, "checkExisting by where method in " + TABLENAME + " throw an exception " + " where=" + where);
                Util.logException(c, e);
                return -1;
            }
        }
    }


}
