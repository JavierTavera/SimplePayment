package com.example.simplepayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddExpense extends AppCompatActivity {

    // ID of user
    public static String IDofUser, documentUserID, concatenacion;
    //public String getVarID(){        return IDofUser;    }
    public static void setVarID(String varID){
        IDofUser = varID;
    }
    public static void setDocumentUserID(String varDocID){
        documentUserID = varDocID;
    }

    //public String[] people = new String[]{            "Mamá", "Papá", "Otro1", "Otro2"    };
    ArrayList<String> names = new ArrayList<String>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        /// Initilizing auth
        // Authentication of installation
        Log.d("TAG11","Verificando 1");
        // Get Installation ID and Document ID
        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String userID = task.getResult();
                            IDofUser = userID;
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
                                                    documentUserID = document.getId();
                                                }
                                                boolean isEmpty = task.getResult().isEmpty();
                                                // If ID is missing, create user AND ID
                                                if (isEmpty) {
                                                    documentUserID="";
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
                                                } else {
                                                    /// People autocomplete from Firebase
                                                    db.collection("Users").document(documentUserID).collection("People").get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                                            Log.d("TAG11. Ver -2. ", document.getId() + " => " + document.getData());
                                                                            concatenacion = document.getData().get("Name").toString();
                                                                            names.add(concatenacion);
                                                                        }
                                                                        /// Autocomplete for people
                                                                        String[] people = new String[names.size()];
                                                                        for (int j = 0; j < names.size(); j++) {
                                                                            people[j] = names.get(j);
                                                                        }
                                                                        AutoCompleteTextView editPeople = findViewById(R.id.auto2);
                                                                        ArrayAdapter<String> adapterPeople = new ArrayAdapter<String>(AddExpense.this, R.layout.support_simple_spinner_dropdown_item, people);
                                                                        editPeople.setAdapter(adapterPeople);
                                                                        for (int k = 0; k < people.length; k++) {
                                                                            Log.d("Tag11", people[k]);
                                                                        }
                                                                    } else {
                                                                        Log.d("TAG11. Ver 0. ", "Error getting documents: ", task.getException());
                                                                    }
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
        //InstallationAuth inAuth = new InstallationAuth();
        //inAuth.instAuth("AddExpense");
        Log.d("TAG11","Verificando 2");
        //IDofUser = inAuth.getVarID();
        //Log.d("TAG11","Verificando 3: " + IDofUser);
        /// Finalizing auth


        /// Dropdown list of options to share
        String[] share = getResources().getStringArray(R.array.howToShare);

        ArrayAdapter arrayAd = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, share);
        AutoCompleteTextView auto1 = (AutoCompleteTextView)findViewById(R.id.auto1);
        auto1.setAdapter(arrayAd);
        /// End options of share









    }

    public void dataOnFirestore(View v){
        String descrip2, person2, amount1, options;
        int amount2;
        //byte tipo2;

        TextInputLayout descrip = findViewById(R.id.textField1);
        descrip2 = descrip.getEditText().getText().toString();

        TextInputLayout amount = findViewById(R.id.textField2);
        amount1 = amount.getEditText().getText().toString();
        amount2 = Integer.parseInt(amount1);

        TextInputLayout person = findViewById(R.id.textField3);
        person2 = person.getEditText().getText().toString();

        TextInputLayout options2 = findViewById(R.id.textField4);
        options = options2.getEditText().getText().toString();

        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("TAG11","Verificando 4");
        Map<String, Object> formu = new HashMap<>();
        formu.put("InstAuth", IDofUser);
        formu.put("Description", descrip2);
        formu.put("Amount", amount2);
        formu.put("Person", person2);
        formu.put("Options", options);
        Log.d("TAG11","Verificando 5");

        db.collection("Prueba").add(formu).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AddExpense.this, descrip2, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddExpense.this, "Error in db", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("TAG11","Verificando 6");

        /// Lookout for the person in "people"

        db.collection("Users").document(documentUserID).collection("People").whereEqualTo("Name", person2)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    boolean isEmpty = task.getResult().isEmpty();
                    /// Creates "people" in Users
                    if(isEmpty) {
                        Map<String, Object> formu2 = new HashMap<>();
                        formu2.put("Name", person2);
                        Log.d("TAG11", "Verificando 7");

                        db.collection("Users").document(documentUserID).collection("People").add(formu2).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG11", "Verificando 8");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG11", "Verificando 8: Error.");
                            }
                        });
                    }
                } else {
                    Log.d("TAG11", "Verificando 7: Error");
                }
            }
        });


    }

}