package com1032.cw1.kf00084.kf00084_todolist.type;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import com1032.cw1.kf00084.kf00084_todolist.R;
import com1032.cw1.kf00084.kf00084_todolist.adapters.TaskArrayAdapter;
import com1032.cw1.kf00084.kf00084_todolist.database.JobTable;
import com1032.cw1.kf00084.kf00084_todolist.provider.JobProvider;

public class JobActivity extends ActionBarActivity {

    private final static String TAG= JobActivity.class.getSimpleName();
    List<String> tTaskList;
    ArrayAdapter tArrayAdapter;
    EditText tNewTaskText;
    ListView tTaskListView;
    private Uri todoUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        tTaskList= new ArrayList<>();
        tArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tTaskList);
        tTaskListView = (ListView) findViewById(R.id.taskListView);
        tTaskListView.setAdapter(tArrayAdapter);
        tTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            TextView task = (TextView)view.findViewById(R.id.task_text);
            if(task.getPaintFlags() == 16){
                task.setPaintFlags(task.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
           else {
                task.setPaintFlags( Paint.STRIKE_THRU_TEXT_FLAG);
            }
            }
    });

        Bundle extras = getIntent().getExtras();

        // check from the saved Instance
        todoUri = (savedInstanceState == null) ? null : (Uri) savedInstanceState
                .getParcelable(JobProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity
        if (extras != null) {
            todoUri = extras
                    .getParcelable(JobProvider.CONTENT_ITEM_TYPE);

            fillData(todoUri);
        }

    }

    private void fillData(Uri uri) {
        String[] projection = {JobTable.COLUMN_TASK};
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();

          String task= cursor.getString(cursor
                    .getColumnIndexOrThrow(JobTable.COLUMN_TASK));


            try {
                JSONArray jsonArray = new JSONArray(task);
                for(int i = 0; i < jsonArray.length(); i++){
                     tTaskList.add((String)jsonArray.get(i));
                }

                tArrayAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }


            cursor.close();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(JobProvider.CONTENT_ITEM_TYPE, todoUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {

        String sTask = new JSONArray(tTaskList).toString();

        if (tTaskList.isEmpty()) {
            return;
        }

        ContentValues values = new ContentValues();

        values.put(JobTable.COLUMN_TASK, sTask);

        if (todoUri == null) {
            // New todo
            todoUri = getContentResolver().insert(JobProvider.CONTENT_URI, values);
        } else {
            // Update todo
            getContentResolver().update(todoUri, values, null, null);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTask(View view) {

        if(tTaskList.size() < 125) {
            LayoutInflater li = LayoutInflater.from(this);
            LinearLayout newJobBaseLayout = (LinearLayout) li.inflate(R.layout.new_task_dialog, null);

            tNewTaskText = (EditText) newJobBaseLayout.getChildAt(0);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener(){

               public void onClick (DialogInterface dialog, int which) {
                   tTaskList.add(tNewTaskText.getText().toString());
                   tArrayAdapter.notifyDataSetChanged();
               }
               });
                    builder.setNegativeButton("cancel", null)
                    .setTitle("New Task");


            builder.setView(newJobBaseLayout);
            builder.show();
        }
        else{
            //notify you can only do 125 tasks in a single job
        }
        }

}
