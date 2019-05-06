package com.digitaldestino.fcmClass;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.digitaldestino.R;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String title, alert;
    PendingIntent pendingIntentNo;
    NotificationManager notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("from", "From: " + remoteMessage.getFrom());
        Map<String, String> data = remoteMessage.getData();
        title = data.get("title");
        alert = data.get("alert");


        Intent noReceive = new Intent();
        noReceive.setAction("Cancel");
        noReceive.setAction("View");
        pendingIntentNo = PendingIntent.getBroadcast(this, 12345, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);

        showNotification(alert, noReceive);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(String alert, Intent intent) {
//        if (type!=null) {
//            if (!sessionToken.isEmpty()) {
//                if (type.equalsIgnoreCase("1001")) {
//                    intent = new Intent(getApplicationContext(), ConnectionRequest.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                } else {
//                    intent = new Intent(getApplicationContext(), MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                }
//            }
//            else {
//                intent = new Intent(getApplicationContext(), Login.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            }
//        }

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo_grid);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //  notification.sound =Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.definite);//Here is FILE_NAME is the name of file that you want to play
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyFirebaseMessagingService.this, channelId)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setContentText(alert)
                .setAutoCancel(true)
                // .setSubText(messageBody)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1, 1, 1})
                .setDefaults(Notification.DEFAULT_SOUND)
                //.addAction(R.drawable.remove_black, actionBtn2, pendingIntentNo)
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.definite))
                .setPriority(Notification.PRIORITY_HIGH)

                .setContentIntent(pendingIntent);
        mBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(notificationId, mBuilder.build());
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.logo_grid : R.drawable.logo_grid;
    }


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("token_fcm", "Refreshed token: " + token);
        BMSPrefs.putString(MyFirebaseMessagingService.this, Constants.DEVICE_TOKEN, token);

    }


    public static boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
