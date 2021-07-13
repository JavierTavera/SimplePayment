package com.example.simplepayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    }

    public void dataOnFirestore(View v){
        String descrip2;
        TextInputLayout descrip = findViewById(R.id.textField1);
        descrip2 = descrip.getEditText().getText().toString();
        //Toast.makeText(this, descrip2, Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> formu = new HashMap<>();
        formu.put("Description", descrip2);
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
    }



}