package com1032.cw1.kf00084.kf00084_todolist;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import com1032.cw1.kf00084.kf00084_todolist.database.JobTable;
import com1032.cw1.kf00084.kf00084_todolist.provider.JobProvider;
import com1032.cw1.kf00084.kf00084_todolist.type.JobActivity;


public class MainActivity extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<Cursor>  {

    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    // private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private ListView tJobListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tJobListView = (ListView) findViewById(R.id.job_list);
        tJobListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, JobActivity.class);
                Uri todoUri = Uri.parse(JobProvider.CONTENT_URI + "/" + id);
                i.putExtra(JobProvider.CONTENT_ITEM_TYPE, todoUri);

                startActivity(i);
            }
        });

        fillData();

    }

    public void toJobActivity(View view){
        Intent i  = new Intent(this, JobActivity.class);
        startActivity(i);
    }

    private void fillData() {

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] {JobTable.COLUMN_TASK };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.task_text };

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this, R.layout.job, null, from,
                to, 0);

      tJobListView.setAdapter(adapter);
    }


    // creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { JobTable.COLUMN_ID, JobTable.COLUMN_TASK };
        CursorLoader cursorLoader = new CursorLoader(this,
                JobProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        adapter.swapCursor(null);
    }
}
