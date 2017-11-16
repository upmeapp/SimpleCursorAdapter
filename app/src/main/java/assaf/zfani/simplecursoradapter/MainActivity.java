package assaf.zfani.simplecursoradapter;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    EditText nameEd,familyEd;
    SQLiteDatabase database;
    SimpleCursorAdapter adapter;
    private static String DATABASE_NAME  = "NamesDataBase";
    private static String TABLE_NAME = "tbl_names";
    private static String TABLE_ROW_NAME = "name";
    private static String TABLE_ROW_FAMILY = "family";
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        String CreateTableCommand = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_ROW_NAME + " TEXT, " + TABLE_ROW_FAMILY +" TEXT);";
        database.execSQL(CreateTableCommand);

        initViews(); //יותר אלגנטי למציאת הווידגטים
        findViewById(R.id.buttonSaved).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEd.getText().toString();
                String family = familyEd.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put(TABLE_ROW_NAME,name);
                contentValues.put(TABLE_ROW_FAMILY,family);

                database.insert(TABLE_NAME,null,contentValues);


            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cursor!=null)
                {
                    cursor.close();
                }
                cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
                String[] from = new String[] {TABLE_ROW_NAME,TABLE_ROW_FAMILY};
                int[] to  = new int[]{R.id.textViewName,R.id.textViewFamily};
                adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.linecontent,cursor,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                ((ListView)findViewById(R.id.listView)).setAdapter(adapter);


            }
        });

        findViewById(R.id.buttonStartWith).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] cols = new String[]{"_id",TABLE_ROW_NAME,TABLE_ROW_FAMILY};
                String where = TABLE_ROW_NAME +" LIKE '"+ nameEd.getText().toString()+"%'";
                if(cursor!=null)
                {
                    cursor.close();
                }
                cursor = database.query(TABLE_NAME,cols,where,null,null,null,null);
                String[] from = new String[] {TABLE_ROW_NAME,TABLE_ROW_FAMILY};
                int[] to  = new int[]{R.id.textViewName,R.id.textViewFamily};
                adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.linecontent,cursor,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                ((ListView)findViewById(R.id.listView)).setAdapter(adapter);

            }
        });


    }
    private void initViews()
    {
        nameEd = (EditText)findViewById(R.id.editTextName);
        familyEd = (EditText)findViewById(R.id.editTextFamily);

    }
}
