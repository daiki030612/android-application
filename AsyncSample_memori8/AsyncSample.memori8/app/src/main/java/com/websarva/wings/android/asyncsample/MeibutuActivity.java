package com.websarva.wings.android.asyncsample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MeibutuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meibutu);

        //戻るボタンが押された時の処理
        Button recommendButton = findViewById(R.id.button5);
        //ボタンがクリックされた時の処理を追加
        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                //Intentを利用して他のアクティビティに遷移する
                Intent intent = new Intent(MeibutuActivity.this, menu.class);
                startActivity(intent);
            }
        });





            // 名物情報のデータ（例）
            List<MeibutuItem> meibutuData = new ArrayList<>();
        meibutuData.add(new MeibutuItem("北海道 - 函館塩ラーメン", "魅力ポイント: 塩味のスープが特徴の函館発祥のラーメン。シンプルながら深い味わい。"));
        meibutuData.add(new MeibutuItem("青森 - 弘前津軽そば", "魅力ポイント: 豊かな風味が広がる津軽そばは、地元産のそば粉を使用しています。"));
        meibutuData.add(new MeibutuItem("岩手 - 八幡平ねぎそば", "魅力ポイント: 新鮮な八幡平ねぎをたっぷりと使った風味豊かなそば。"));
        meibutuData.add(new MeibutuItem("宮城 - 松島牡蠣しゃぶしゃぶ", "魅力ポイント: 松島で水揚げされた新鮮な牡蠣をしゃぶしゃぶで楽しむ贅沢な料理。"));
        meibutuData.add(new MeibutuItem("秋田 - 角館きりたんぽ鍋", "魅力ポイント: きりたんぽを鍋に入れて食べる郷土料理。もちもちとした食感が楽しい。"));
        meibutuData.add(new MeibutuItem("山形 - 山形三色そば", "魅力ポイント: 三色のそばが一度に楽しめる山形ならではのそば料理。"));
        meibutuData.add(new MeibutuItem("福島 - いわきもも豚のカツ丼", "魅力ポイント: いわき産のもも豚を使用したカツ丼。ジューシーでボリューミーな味わい。"));
        meibutuData.add(new MeibutuItem("茨城 - 水戸牛すき焼き", "魅力ポイント: 水戸牛の上質な肉を使った贅沢なすき焼き。甘みと旨みが広がります。"));
        meibutuData.add(new MeibutuItem("栃木 - 宇都宮焼きそば", "魅力ポイント: 中華そばの上に焼きそばがトッピングされた宇都宮風の美味しい一品。"));
        meibutuData.add(new MeibutuItem("群馬 - 館林焼きうどん", "魅力ポイント: 館林市で愛されている焼きうどん。香ばしさがクセになります。"));
        meibutuData.add(new MeibutuItem("埼玉 - 川越しゃも焼き", "魅力ポイント: 川越名物のしゃも焼きは、甘辛いたれとジューシーな鶏肉が絶妙なバランス。"));
        meibutuData.add(new MeibutuItem("千葉 - 木更津風アボカド丼", "魅力ポイント: 新鮮なアボカドと海の幸がたっぷりの贅沢な丼物。"));
        meibutuData.add(new MeibutuItem("東京 - 東京湾アジの寿司", "魅力ポイント: 東京湾で獲れた新鮮なアジを使用した握り寿司。"));
        meibutuData.add(new MeibutuItem("神奈川 - 鎌倉しらす丼", "魅力ポイント: 鎌倉で水揚げされた新鮮なしらすを使用した海の幸丼。"));
        meibutuData.add(new MeibutuItem("新潟 - 新潟へぎそば", "魅力ポイント: 新潟で育ったへぎを使用した香り高いへぎそば。"));
        meibutuData.add(new MeibutuItem("富山 - 富山湾のます寿司", "魅力ポイント: 富山湾で獲れたますを使用した握り寿司。濃厚な脂が楽しめます。"));
        meibutuData.add(new MeibutuItem("石川 - 金沢名物 かぶら寿司", "魅力ポイント: かぶら寿司は木の器に盛られた、地元の新鮮な食材を使用した寿司。"));
        meibutuData.add(new MeibutuItem("福井 - 若狭塩焼きそば", "魅力ポイント: 若狭町で愛される塩焼きそば。シンプルながら奥深い味わい。"));
        meibutuData.add(new MeibutuItem("山梨 - 甲州名物 ほうとう鍋", "魅力ポイント: ほうとう鍋は太い麺と野菜がたっぷり入った郷土料理。温かいスープが心地よい。"));
        meibutuData.add(new MeibutuItem("長野 - 信州名物 そばがき汁", "魅力ポイント: そばがきと具材が入った優しい味わいの汁物。信州ならではのヘルシーな一品。"));
        meibutuData.add(new MeibutuItem("岐阜 - 高山ラーメン", "魅力ポイント: 高山市で愛される濃厚な醤油ベースのラーメン。こってりとした味わい。"));
        meibutuData.add(new MeibutuItem("静岡 - 三島名物 うなぎパイ", "魅力ポイント: 三島で有名なうなぎパイは、サクサクのパイ生地に甘辛いうなぎの風味が広がります。"));
        meibutuData.add(new MeibutuItem("愛知 - 尾張名物 ひつまぶし", "魅力ポイント: 鶏の炭火焼きをご飯に掛け、たれと一緒に食べる尾張名物のご飯料理。"));
        meibutuData.add(new MeibutuItem("三重 - 伊勢名物 おはぎ", "魅力ポイント: 伊勢市で愛されるおはぎは、もちもちとしたお餅と甘いあんこが絶妙なハーモニー。"));
        meibutuData.add(new MeibutuItem("滋賀 - 長浜名物 たこ焼きせんべい", "魅力ポイント: たこ焼きの味わいをせんべいにアレンジした長浜の名物。サクサクとした食感が楽しい。"));
        meibutuData.add(new MeibutuItem("京都 - 京都名物 茶碗蒸し", "魅力ポイント: 季節の旬の食材を使った茶碗蒸しは、優雅な京都の伝統料理。"));
        meibutuData.add(new MeibutuItem("大阪 - 天王寺たこ焼き", "魅力ポイント: 天王寺で人気のたこ焼きは、ふんわりとした生地にジューシーなたこが入っています。"));
        meibutuData.add(new MeibutuItem("兵庫 - 加古川名物 たたきポーク", "魅力ポイント: 加古川で育った豚肉をたたいて柔らかく仕上げた、ジューシーなたたきポーク。"));
        meibutuData.add(new MeibutuItem("奈良 - 奈良名物 どて焼き", "魅力ポイント: どて焼きは奈良の伝統的な和菓子で、もちもちとした食感が特徴。"));
        meibutuData.add(new MeibutuItem("和歌山 - 和歌山名物 くだものゼリー", "魅力ポイント: 和歌山の新鮮なくだものを使用したさわやかなゼリー。"));
        meibutuData.add(new MeibutuItem("鳥取 - 鳥取名物 かに汁", "魅力ポイント: 鳥取で獲れた新鮮なカニを使用した濃厚なかに汁。"));
        meibutuData.add(new MeibutuItem("島根 - 出雲名物 いも汁", "魅力ポイント: 出雲で愛されるいも汁は、甘さとほくほく感が絶妙。"));
        meibutuData.add(new MeibutuItem("岡山 - 岡山名物 ひもの寿司", "魅力ポイント: 岡山のひもの寿司は、見た目も美しく、新鮮な魚介が贅沢に使われています。"));
        meibutuData.add(new MeibutuItem("広島 - 広島名物 お好み焼き", "魅力ポイント: 広島風お好み焼きは、もちもちとした生地に具材がたっぷりと入った絶品。"));
        meibutuData.add(new MeibutuItem("山口 - 下関名物 ふぐ刺し", "魅力ポイント: 下関で水揚げされた新鮮なふぐを薄く切って生のまま楽しむ贅沢な一品。"));
        meibutuData.add(new MeibutuItem("徳島 - 鳴門名物 うずしおラーメン", "魅力ポイント: 鳴門市で愛されるうずしおラーメンは、海の香りが感じられる絶品の一杯。"));
        meibutuData.add(new MeibutuItem("香川 - 讃岐うどん", "魅力ポイント: 香川県を代表する讃岐うどんは、もちもちとした麺と澄んだつゆが特徴。"));
        meibutuData.add(new MeibutuItem("愛媛 - 伊予名物 かんざし寿司", "魅力ポイント: 伊予の郷土料理かんざし寿司は、見た目も美しく、美味しいごちそう。"));
        meibutuData.add(new MeibutuItem("高知 - 高知名物 かつおのたたき", "魅力ポイント: 高知の名物かつおのたたきは、深い味わいとプリプリの食感が楽しめます。"));
        meibutuData.add(new MeibutuItem("福岡 - 博多名物 もつ鍋", "魅力ポイント: 博多もつ鍋はもつの旨味が凝縮された濃厚なスープが特徴。"));
        meibutuData.add(new MeibutuItem("佐賀 - 佐賀名物 有田焼きうどん", "魅力ポイント: 有田焼の器で提供されるうどんは、見た目も美しく、風味豊か。"));
        meibutuData.add(new MeibutuItem("長崎 - 長崎名物 皿うどん", "魅力ポイント: 長崎の皿うどんは、太い麺と濃厚なスープが絶妙なバランス。"));
        meibutuData.add(new MeibutuItem("熊本 - 熊本名物 あんかけ焼きそば", "魅力ポイント: 熊本のあんかけ焼きそばは、とろみのあるあんかけが絶品。"));
        meibutuData.add(new MeibutuItem("大分 - 大分名物 とり天そば", "魅力ポイント: 大分のとり天そばは、サクサクとしたとり天と香り高いそばの相性が抜群。"));
        meibutuData.add(new MeibutuItem("宮崎 - 宮崎名物 ちゃんぽん", "魅力ポイント: 宮崎のちゃんぽんは、あっさりとしたスープと新鮮な具材が楽しめます。"));
        meibutuData.add(new MeibutuItem("鹿児島 - 鹿児島名物 かごしまもつ鍋", "魅力ポイント: 鹿児島のもつ鍋は、もつの旨味がたっぷり詰まった濃厚なスープが絶品。"));
        meibutuData.add(new MeibutuItem("沖縄 - 沖縄そば", "魅力ポイント: 沖縄そばは、やわらかい麺と澄んだつゆが特徴。地元の風味を存分に楽しめます。"));


        MeibutuAdapter adapter = new MeibutuAdapter(this, R.layout.meibutu_list_item, meibutuData);
            ListView listView = findViewById(R.id.meibutuListView);
            listView.setAdapter(adapter);



        }
    }

