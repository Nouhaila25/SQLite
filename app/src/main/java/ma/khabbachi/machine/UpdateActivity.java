package ma.khabbachi.machine;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText reference_input, prix_input, marque_input;
    Button update_button, delete_button;
    String id, reference, prix , marque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        reference_input = findViewById(R.id.reference_input2);
        prix_input= findViewById(R.id.prix_input2);
        marque_input= findViewById(R.id.marque_input2);
        update_button= findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        getAndSetIntentData ();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);

                // Retrieve the updated data from EditText fields
                String updatedReference = reference_input.getText().toString().trim();
                String updatedPrix = prix_input.getText().toString().trim();
                String updatedMarque = marque_input.getText().toString().trim();

                // Check if the retrieved data is not empty
                if (!updatedReference.isEmpty() && !updatedPrix.isEmpty() && !updatedMarque.isEmpty()) {
                    // Update the data in the database
                    myDB.updateData(id, updatedReference, updatedPrix, updatedMarque);

                    // Indicate that the update was successful
                    Toast.makeText(UpdateActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();

                    // Set the result and finish the activity
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(UpdateActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestion du clic sur le bouton de retour
        if (item.getItemId() == android.R.id.home) {
            // Navigate back to MainActivity
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void getAndSetIntentData (){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("reference") && getIntent().hasExtra("prix") &&
                getIntent().hasExtra("marque")){
            //getting Data from intent
            id = getIntent().getStringExtra("id");
            reference = getIntent().getStringExtra("reference");
            prix = getIntent().getStringExtra("prix");
            marque = getIntent().getStringExtra("marque");
            // setting intent data
            reference_input.setText(reference);
            prix_input.setText(prix);
            marque_input.setText(marque);




        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + reference + " ?");
        builder.setMessage("Are you sure you want to delete " + reference + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteData(id);

                // Set the result and finish the activity
                setResult(RESULT_OK);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog, no further action needed
                dialog.dismiss();
                // Set the result and finish the activity
                setResult(RESULT_OK);
                finish();
            }
        });
        builder.create().show();
    }

}