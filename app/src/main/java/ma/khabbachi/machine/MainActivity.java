 package ma.khabbachi.machine;
 import androidx.annotation.Nullable;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import android.content.Intent;
 import android.database.Cursor;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Toast;

 import com.google.android.material.floatingactionbutton.FloatingActionButton;

 import java.util.ArrayList;

 public class MainActivity extends AppCompatActivity {

     RecyclerView recyclerView;
     FloatingActionButton add_button;
     MyDatabaseHelper myDB;
     ArrayList<String> id, reference, prix, marque;
     CustomAdapter customAdapter;

     private static final int ADD_ACTIVITY_REQUEST_CODE = 1;
     public static final int UPDATE_ACTIVITY_REQUEST_CODE = 2;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         recyclerView = findViewById(R.id.recyclerView);
         add_button = findViewById(R.id.add_button);

         add_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, AddActivity.class);
                 startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE);
             }
         });

         myDB = new MyDatabaseHelper(MainActivity.this);
         id = new ArrayList<>();
         reference = new ArrayList<>();
         prix = new ArrayList<>();
         marque = new ArrayList<>();

         storeDataInArrays();
         customAdapter = new CustomAdapter(MainActivity.this,this, id, reference, prix, marque);
         recyclerView.setAdapter(customAdapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         // Check if the result is from AddActivity and indicates success
         if (requestCode == ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
             // Refresh the data in the MainActivity
             refreshData();
         } else if (requestCode == UPDATE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
             // Refresh the data in the MainActivity after updating
             refreshData();
         }
     }

     private void refreshData() {
         // Clear existing data
         id.clear();
         reference.clear();
         prix.clear();
         marque.clear();

         // Reload data from the database
         storeDataInArrays();

         // Notify the adapter that the data has changed
         customAdapter.notifyDataSetChanged();
     }

     void storeDataInArrays() {
         Cursor cursor = myDB.readAllData();
         if (cursor.getCount() == 0) {
             Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
         } else {
             while (cursor.moveToNext()) {
                 id.add(cursor.getString(0));
                 reference.add(cursor.getString(1));
                 prix.add(cursor.getString(2));
                 marque.add(cursor.getString(3));
             }
         }
     }
 }
