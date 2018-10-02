package com.example.wanjiku.ownstore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wanjiku.ownstore.data.PetContract;
import com.example.wanjiku.ownstore.data.PetContract.PetEntry;
import com.example.wanjiku.ownstore.data.PetdbHelper;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    TextView tv_petName,tv_pet_summary;
    ListView item_list;
    PetCursorAdapter mCursorAdapter;
    /** Identifier for the pet data loader */
    private static final int PET_LOADER = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,EditorActivity.class);
                startActivity(i);
            }
        });

        // Find the ListView which will be populated with the pet data
        ListView petListView = (ListView) findViewById(R.id.lv_pets);

        mCursorAdapter = new PetCursorAdapter(this, null, false);
        petListView.setAdapter(mCursorAdapter);
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);


        // Kick off the loader

        //getLoaderManager().initLoader(PET_LOADER,null, (android.app.LoaderManager.LoaderCallbacks<Object>) this);
        getSupportLoaderManager().initLoader(PET_LOADER,null,this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void insertPet(){
        //SQLiteDatabase db =mdbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PetEntry.COLUMN_NAME,"Toto");
        values.put(PetEntry.COLUMN_BREED,"Terrier");
        values.put(PetEntry.COLUMN_GENDER,PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_WEIGHT,7);


        Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI,values);
        //Log.v("MainActivity","New row id"+ newRowID);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
               insertPet();
               //displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_NAME,
                PetEntry.COLUMN_BREED };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                PetEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    /* try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database)
           // displayView.setText("Number of rows in pets database table: " + cursor.getCount() + "\n\n");
            //displayView.append(PetEntry._ID+" - "+PetEntry.COLUMN_NAME + "\n");

            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_WEIGHT);

            // Setup an Adapter to create a list item for each row of pet data in the Cursor.
            PetCursorAdapter adapter = new PetCursorAdapter(this,cursor,false);
            petListview.setAdapter(adapter);

            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);


                displayView.append(("\n" +currentID + "-"+ currentName + " - " + currentBreed +" - " +currentGender + "-" + currentWeight));

            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }*/
}
