package com.example.aashna.mynotes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerData extends AppCompatActivity {

    RecyclerView myRecycler;
    DatabaseReference ref1;//, ref2;
    MyAdapter myAdapter;
    ArrayList<Data> data = new ArrayList<>();
    ValueEventListener valueEventListener;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_data);

        ref1 = FirebaseDatabase.getInstance().getReference().child("document");
        // ref2 = FirebaseDatabase.getInstance().getReference().child("");
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        myRecycler = findViewById(R.id.recyclerView);
        myRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        data.clear();
        myRecycler.setLayoutManager(layoutManager);

        valueEventListener = ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Data dt = ds.getValue(Data.class);
                    data.add(dt);

                   // Log.d("DATA Value...", " >>>>>>>>>>>>>" + ds.getValue(Data.class));
                }//end of for loop DataSnapshot()
                if (data != null) {
                    myAdapter = new MyAdapter(data, RecyclerData.this);
                    progressBar.setVisibility(View.GONE);
                    myRecycler.setAdapter(myAdapter);
                                    }

            }//end of onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RecyclerData.this, "OOOOPppsssssss", Toast.LENGTH_SHORT).show();
            }
        });


    }//end of onCreate()

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ref1.removeEventListener(valueEventListener);
    }
}

