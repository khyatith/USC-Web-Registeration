package usc.edu.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import usc.edu.Common.GIFunctions;

/**
 * Class to create all the tables (if needed) and/or creating queries
 */
public class DBHelper extends SQLiteOpenHelper {

    //database name
    public static final String DB_NAME = "USCWebApp.db";
    public static final int DB_VERSION = 1;

    //table name
    public static final String TABLE_USERS="Users";

    //User Table columns
    private static final String USERID="UserId";
    private static final String USERUSCID="UserUSCId";
    private static final String FIRSTNAME="FirstName";
    private static final String LASTNAME="LastName";
    private static final String LOCAL_ADDRESS="LocalAddress";
    private static final String LOCAL_CONTACT="LocalContact";
    private static final String DEN_STUDENT="DenStudent";
    private static final String START_SEMESTER="StartSemester";
    private static final String SCHOOL_ID="SchoolId";
    private static final String DEPARTMENT_ID="DepartmentId";
    private static final String USERNAME="UserName";
    private static final String PASSWORD="Password";

    //create table User string
    private static final String CREATE_TABLE_USER= "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" + USERID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USERUSCID + " TEXT," + FIRSTNAME + " TEXT," + LASTNAME + " TEXT," + LOCAL_ADDRESS + " TEXT," + LOCAL_CONTACT + " TEXT," + DEN_STUDENT + " INTEGER," + START_SEMESTER + " STRING," + SCHOOL_ID + " INTEGER," + DEPARTMENT_ID + " INTEGER," + USERNAME + " STRING," + PASSWORD + " STRING" + ")";



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
    public Long createUsers(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //put values in the user table

        values.put(USERUSCID, user.UserUSCId);
        values.put(FIRSTNAME, user.firstName);
        values.put(LASTNAME, user.lastName);
        values.put(LOCAL_ADDRESS, user.LocalAddress);
        values.put(LOCAL_CONTACT, user.LocalContact);
        values.put(DEN_STUDENT, user.DenStudent);
        values.put(START_SEMESTER, user.StartSemester);
        values.put(SCHOOL_ID, user.schoolId);
        values.put(DEPARTMENT_ID, user.DeptId);
        values.put(USERNAME, user.UserName);
        values.put(PASSWORD,user.password);

        // insert row
        Long user_id = db.insert(TABLE_USERS, null, values);
        return user_id;
    }
    public List<User> findUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> user = new ArrayList<>();
        String selectQuery="SELECT * FROM Users WHERE UserName = '"+username.trim()+"' AND Password = '"+password.trim()+"';";
        Log.e("log", selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                User u = new User();
                u.setUserUSCId(c.getString((c.getColumnIndex(USERUSCID))));
                u.setUserName((c.getString(c.getColumnIndex(USERNAME))));
                u.setFirstName(c.getString(c.getColumnIndex(FIRSTNAME)));
                u.setLastName(c.getString(c.getColumnIndex(LASTNAME)));
                u.setDenStudent(c.getInt(c.getColumnIndex(DEN_STUDENT)));
                // adding to todo list
                user.add(u);
            } while (c.moveToNext());
        }
        return user;
    }
}
