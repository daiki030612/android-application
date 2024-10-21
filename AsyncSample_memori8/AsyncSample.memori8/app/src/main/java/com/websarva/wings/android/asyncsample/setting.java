package com.websarva.wings.android.asyncsample;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class setting extends AppCompatActivity {

    private Spinner languageSpinner;
    private Button changeLanguageButton;
    private boolean isLocaleChanged = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 戻るボタンが押された時の処理
        Button weatherButton = findViewById(R.id.button5);
        //ボタンがクリックされた時の処理
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                // Intentを利用して他のアクティビティに遷移する
                Intent intent = new Intent(setting.this, menu.class);
                startActivity(intent);
            }
        });

        languageSpinner = findViewById(R.id.languageSpinner);
        changeLanguageButton = findViewById(R.id.changeLanguageButton);

        String[] languages = {"日本語","English", "中文","Français","한국어","عربي","Deutsch","Italiano"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        languageSpinner.setAdapter(adapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    setLocale("ja");
                } else if (position == 1) {
                    setLocale("en");
                } else if (position == 2) {
                    setLocale("zh");
                } else if (position == 3) {
                    setLocale("fr");
                } else if (position == 4) {
                    setLocale("ko");
                } else if (position == 5) {
                    setLocale("ar");
                } else if (position == 6) {
                    setLocale("de");
                } else if (position == 7) {
                    setLocale("it");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // 何も選択されなかった場合の処理
            }
        });

        changeLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ボタンがクリックされたときの処理
                Toast.makeText(setting.this, R.string.toast_setting, Toast.LENGTH_SHORT).show();
                isLocaleChanged = true;
                recreate();
            }
        });
    }

    private void setLocale(String languageCode) {
        if (!isLocaleChanged) {
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            // recreate() メソッドはここで呼び出さない
        }
    }
}

