package com.example.ada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Viewmedicines extends AppCompatActivity {
    private ArrayList<MedicineModal> medicineModalArrayList;
    private Database database;
    private  MedicineRVAdapter medicineRVAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmedicines);

        medicineModalArrayList = new ArrayList<>();
        database = new Database(Viewmedicines.this);

        medicineModalArrayList = database.readMedicineData();

        medicineRVAdapter = new MedicineRVAdapter(medicineModalArrayList, Viewmedicines.this);
        recyclerView = findViewById(R.id.idRVMedicine);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Viewmedicines.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(medicineRVAdapter);
    }
}