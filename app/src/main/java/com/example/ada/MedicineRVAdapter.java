package com.example.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedicineRVAdapter extends RecyclerView.Adapter<MedicineRVAdapter.ViewHolder> {
    private ArrayList<MedicineModal> medicineModalArrayList;
    private Context context;

    public MedicineRVAdapter(ArrayList<MedicineModal> medicineModalArrayList, Context context) {
        this.medicineModalArrayList = medicineModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicineModal modal = medicineModalArrayList.get(position);
        holder.medicineNameTV.setText(modal.getMedicineName());
        holder.medicineTimeTV.setText(modal.getMedicineTime());
        holder.medicineDateTV.setText(modal.getMedicineDate());
    }

    @Override
    public int getItemCount() {
        return medicineModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView medicineNameTV, medicineTimeTV, medicineDateTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineNameTV = itemView.findViewById(R.id.idMedicineName);
            medicineTimeTV = itemView.findViewById(R.id.idMedicineTime);
            medicineDateTV = itemView.findViewById(R.id.idMedicineDate);

        }
    }
}
