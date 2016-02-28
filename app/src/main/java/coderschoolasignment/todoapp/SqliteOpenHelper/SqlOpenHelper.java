package coderschoolasignment.todoapp.SqliteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import coderschoolasignment.todoapp.Note;

/**
 * Created by Administrator on 25/02/2016.
 */
public class SqlOpenHelper{
    private Context context;
    private SqlHelper helper;

    public SqlOpenHelper(Context context) {
        this.context = context;
        helper = new SqlHelper(context);
    }

    public long insertNote(Note n)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqlHelper.KEY_title,n.getTitle());
        values.put(SqlHelper.KEY_content,n.getContent());
        values.put(SqlHelper.KEY_time,n.getTime());
        values.put(SqlHelper.KEY_status,n.getStatus());
        values.put(SqlHelper.KEY_color,n.getColor());
        values.put(SqlHelper.KEY_link,n.getLink());
        values.put(SqlHelper.KEY_imageDir,n.getImageDir());
        values.put(SqlHelper.KEY_deadline,n.getDeadline());

        long result = db.insert(SqlHelper.TABLE_note,null,values);
        db.close();
        return result;
    }

    public ArrayList<Note> getAllNotes()
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Note> result = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SqlHelper.TABLE_note,null);
        if(cursor.moveToFirst())
        {
            while (cursor.isAfterLast()==false)
            {
                Note n = new Note();
                n.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_id))));
                n.setTitle(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_title)));
                n.setContent(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_content)));
                n.setTime(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_time)));
                n.setColor(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_color)));
                n.setLink(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_link)));
                n.setImageDir(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_imageDir)));
                n.setDeadline(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_deadline)));
                n.setStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_status))));
                result.add(n);
                cursor.moveToNext();
            }
        }
        db.close();
        cursor.close();
        return result;
    }

    public Note getLastNote()
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        Note note = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + SqlHelper.TABLE_note +" ORDER BY " +SqlHelper.KEY_id +" DESC LIMIT 1; ",null);
        if(cursor.moveToFirst())
        {
            note = new Note();
            note.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_id))));
            note.setTitle(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_title)));
            note.setContent(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_content)));
            note.setTime(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_time)));
            note.setColor(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_color)));
            note.setStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_status))));
            note.setLink(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_link)));
            note.setImageDir(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_imageDir)));
            note.setDeadline(cursor.getString(cursor.getColumnIndex(SqlHelper.KEY_deadline)));
        }
        db.close();
        cursor.close();
        return note;
    }

    public int deleteNote(int id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        int result = db.delete(SqlHelper.TABLE_note,SqlHelper.KEY_id+" = ?",new String[]{String.valueOf(id)} );
        db.close();
        return result;
    }

    public int UpdateNote(Note note)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqlHelper.KEY_title,note.getTitle());
        values.put(SqlHelper.KEY_content,note.getContent());
        values.put(SqlHelper.KEY_status,note.getStatus());
        values.put(SqlHelper.KEY_link,note.getLink());
        values.put(SqlHelper.KEY_imageDir,note.getImageDir());
        values.put(SqlHelper.KEY_deadline,note.getDeadline());
        int result=  db.update(SqlHelper.TABLE_note,values,SqlHelper.KEY_id +" =?",new String[]{String.valueOf(note.getId())});

        db.close();
        return result;
    }


    static class SqlHelper extends SQLiteOpenHelper{
        private static final String DB_notemanager = "NoteManager";
        private static final int DB_VERSION = 2;
        private static final String TABLE_note = "tb_Note";
        private static final String KEY_id = "Id";
        private static final String KEY_title = "Title";
        private static final String KEY_content = "Content";
        private static final String KEY_time = "Time";
        private static final String KEY_color = "Color";
        private static final String KEY_status="Status";
        private static final String KEY_link="Link";
        private static final String KEY_imageDir="ImageDir";
        private static final String KEY_deadline="Deadline";


        private Context context;

        private static final String CREATE_TABLE_NOTE = "CREATE TABLE " + TABLE_note + "(" + KEY_id +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_title + " TEXT,"+KEY_content+" TEXT," +KEY_time +" TEXT,"+KEY_color+" TEXT,"+KEY_link+" TEXT,"+KEY_imageDir+" TEXT,"
                +KEY_deadline+" TEXT," +KEY_status+" INTEGER);";
        private static final String DROP_TABLE_NOTE = "DROP TABLE " + TABLE_note + " IF EXISTS";



        public SqlHelper(Context context) {
            super(context, DB_notemanager, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try
            {
                db.execSQL(CREATE_TABLE_NOTE);
            }catch (SQLException e)
            {
                Toast.makeText(context, e+"", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try
            {
                db.execSQL(DROP_TABLE_NOTE);
                onCreate(db);
            }catch (SQLException e)
            {
                Toast.makeText(context, e+"", Toast.LENGTH_LONG).show();
            }
        }
    }
}
