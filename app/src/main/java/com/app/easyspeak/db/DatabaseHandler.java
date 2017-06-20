package com.app.easyspeak.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.easyspeak.model.User;
import com.app.easyspeak.model.UserSession;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "easyspeakenglish";

    // Contacts table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_SESSION = "session";


    // Contacts Table Columns names
    private static final String USER_KEY_ID = "id";
    private static final String KEY_USER_NAME = "userName";
    private static final String USER_KEY_PWD = "password";
    private static final String USER_KEY_EMAIL = "email";
    private static final String USER_KEY_FNAME = "firstName";
    private static final String USER_KEY_SNAME = "secondName";
    private static final String USER_KEY_DOB = "dateofBirth";
    private static final String USER_KEY_MOB_NUM = "mobile";

    // Contacts Table Columns names
    private static final String SESSION_KEY_ID = "id";
    private static final String SESSION_USER_ID = "userId";
    private static final String SESSION_IS_ACTIVE = "isActive";
    private static final String SESSION_USER_NAME= "userName";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = createUserQuery();
        Log.v("CREATE_USER_TABLE ", CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        String CREATE_SESSION_TABLE = createSessionQuery();
        Log.v("CREATE_SESSION_TABLE ", CREATE_SESSION_TABLE);

        db.execSQL(CREATE_SESSION_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }
    private String createSessionQuery(){

        String query = "CREATE TABLE " + TABLE_SESSION + "("
                + SESSION_KEY_ID + " INTEGER PRIMARY KEY,"
                + SESSION_USER_ID + " TEXT,"
                + SESSION_USER_NAME + " TEXT,"
                + SESSION_IS_ACTIVE + " INTEGER " + " )";

        return query;
    }
    private String createUserQuery(){

        String query = "CREATE TABLE " +  TABLE_USER+ " ( "
                + USER_KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_NAME + " TEXT,"
                + USER_KEY_PWD + " TEXT,"
                + USER_KEY_EMAIL + " TEXT,"
                + USER_KEY_FNAME + " TEXT,"
                + USER_KEY_SNAME + " TEXT,"
                + USER_KEY_DOB + " TEXT,"
                + USER_KEY_MOB_NUM + " TEXT " + " )";

        return query;
    }
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public long addUserFromLogin(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getUserName()); // Contact Name
        values.put(USER_KEY_PWD, user.getPassword()); // Contact Phone
        values.put(USER_KEY_EMAIL, user.getUserName()); // Contact Phone


        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
        return id;
    }
    // Adding remember me functionality
    public boolean addUserSession(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SESSION_IS_ACTIVE, 1); // Contact Phone
        values.put(SESSION_USER_ID, user.getId()); // Contact Phone
        values.put(SESSION_USER_NAME, user.getUserName()); // Contact Phone

        // Inserting Row
        long id = db.insert(TABLE_SESSION, null, values);
        db.close(); // Closing database connection
        return true;
    }
    // Getting single contact
    public User getContact(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { USER_KEY_ID,
                        KEY_USER_NAME, USER_KEY_PWD, USER_KEY_EMAIL, USER_KEY_FNAME, USER_KEY_SNAME,  USER_KEY_DOB, USER_KEY_MOB_NUM}, USER_KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7));
        // return user
        return user;
    }
    // Getting single contact
    public User getContactByUserName(User user) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { USER_KEY_ID,
                        KEY_USER_NAME, USER_KEY_PWD, USER_KEY_EMAIL, USER_KEY_FNAME, USER_KEY_SNAME,  USER_KEY_DOB, USER_KEY_MOB_NUM}, KEY_USER_NAME + "=?",
                new String[] { user.getUserName() }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor.getCount() != 0 ) {
            System.out.print("ssssssssssss");
            user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        }else{
            System.out.print("elseeeee");
            user.setId("0");
        }
        // return user
        return user;
    }

    public UserSession getUserSession() {
        Log.v("getUserSession ","");
        UserSession session = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            //String query = "SELECT column1,column2,column3 FROM table "
            final StringBuilder query = new StringBuilder("SELECT ");
            query.append(SESSION_KEY_ID).append(", ").append(SESSION_USER_ID).append(", ").append(SESSION_USER_NAME).append(", ").append(SESSION_IS_ACTIVE).append(" FROM ")
                    .append(TABLE_SESSION);

            Log.v("user  is ", query.toString());

            Cursor cursor = db.rawQuery(query.toString(), null);

            if (cursor != null)
                cursor.moveToFirst();

            if (cursor.getCount() != 0 ){
                session = new UserSession(cursor.getString(0), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
                Log.v("session  is ", session.toString());
            }
        }catch(SQLException e){
            Log.v("getUserSession exec", e.toString());
            }

        // return user
        return session;
    }

    public boolean logoutUser(User user) {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(SESSION_IS_ACTIVE,0); //These Fields should be your String values of actual column names

            String where = "id=?";
            String[] whereArgs = new String[] {String.valueOf(user.getId())};
            db.update(TABLE_SESSION, cv, where, whereArgs);
            UserSession userSession = getUserSession();
            Log.v("updated ", userSession.toString());
            return true;

        }catch(SQLException e){
            Log.v("getUserSession exec", e.toString());
        }
        return false;
    }

    public User updateUserProfile(User user) {

        try{
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(USER_KEY_DOB,user.getDateofBirth());
            cv.put(USER_KEY_EMAIL,user.getEmail());
            cv.put(USER_KEY_FNAME,user.getFirstName());
            cv.put(USER_KEY_MOB_NUM,user.getMobileNumber());
            cv.put(USER_KEY_PWD,user.getPassword());
            cv.put(USER_KEY_SNAME,user.getSecondName());


            String where = "id=?";
            String[] whereArgs = new String[] {String.valueOf(user.getId())};
            db.update(TABLE_USER, cv, where, whereArgs);
            user = getContact(Integer.parseInt(user.getId()));
            Log.v("userObj ", user.toString());
            return user;

        }catch(SQLException e){
            Log.v("getUserSession exec", e.toString());
        }
        return user;
    }


//                USER_KEY_ID,KEY_USER_NAME,USER_KEY_PWD,USER_KEY_EMAIL,USER_KEY_FNAME
//                USER_KEY_SNAME,USER_KEY_DOB,USER_KEY_MOB_NUM
}