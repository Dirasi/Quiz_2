package com.example.pustikom.adapterplay.com.example.pustikom.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;
import android.util.Log;

import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;
import static android.content.ContentValues.TAG;
import java.util.ArrayList;
/**
 * Created by Dian on 11/23/2016.
 */

public class StudentDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "college.db";
    private static final int DATABASE_VERSION = 1;
    private Appendable displayView;

    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_STUDENT_TABLE = "CREATE TABLE" + StudentContract.TABLE_STUDENT + " " +
                StudentContract._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                StudentContract.COLUMN_NIM + "TEXT NOT NULL," +
                StudentContract.COLUMN_NAME + "TEXT NOT NULL," +
                StudentContract.COLUMN_GENDER + "INTEGER," +
                StudentContract.COLUMN_EMAIL + "TEXT," +
                StudentContract.COLUMN_PHONE + "TEXT";
        db.execSQL(SQL_CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(StudentDbHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + StudentContract.TABLE_STUDENT);
        onCreate(db);
    }

    public void insertStudent(SQLiteDatabase db, Student student) {
        SQLiteDatabase wdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_NIM, student.getNoreg());
        values.put(StudentContract.COLUMN_NAME, student.getName());
        values.put(StudentContract.COLUMN_GENDER, student.getGender());
        values.put(StudentContract.COLUMN_EMAIL, student.getMail());
        values.put(StudentContract.COLUMN_PHONE, student.getPhone());
        wdb.insert(StudentContract.TABLE_STUDENT, null, values);
    }

    public void updateStudent(SQLiteDatabase db, Student student) {
        SQLiteDatabase wdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_NIM, student.getNoreg());
        values.put(StudentContract.COLUMN_NAME, student.getName());
        values.put(StudentContract.COLUMN_GENDER, student.getGender());
        values.put(StudentContract.COLUMN_EMAIL, student.getMail());
        values.put(StudentContract.COLUMN_PHONE, student.getPhone());
        String condition = StudentContract._ID + "= ?";
        String[] conditionArgs = {student.getId() + ""};
        wdb.update(StudentContract.TABLE_STUDENT, values, condition, conditionArgs);
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StudentContract._ID,
                StudentContract.COLUMN_NIM,
                StudentContract.COLUMN_NAME,
                StudentContract.COLUMN_GENDER,
                StudentContract.COLUMN_EMAIL,
                StudentContract.COLUMN_PHONE};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                StudentContract.TABLE_STUDENT,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                 // The sort order

        //TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(StudentContract._ID + " - " +
                    StudentContract.TABLE_STUDENT + " - " +
                    StudentContract.COLUMN_NIM + " - " +
                    StudentContract.COLUMN_NAME + " - " +
                    StudentContract.COLUMN_GENDER + " - " +
                    StudentContract.COLUMN_EMAIL + " - " +
                    StudentContract.COLUMN_PHONE + " - " +
            );
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(StudentContract._ID);
            int nimColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_NIM);
            int nameColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_NAME);
            int genderColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_GENDER);
            int mailColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_EMAIL);
            int phoneColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_PHONE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentNim = cursor.getString(nimColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                String currentMail = cursor.getString(mailColumnIndex);
                String currentPhone = cursor.getString(phoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentNim + " - " +
                        currentName + " - " +
                        currentGender + " - " +
                        currentMail + " - " +
                        currentPhone + " - " +
                ));
            }
            finally{
                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            }
        }
    }

