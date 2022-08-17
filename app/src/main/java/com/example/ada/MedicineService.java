package com.example.ada;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MedicineService extends Service {
    private Database readData;
    private ArrayList<MedicineModal> medicineModals;
    LocalDate localDate;
    LocalTime localTime;
    DateTimeFormatter dFormat,tFormat;
    private static final  String CHANNEL_ID = "App Service" ;
    private static final  String CHANNEL_FUNC = "Notify";
    public static final int NOTIFICATION_ID = 19875;
    public MedicineService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Notification Channel for OS >= Oreo
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_FUNC,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription("Medicine Notification Channel");
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        traverseDBForMatches();
        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void traverseDBForMatches(){
        readData = new Database(MedicineService.this);
        dFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
        tFormat = DateTimeFormatter.ofPattern("H:m");
        new Thread(() -> {
            while(true){
                medicineModals = readData.readMedicineData();
                localDate = LocalDate.now();
                localTime = LocalTime.now();
                for(MedicineModal m: medicineModals){
                    if(m.getMedicineDate().equalsIgnoreCase(dFormat.format(localDate))){
                        if(m.getMedicineTime().equalsIgnoreCase(tFormat.format(localTime))){
                            Log.v("ims", m.getMedicineName());
                            Notification builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setContentTitle(m.getMedicineName()+" at "+m.getMedicineTime())
                                    .setContentText("Medicine Reminder")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setTimeoutAfter(1000*60*30)
                                    .setAutoCancel(true)
                                    .build();
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                            notificationManager.notify(NOTIFICATION_ID, builder);
                        }
                    }
                }
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}