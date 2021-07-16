package com.example.simplepayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Below line is deprecated
        // bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setSelectedItemId(R.id.fragmentHome);

// Probando ejemplo
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //DocumentReference docRef = db.collection("cities").document("SF");
        db.collection("cities").document("SF").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG11", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("TAG11", "No such document");
                    }
                } else {
                    Log.d("TAG11", "get failed with ", task.getException());
                }
            }
        });

        // Get installation ID
        //String userID2 = FirebaseInstallations.getInstance().getId().getResult();
        //Log.d("Installations", "Installation ID: " + userID2);
        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String userID = task.getResult();
                            Log.d("TAG11 Installations", "Installation ID: " + userID);
                            Date currentTime = Calendar.getInstance().getTime();

                            // Verifing if user is registered


                            db.collection("Users")
                                    .whereEqualTo("UniqueID", userID)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.d("TAG11", document.getId() + " => " + document.getData());
                                                }
                                                boolean isEmpty = task.getResult().isEmpty();
                                                // If ID is missing, create user AND ID
                                                if(isEmpty){
                                                    Map<String, Object> formu2 = new HashMap<>();
                                                    formu2.put("UniqueID", userID);
                                                    formu2.put("date_init", currentTime);

                                                    db.collection("Users").add(formu2).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Log.d("TAG11", "Se creó usuario.");
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d("TAG11", "No se creó usuario.");
                                                        }
                                                    });
                                                }
                                                //Fin
                                            } else {
                                                Log.e("TAG11", "Error getting documents");
                                            }

                                        }
                                    });


                        } else {
                            Log.d("Installations", "Unable to get Installation ID");
                        }
                    }
                });

        //Buscar usuario
        //String userID2 = "zero";


        /*
        db.collection("Users").get()

        Map<String, Object> formu = new HashMap<>();
        formu.put("Description", descrip2);

         */


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Switch is going to be deprecated in these cases !! That is why I'm using if else
        int idOfItem = item.getItemId();

        if (idOfItem == R.id.fragmentHome){
            FragmentHome fragmentHome1 = new FragmentHome();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragmentHome1).commit();
            return true;
        } else if(idOfItem == R.id.fragmentDetails){
            FragmentDetails fragmentDetails1 = new FragmentDetails();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragmentDetails1).commit();
            return true;
        } else if (idOfItem == R.id.fragmentProfile){
            FragmentProfile fragmentProfile1 = new FragmentProfile();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragmentProfile1).commit();
            return true;
        }

        return false;
    }



}