package ma.khabbachi.machine;

import static ma.khabbachi.machine.MainActivity.UPDATE_ACTIVITY_REQUEST_CODE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList id, reference , prix, marque;


    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList reference , ArrayList prix , ArrayList marque){
        this.activity=activity;
        this.context=context;
        this.id=id;
        this.reference= reference;
        this.prix=prix;
        this.marque=marque;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id_txt, reference_txt, prix_txt, marque_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt =itemView.findViewById(R.id.id_txt);
            reference_txt= itemView.findViewById(R.id.reference_txt);
            prix_txt = itemView.findViewById(R.id.prix_txt);
            marque_txt= itemView.findViewById(R.id.marque_txt);
            mainLayout= itemView.findViewById(R.id.mainLayout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {


        holder.id_txt.setText(String.valueOf(id.get(position)));
        holder.reference_txt.setText(String.valueOf(reference.get(position)));
        holder.prix_txt.setText(String.valueOf(prix.get(position)));
        holder.marque_txt.setText(String.valueOf(marque.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("reference", String.valueOf(reference.get(position)));
                intent.putExtra("prix", String.valueOf(prix.get(position)));
                intent.putExtra("marque", String.valueOf(marque.get(position)));
                ((Activity) context).startActivityForResult(intent, UPDATE_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }
}
