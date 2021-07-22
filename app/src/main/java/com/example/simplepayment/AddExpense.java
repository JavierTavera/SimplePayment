package com.example.simplepayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddExpense extends AppCompatActivity {

    // ID of user
    public static String IDofUser;

    public String getVarID(){
        return IDofUser;
    }

    public static void setVarID(String varID){
        IDofUser = varID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        ArrayList<String> arraylistShare = new ArrayList<>();
        arraylistShare.add("I pay. Share in equal parts");
        arraylistShare.add("He pays. Share in equal parts");

        ArrayAdapter arrayAd = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arraylistShare);
        AutoCompleteTextView auto1 = (AutoCompleteTextView)findViewById(R.id.auto1);
        auto1.setAdapter(arrayAd);

        /// Initilizing auth

        // Authentication of installation
        Log.d("TAG11","Verificando 1");
        InstallationAuth inAuth = new InstallationAuth();
        inAuth.instAuth("AddExpense");
        Log.d("TAG11","Verificando 2");
        //IDofUser = inAuth.getVarID();
        Log.d("TAG11","Verificando 3: " + IDofUser);
        /// Finalizing auth



    }

    public void dataOnFirestore(View v){
        String descrip2, person2, amount1, options;
        int amount2;
        //byte tipo2;

        TextInputLayout descrip = findViewById(R.id.textField1);
        descrip2 = descrip.getEditText().getText().toString();
        //Toast.makeText(this, descrip2, Toast.LENGTH_SHORT).show();

        TextInputLayout amount = findViewById(R.id.textField2);
        amount1 = amount.getEditText().getText().toString();
        amount2 = Integer.parseInt(amount1);

        TextInputLayout person = findViewById(R.id.textField3);
        person2 = person.getEditText().getText().toString();

        TextInputLayout options2 = findViewById(R.id.textField4);
        options = options2.getEditText().getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
    }



}