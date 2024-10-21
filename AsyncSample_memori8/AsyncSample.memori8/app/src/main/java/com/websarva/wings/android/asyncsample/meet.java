package com.websarva.wings.android.asyncsample;

/**
 * https://otologic.jp/free/se/clock-page02.html#google_vignette　(アラームBGM)
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

//アラーム関連
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import android.os.Build;

import android.os.CountDownTimer;

import android.media.MediaPlayer;


public class meet extends AppCompatActivity {
    /**
     * 緯度フィールド。
     */
    private double _latitude = 0;
    /**
     * 経度フィールド
     */
    private double _longitude = 0;
    /**
     * FusedLocationProviderClientオブジェクトフィールド。
     */
    private FusedLocationProviderClient _fusedLocationClient;
    /**
     * LocationRequestオブジェクトフィールド。
     */
    private LocationRequest _locationRequest;
    /**
     * 位置情報が変更された時の処理を行うコールバックオブジェクトフィールド。
     */
    private OnUpdateLocation _onUpdateLocation;

    //アラーム
    private TimePicker timePicker;
    private Button setAlarmButton;
    private CountDownTimer countDownTimer;
    private TextView countdownTextView;
    private MediaPlayer mediaPlayer;
    public static final long DEFAULT_TIMER_DURATION = 10 * 60 * 1000; // デフォルトの時間（ミリ秒）


    //アラームここまで
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hour", getTimePickerHour());
        outState.putInt("minute", getTimePickerMinute());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);

        // FusedLocationProviderClientオブジェクトを取得。
        _fusedLocationClient = LocationServices.getFusedLocationProviderClient(meet.this);
        // LocationRequestのビルダーオブジェクトを生成。
        LocationRequest.Builder builder = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000);
        // LocationRequestオブジェクトを生成。
        _locationRequest = builder.build();
        // 位置情報が変更された時の処理を行うコールバックオブジェクトを生成。
        _onUpdateLocation = new OnUpdateLocation();

        timePicker = findViewById(R.id.timePicker);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // 選択された時間を処理するロジックをここに追加
            }
        });

        // アラームボタン
        setAlarmButton = findViewById(R.id.setAlarmButton);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // アラームサービスを起動
                startService(new Intent(meet.this, AlarmService.class));
                setAlarm();
            }
        });

        // 残り時間を表示するTextView
        countdownTextView = findViewById(R.id.countdownTextView);

        // MediaPlayerの初期化
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);  // your_sound_fileは追加した音声ファイルの名前

        // 再生
        mediaPlayer.start();

        // 停止
        mediaPlayer.stop();

        // リソース解放
        mediaPlayer.release();

        // 以前の状態が保存されている場合はそれを復元
        if (savedInstanceState != null) {
            int savedHour = savedInstanceState.getInt("hour");
            int savedMinute = savedInstanceState.getInt("minute");

            // ここで時間を復元して設定する処理を追加
            setTimePickerHour(savedHour);
            setTimePickerMinute(savedMinute);
        }

        // 戻るボタンが押された時の処理
        Button weatherButton = findViewById(R.id.button5);
        //ボタンがクリックされた時の処理
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                // Intentを利用して他のアクティビティに遷移する
                Intent intent = new Intent(meet.this, menu.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // ACCESS_FINE_LOCATIONとACCESS_COARSE_LOCATIONの許可が下りていないなら…
        if(ActivityCompat.checkSelfPermission(meet.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(meet.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 許可をACCESS_FINE_LOCATIONとACCESS_COARSE_LOCATIONに設定。
            String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
            // 許可を求めるダイアログを表示。その際、リクエストコードを1000に設定。
            ActivityCompat.requestPermissions(meet.this, permissions, 1000);
            // onResume()メソッドを終了。
            return;
        }
        // 位置情報の追跡を開始。
        _fusedLocationClient.requestLocationUpdates(_locationRequest, _onUpdateLocation, Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 位置情報のパーミションダイアログでかつ許可を選択したなら…
        if(requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            // 再度許可が下りていないかどうかのチェックをし、降りていないなら処理を中止。
            if(ActivityCompat.checkSelfPermission(meet.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(meet.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            // 位置情報の追跡を開始。
            _fusedLocationClient.requestLocationUpdates(_locationRequest, _onUpdateLocation, Looper.getMainLooper());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 位置情報の追跡を停止。
        _fusedLocationClient.removeLocationUpdates(_onUpdateLocation);
    }

    /**
     * 地図検索ボタンがタップされたときの処理メソッド。
     */
    public void onMapSearchButtonClick(View view) {
        // 入力欄に入力されたキーワード文字列を取得。
        EditText etSearchWord = findViewById(R.id.etSearchWord);
        String searchWord = etSearchWord.getText().toString();

        try {
            // 入力されたキーワードをURLエンコード。
            searchWord = URLEncoder.encode(searchWord, "UTF-8");
            // マップアプリと連携するURI文字列を生成。
            String uriStr = "geo:0,0?q=" + searchWord;
            // URI文字列からURIオブジェクトを生成。
            Uri uri = Uri.parse(uriStr);
            // Intentオブジェクトを生成。
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            // アクティビティを起動。
            startActivity(intent);
        }
        catch(UnsupportedEncodingException ex) {
            Log.e("meet", "検索キーワード変換失敗", ex);
        }
    }

    /**
     * 現在地の地図表示ボタンがタップされたときの処理メソッド。
     */
    public void onMapShowCurrentButtonClick(View view) {
        // フィールドの緯度と経度の値をもとにマップアプリと連携するURI文字列を生成。
        String uriStr = "geo:" + _latitude + "," + _longitude;
        // URI文字列からURIオブジェクトを生成。
        Uri uri = Uri.parse(uriStr);
        // Intentオブジェクトを生成。
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        // アクティビティを起動。
        startActivity(intent);
    }

    /**
     * 位置情報が変更された時の処理を行うコールバッククラス。
     */
    private class OnUpdateLocation extends LocationCallback {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            // 直近の位置情報を取得。
            Location location = locationResult.getLastLocation();
            if(location != null) {
                // locationオブジェクトから緯度を取得。
                _latitude = location.getLatitude();
                // locationオブジェクトから経度を取得。
                _longitude = location.getLongitude();
                // 取得した緯度をTextViewに表示。
                TextView tvLatitude = findViewById(R.id.tvLatitude);
                tvLatitude.setText(Double.toString(_latitude));
                // 取得した経度をTextViewに表示。
                TextView tvLongitude = findViewById(R.id.tvLongitude);
                tvLongitude.setText(Double.toString(_longitude));
            }
        }
    }

    private int getTimePickerHour() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getHour();
        } else {
            return timePicker.getCurrentHour();
        }
    }

    private int getTimePickerMinute() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getMinute();
        } else {
            return timePicker.getCurrentMinute();
        }
    }

    private void setTimePickerHour(int hour) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hour);
        } else {
            timePicker.setCurrentHour(hour);
        }
    }

    private void setTimePickerMinute(int minute) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setMinute(minute);
        } else {
            timePicker.setCurrentMinute(minute);
        }
    }

    private void setAlarm() {
        Log.d("meet", "setAlarm() called");

        int hour, minute;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        Log.d("meet", "Selected time: " + hour + ":" + minute);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long alarmTime = calendar.getTimeInMillis();

        Log.d("meet", "Alarm time in millis: " + alarmTime);

        // アラームを設定するためのIntent作成
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // AlarmManagerを使用してアラームを設定
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);

        // MediaPlayerの初期化
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound); // alarm_soundはres/raw フォルダに置いた音声ファイルの名前


        // CountDownTimerのセットアップ
        long currentTime = System.currentTimeMillis();
        long timeDifference = alarmTime - currentTime;

        countDownTimer = new CountDownTimer(timeDifference, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // millisUntilFinishedは残り時間をミリ秒単位で表す
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                seconds %= 60;
                long hours = minutes / 60;
                minutes %= 60;

                // 残り時間を表示する
                String countdownText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                countdownTextView.setText(countdownText);

                // 残り10分になったときの処理
                if (minutes == 10 && seconds == 0) {
                    // アラーム音を再生
                    playAlarmSound();
                    // ここにアラームを鳴らす処理を追加
                    Toast.makeText(meet.this, "残り10分です", Toast.LENGTH_SHORT).show();
                }

                // 0秒になったときの処理
                if (hours == 0 && minutes == 0 && seconds == 0) {
                    // アラーム音を再生
                    playAlarmSound();
                    // ここにアラームを鳴らす処理を追加
                    Toast.makeText(meet.this, "時間です", Toast.LENGTH_SHORT).show();
                    // カウントダウンを停止する
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                // カウントダウンが終了したときの処理
                countdownTextView.setText("00:00:00");
                // その他の終了処理を追加する場合はここに記述
            }
        };

        // カウントダウンを開始
        countDownTimer.start();

        Toast.makeText(this, "アラームが設定されました", Toast.LENGTH_SHORT).show();
    }

    private void playAlarmSound() {
        // アラーム音再生
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void resetAlarmService() {
        Intent intent = new Intent(this, AlarmService.class);
        intent.setAction(AlarmService.ACTION_RESET_TIMER);
        startService(intent);
    }
}