package com.EdmonAntonyan.aerorush;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LiderboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeaderboardAdapter adapter;
    private List<UserScore> userScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liderboard);

        recyclerView = findViewById(R.id.leaderboardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userScores = new ArrayList<>();
        adapter = new LeaderboardAdapter(userScores);
        recyclerView.setAdapter(adapter);
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LiderboardActivity.this, MainActivity.class));
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance(
                        "https://aerorushgame-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<UserScore> scoreList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("username").getValue(String.class);
                    Integer points = dataSnapshot.child("highScore").getValue(Integer.class);

                    if (name != null && points != null) {
                        scoreList.add(new UserScore(name, points));
                    }
                }

                // Сортировка и отображение только топ-10
                Collections.sort(scoreList, (s1, s2) -> Integer.compare(s2.getPoints(), s1.getPoints()));
                if (scoreList.size() > 10) {
                    scoreList = scoreList.subList(0, 10);
                }

                adapter.setScores(scoreList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseData", "Error: " + error.getMessage());
            }
        });
    }
}