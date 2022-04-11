package com.ewubd.servicebhai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

public class homePage extends AppCompatActivity {

    Button logout, profile, problempost, problemshow, inboxButton;
    SharedPreferences myPref;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        logout = findViewById(R.id.homeLogout);
        logout.setOnClickListener(v->logout());
        profile = findViewById(R.id.userProfile);
        problempost = findViewById(R.id.problemPost);
        myPref = getApplicationContext().getSharedPreferences("userId", MODE_PRIVATE);
        userid = myPref.getInt("loggedInID", -1);
        System.out.println(userid);

        profile.setOnClickListener(v->userProfile());
        problempost.setOnClickListener(v -> problemPage());
        problemshow = findViewById(R.id.problemShow);
        problemshow.setOnClickListener(v -> {
            Intent intent = new Intent(this, problemShow.class);
            startActivity(intent);
        });
        inboxButton = findViewById(R.id.inbox);
        inboxButton.setOnClickListener(v->inboxPro());


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, inbox.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My Notification")
                .setSmallIcon(R.drawable.profile_layout_shaper)
                .setContentTitle("You have a new message")
                .setContentText("Someone Wants to get Hire you")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());

    }
    void logout(){
        myPref.edit().putInt("loggedInID", -1).apply();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    void userProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
    }
    void problemPage(){
        Intent intent = new Intent(this, problemPosting.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    void inboxPro(){
        Intent intent=  new Intent(this, inbox.class);
        startActivity(intent);
    }
}