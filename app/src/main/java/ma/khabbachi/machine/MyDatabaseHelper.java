package ma.khabbachi.machine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Machines.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME= "machine";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_REFERENCE = "reference";
    private static final String COLUMN_PRIX = "prix";
    private static final String COLUMN_MARQUE = "marque";



     MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_REFERENCE + " TEXT, " +
                        COLUMN_PRIX + " TEXT, " +
                        COLUMN_MARQUE + " TEXT);";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addMachine( String reference, String prix , String marque){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_REFERENCE , reference);
        cv.put(COLUMN_PRIX , prix);
        cv.put(COLUMN_MARQUE , marque);

        long result = db.insert(TABLE_NAME, null , cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData (){
        String qurey = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =null;
        if (db != null){
            cursor = db.rawQuery(qurey, null);

        }
        return cursor;
    }

    public void updateData(String id, String reference, String prix, String marque) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_REFERENCE, reference);
        cv.put(COLUMN_PRIX, prix);
        cv.put(COLUMN_MARQUE, marque);

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();


        long result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Data deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
