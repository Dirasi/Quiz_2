package com.example.pustikom.adapterplay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pustikom.adapterplay.com.example.pustikom.adapter.StudentArrayAdapter;
import com.example.pustikom.adapterplay.com.example.pustikom.db.StudentDbHelper;
import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;
import com.example.pustikom.adapterplay.com.example.pustikom.user.StudentList;

import java.util.ArrayList;


/**
 * Created by pustikom on 07/10/16.
 */

public class StudentActivity extends AppCompatActivity {
    private FloatingActionButton addButton;
    //private static ArrayList<Student> studentList;
    private StudentArrayAdapter studentArrayAdapter;
    private ListView listItem;
    private StudentDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        studentArrayAdapter = new StudentArrayAdapter(this,new StudentList());
        listItem = (ListView) findViewById(R.id.list_item);
        listItem.setAdapter(studentArrayAdapter);

        //register button
        addButton  = (FloatingActionButton) findViewById(R.id.floatingAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, StudentFormActivity.class);
                intent.putExtra("mode",0);
                startActivity(intent);
            }
        });

        //set listener for each list item
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StudentActivity.this, StudentFormActivity.class);
                intent.putExtra("mode",1);

                // get current student from StudentList based on position then past student as Intent Extra
                Student student = Student.getStudentList().get(position);
                intent.putExtra("student", student);
                startActivity(intent);
            }
        });
        db = new StudentDbHelper(getApplicationContext());
    }

    @Override
    public void onResume(){
        super.onResume();
        // Refresh list post add or edit student
        //this can be done by reload studentArrayAdapter to current List
        //then reload listItems
        StudentList list = Student.getStudentList();
        new DataSyncTask().execute(list);




    }

    private StudentList populateStudentDummies(){

        StudentList studentList = new StudentList();
        Student student = new Student("3145136188","TRI FEBRIANA SIAMI",1,"tri@mhs.unj.ac.id","0858xxxxxx");
        studentList.add(student);
        db.insertStudent(student);
        return studentList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.createDummyItem:
                // add action
                StudentList dummies = populateStudentDummies();
                studentArrayAdapter = new StudentArrayAdapter(this, dummies);
                listItem.setAdapter(studentArrayAdapter);
                Student.setStudentList(dummies);

                return true;
            case R.id.clearListItem:
                // add action
                studentArrayAdapter = new StudentArrayAdapter(this, new StudentList());
                listItem.setAdapter(studentArrayAdapter);
                Student.getStudentList().clear();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DataSyncTask extends AsyncTask<StudentList, Void, StudentArrayAdapter> {
        protected StudentArrayAdapter doInBackground(StudentList... list) {
            StudentArrayAdapter studentAdapter= new StudentArrayAdapter(getApplicationContext(), list[0]);
            return studentAdapter;

        }

        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(StudentArrayAdapter studentAdapter) {
            listItem.setAdapter(studentAdapter);
        }
    }
}
