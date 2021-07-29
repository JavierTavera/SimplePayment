package com.example.simplepayment;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InstallationAuth {

public void instAuth(String whatActivity) {
    // DB connection
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    AddExpense theVar = new AddExpense();

    // Get installation ID
    FirebaseInstallations.getInstance().getId()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (task.isSuccessful()) {
                        String userID = task.getResult();
                        if(whatActivity == "AddExpense")
                        theVar.setVarID(userID);
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
                                                if(whatActivity == "AddExpense")
                                                    theVar.setDocumentUserID(document.getId());
                                            }
                                            boolean isEmpty = task.getResult().isEmpty();
                                            // If ID is missing, create user AND ID
                                            if (isEmpty) {
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
                                            //End
                                        } else {
                                            Log.e("TAG11", "Error getting documents");
                                        }

                                    }
                                });
                        // Fin

                    } else {
                        Log.d("Installations", "Unable to get Installation ID");
                    }
                }
            });
}

}
