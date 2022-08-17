package com.example.ada;

public class MedicineModal {
    private String medicineName, medicineDate, medicineTime;
    private int id;

    public MedicineModal(String medicineName, String medicineTime, String medicineDate) {
        this.medicineName = medicineName;
        this.medicineDate = medicineDate;
        this.medicineTime = medicineTime;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineDate() {
        return medicineDate;
    }

    public void setMedicineDate(String medicineDate) {
        this.medicineDate = medicineDate;
    }

    public String getMedicineTime() {
        return medicineTime;
    }

    public void setMedicineTime(String medicineTime) {
        this.medicineTime = medicineTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
