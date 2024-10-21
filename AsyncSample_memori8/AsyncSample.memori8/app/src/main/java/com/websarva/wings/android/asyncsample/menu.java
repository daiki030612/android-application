package com.websarva.wings.android.asyncsample;
/**
 * <menuの参考文献>
 * ボタンを押したときの画面遷移方法について参照(2024年1月8日参照)
 * https://www.bing.com/ck/a?!&&p=f10a7138d7bd0179JmltdHM9MTcwNDU4NTYwMCZpZ3VpZD0xNDg0YTZhMi1kNWI1LTYwNDYtMWNkOS1iNTcwZDRiNDYxMWImaW5zaWQ9NTIyOA&ptn=3&ver=2&hsh=3&fclid=1484a6a2-d5b5-6046-1cd9-b570d4b4611b&psq=android+studio+%e3%83%9c%e3%82%bf%e3%83%b3%e3%82%92%e6%8a%bc%e3%81%97%e3%81%9f%e3%82%89%e7%94%bb%e9%9d%a2%e9%81%b7%e7%a7%bb&u=a1aHR0cHM6Ly9qYS5zdGFja292ZXJmbG93LmNvbS9xdWVzdGlvbnMvNjg4NzMvYW5kcm9pZC1zdHVkaW8lZTMlODElYTclZTMlODMlOWMlZTMlODIlYmYlZTMlODMlYjMlZTMlODIlYWYlZTMlODMlYWElZTMlODMlODMlZTMlODIlYWYlZTYlOTklODIlZTMlODElYWIlZTclOTQlYmIlZTklOWQlYTIlZTklODElYjclZTclYTclYmIlZTMlODElOTUlZTMlODElOWIlZTMlODIlOGIlZTYlOTYlYjklZTYlYjMlOTU&ntb=1
 *
 * <weatherの参考文献>
 * ・【はじめてのKotlinプログラミング(29)】お天気アプリ～APIと非同期処理（コルーチン/Coroutine）～　
 * https://howcang.com/2021/12/28/kt29/　
 * ・Kotlin覚書 - KotlinからJavaにデコードする方法　https://qiita.com/ke__kyukyun1828/items/384972110df2152bf530
 *
 * <Meibutuの参考文献>
 * ４７都道府県別特産物・名物・名所一覧｜会話が盛り上がるネタ
 * https://dekirukaiwajutu.com/category11/entry267.html
 *
 * <meetの参考文献>
 * ・ImplicitlntentSample(meetにおける地図表示)
 * ・MediaSample(アラーム機能でアラーム音を鳴らす)
 * ・『プログラミング・開発の備忘録』指定時刻や定期的に実行するアラームの実装方法【Androidアプリ開発】(2024年1月20日参照)
 * https://pg.akihiro-takeda.com/android-alarm/
 *
 * <settingの参考文献>
 * https://developer.android.com/guide/topics/resources/providing-resources?hl=ja
 *
 * <画像、音声>
 * background_image.jpg↓
 * https://www.bing.com/images/search?view=detailV2&ccid=8OLkJueh&id=A70505120FFC38C2BCD4D6BA35AB48F6DF2BA99A&thid=OIP.8OLkJuehfpSEUPewcm4R_QHaNK&mediaurl=https%3a%2f%2fdivnil.com%2fwallpaper%2fiphone12%2fimg%2fapp%2fn%2fo%2fnoon-grassland-galaxy-s5-nature-wallpapers-in-1080-x-1920_72e777a6c884237104b3bc39a805cd28_raw.jpg&exph=1920&expw=1080&q=Piccel6+%e3%81%ae%e7%94%bb%e9%9d%a2%e3%82%b5%e3%82%a4%e3%82%ba%e3%81%ab%e5%90%88%e3%81%86%e6%99%b4%e3%82%8c%e3%81%ae%e7%94%bb%e5%83%8f&simid=608012729442835066&FORM=IRPRST&ck=B985C5FBAA3B682449623A17955E5C93&selectedIndex=12&itb=0&ajaxhist=0&ajaxserp=0
 *
 * icon.jpg↓
 * http://goto-ikuei.ac.jp/10hojin/images/2010-0422-1546.jpg
 *
 * ic_notification.png↓
 * https://www.bing.com/images/search?view=detailV2&ccid=%2fHqbHprA&id=AB4C23891FFEDCC46120283F7921134E05048E15&thid=OIP._HqbHprAmu3Q4KGISJqexQAAAA&mediaurl=https%3a%2f%2ficon-pit.com%2fwp-content%2fuploads%2f2018%2f11%2falarm_bell_icon_2106-300x300.png&exph=300&expw=300&q=%e3%82%a2%e3%83%a9%e3%83%bc%e3%83%a0%e7%94%bb%e5%83%8f%e3%83%95%e3%83%aa%e3%83%bc&simid=608015010085293699&FORM=IRPRST&ck=E5A66D469737795731E412A501AFE6FE&selectedIndex=0&itb=1&ajaxhist=0&ajaxserp=0
 *
 * alarm_sound.mp3↓
 * https://otologic.jp/free/se/clock-page02.html
 * (時計　アラーム02)
 **/

//https://www.bing.com/images/search?view=detailV2&ccid=8OLkJueh&id=A70505120FFC38C2BCD4D6BA35AB48F6DF2BA99A&thid=OIP.8OLkJuehfpSEUPewcm4R_QHaNK&mediaurl=https%3a%2f%2fdivnil.com%2fwallpaper%2fiphone12%2fimg%2fapp%2fn%2fo%2fnoon-grassland-galaxy-s5-nature-wallpapers-in-1080-x-1920_72e777a6c884237104b3bc39a805cd28_raw.jpg&exph=1920&expw=1080&q=Piccel6+%e3%81%ae%e7%94%bb%e9%9d%a2%e3%82%b5%e3%82%a4%e3%82%ba%e3%81%ab%e5%90%88%e3%81%86%e6%99%b4%e3%82%8c%e3%81%ae%e7%94%bb%e5%83%8f&simid=608012729442835066&FORM=IRPRST&ck=B985C5FBAA3B682449623A17955E5C93&selectedIndex=12&itb=0&ajaxhist=0&ajaxserp=0
//背景画像取得先↑

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //天気ボタンが押された時の処理
        Button weatherButton = findViewById(R.id.button5);
        //ボタンがクリックされた時の処理を追加
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                //Intentを利用して他のアクティビティに遷移する
                Intent intent = new Intent(menu.this, weather.class);
                startActivity(intent);
            }
        });

        //おすすめボタンが押された時の処理
        Button recommendButton = findViewById(R.id.button2);
        //ボタンがクリックされた時の処理を追加
        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                //Intentを利用して他のアクティビティに遷移する
                Intent intent = new Intent(menu.this, MeibutuActivity.class);
                startActivity(intent);
            }
        });

        //トークボタンが押された時の処理
        Button talkButton = findViewById(R.id.button3);
        //ボタンがクリックされた時の処理を追加
        talkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                //Intentを利用して他のアクティビティに遷移する
                Intent intent = new Intent(menu.this, meet.class);
                startActivity(intent);
            }
        });

        //設定ボタンが押された時の処理
        Button settingButton = findViewById(R.id.button4);
        //ボタンがクリックされた時の処理を追加
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                //Intentを利用して他のアクティビティに遷移する
                Intent intent = new Intent(menu.this, setting.class);
                startActivity(intent);
            }
        });
    }
}