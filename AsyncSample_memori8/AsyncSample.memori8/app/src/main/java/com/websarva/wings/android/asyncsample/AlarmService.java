package com.websarva.wings.android.asyncsample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.websarva.wings.android.asyncsample.meet;


public class AlarmService extends Service {

    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;
    private long remainingTimeMillis;
    // 任意のデフォルトの時間（ミリ秒単位）を設定
    private static final long DEFAULT_TIMER_DURATION = 10 * 60 * 1000;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static final String ACTION_RESET_TIMER = "action_reset_timer";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setAlarm();

        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_RESET_TIMER.equals(action)) {
                // 残り時間をリセットする処理
                updateRemainingTime(DEFAULT_TIMER_DURATION);  // デフォルトの時間に変更するか、適切な値を設定
            }
        }

        return START_STICKY;
    }

    private void setAlarm() {
        Log.d("AlarmService", "setAlarm() called");

        // アラーム音の初期化
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);

        // CountDownTimerのセットアップ
        long timeDifference = 10 * 60 * 1000; // 10分
        countDownTimer = new CountDownTimer(timeDifference, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // millisUntilFinishedは残り時間をミリ秒単位で表す
                // 残り時間が10分になったときの処理
                if (millisUntilFinished <= 10000) { // 10秒未満
                    playAlarmSound();
                    Toast.makeText(AlarmService.this, "残り10秒です", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFinish() {
                // カウントダウンが終了したときの処理
                stopSelf();
            }
        };

        // カウントダウンを開始
        countDownTimer.start();
    }

    private void playAlarmSound() {
        // アラーム音再生
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void showNotification() {
        // Notificationを表示するIntent
        Intent notificationIntent = new Intent(this, meet.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // NotificationのチャンネルID
        String channelId = "alarm_channel";
        // NotificationManagerを取得
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Android 8.0以降はNotificationChannelが必要
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Alarm Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Notificationを構築
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("アラームが動作中です")
                .setContentText("タップしてアプリに戻る")
                .setContentIntent(pendingIntent)
                .setOngoing(true); // ユーザーがキャンセルできないようにする

        // Notificationを表示
        startForeground(1, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void updateRemainingTime(long millis) {
        remainingTimeMillis = millis;
    }
}