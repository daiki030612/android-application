package com.websarva.wings.android.asyncsample;

import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
//IntentとButtonを追加
import android.content.Intent;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.widget.EditText;
import java.net.URLEncoder;

public class weather extends AppCompatActivity {
    /**
     * ログに記載するタグ用の文字列。
     */
    private static final String DEBUG_TAG = "AsyncSample";
    /**
     * お天気情報のURL。
     */
    private static final String WEATHERINFO_URL = "https://api.openweathermap.org/data/2.5/weather?lang=";
    /**
     * お天気APIにアクセスすするためのAPIキー。
     * ※※※※※この値は各自のものに書き換える!!※※※※※
     */
    private static final String APP_ID = "efe0e36e3f50f6481e1ead26eaa0d13f";

    private EditText etSearch;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        String[] from = {"name"};
        int[] to = {android.R.id.text1};

        etSearch = findViewById(R.id.etSearch);
        searchButton = findViewById(R.id.searchButton);

        // 検索ボタンがクリックされた時の処理を追加
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 検索バーから県名を取得
                String prefecture = etSearch.getText().toString();
                // 検索結果に基づいて天気情報を取得
                getWeatherInfo(prefecture);
            }
        });

        //戻るボタンが押された時の処理
        Button recommendButton = findViewById(R.id.button5);
        //ボタンがクリックされた時の処理を追加
        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                //Intentを利用して他のアクティビティに遷移する
                Intent intent = new Intent(weather.this, menu.class);
                startActivity(intent);
            }
        });
    }
    public void onClickButton(android.view.View view) {
    }

    /**
     * 天気情報を取得するメソッド。
     *
     * @param prefecture 検索する県名。
     */
    private void getWeatherInfo(String prefecture) {
        // 検索バーに入力された県名に基づいて天気情報のURLを生成
        String encodedPrefecture = null;
        try {
            encodedPrefecture = URLEncoder.encode(prefecture, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String WEATHERINFO = getString(R.string.URL);
        String urlFull = WEATHERINFO_URL + WEATHERINFO + "&q=" + encodedPrefecture + "&appid=" + APP_ID;
        // 天気情報の取得処理を実行
        receiveWeatherInfo(urlFull);
    }

    /**
     * リストビューに表示させる天気ポイントリストデータを生成するメソッド。
     *
     * @return 生成された天気ポイントリストデータ。
     */


    /**
     * お天気情報の取得処理を行うメソッド。
     *
     * @param urlFull お天気情報を取得するURL。
     */
    @UiThread
    private void receiveWeatherInfo(final String urlFull) {
        WeatherInfoBackgroundReceiver backgroundReceiver = new WeatherInfoBackgroundReceiver(urlFull);
        ExecutorService executorService  = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(backgroundReceiver);
        String result = "";
        try {
            result = future.get();
            // 空のレスポンスの場合に対処
            if (result.isEmpty()) {
                Log.e(DEBUG_TAG, "APIからのレスポンスが空です。");
                // ここで適切なエラーハンドリングを行う（ユーザーに通知、デフォルトのデータを表示など）
                return;
            }

        }
        catch(ExecutionException ex) {
            Log.w(DEBUG_TAG, "非同期処理結果の取得で例外発生: ", ex);
        }
        catch(InterruptedException ex) {
            Log.w(DEBUG_TAG, "非同期処理結果の取得で例外発生: ", ex);
        }
        showWeatherInfo(result);
    }

    /**
     * 取得したお天気情報JSON文字列を解析の上、画面に表示させるメソッド。
     *
     * @param result 取得したお天気情報JSON文字列。
     */
    @UiThread
    private void showWeatherInfo(String result) {
        // 都市名。
        String cityName = "";
        // 天気。
        String weather = "";
        // 最高気温
        String maxTemp = "";
        // 最低気温
        String minTemp = "";
        //湿度
        String humidity = "";

        try {
            // ルートJSONオブジェクトを生成。
            JSONObject rootJSON = new JSONObject(result);

            // 都市名文字列を取得。
            cityName = rootJSON.getString("name");

            // 天気情報JSON配列オブジェクトを取得。
            JSONArray weatherJSONArray = rootJSON.getJSONArray("weather");

            // 現在の天気情報JSONオブジェクトを取得。
            JSONObject weatherJSON = weatherJSONArray.getJSONObject(0);

            // 現在の天気情報文字列を取得。
            weather = weatherJSON.getString("description");

            // Mainオブジェクトを取得
            JSONObject mainJSON = rootJSON.getJSONObject("main");

            // 最高気温と最低気温を取得（ケルビン）
            double tempMaxKelvin = mainJSON.getDouble("temp_max");
            double tempMinKelvin = mainJSON.getDouble("temp_min");

            // ケルビンから摂氏に変換
            double tempMaxCelsius = tempMaxKelvin - 273.15;
            double tempMinCelsius = tempMinKelvin - 273.15;

            // 文字列に変換
            maxTemp = String.format("%.1f", tempMaxCelsius);
            minTemp = String.format("%.1f", tempMinCelsius);

            // Mainオブジェクトから湿度を取得
            double humidityValue = mainJSON.getDouble("humidity");
            humidity = humidityValue + "%";


        } catch (JSONException ex) {
            Log.e(DEBUG_TAG, "JSON解析失敗", ex);
        }

        // 画面に表示する「〇〇の天気」文字列を生成。
        String telop = cityName;
        // 天気の詳細情報を表示する文字列を生成。
        String desc = weather;
        String desc2 = maxTemp + "℃";
        String desc3 = minTemp + "℃";


        // 天気情報を表示するTextViewを取得。
        TextView tvWeatherTelop = findViewById(R.id.tvWeatherTelop);
        TextView tvWeatherDesc = findViewById(R.id.tvWeatherDesc);
        TextView tvWeatherDesc2 = findViewById(R.id.tvWeatherDesc2);
        TextView tvWeatherDesc3 = findViewById(R.id.tvWeatherDesc3);
        TextView tvWeatherHumidity = findViewById(R.id.tvWeatherDesc7);
        // 天気情報を表示。
        tvWeatherTelop.setText(telop);
        tvWeatherTelop.setTextSize(24);

        tvWeatherDesc.setText(desc);
        tvWeatherDesc.setTextSize(24);

        tvWeatherDesc2.setText(desc2);
        tvWeatherDesc2.setTextSize(24);

        tvWeatherDesc3.setText(desc3);
        tvWeatherDesc3.setTextSize(24);

        // 湿度情報を表示。
        tvWeatherHumidity.setText(humidity);
        tvWeatherHumidity.setTextSize(24);
    }



    /**
     * 非同期でお天気情報APIにアクセスするためのクラス。
     */
    private class WeatherInfoBackgroundReceiver implements Callable<String> {
        /**
         * お天気情報を取得するURL。
         */
        private final String _urlFull;

        /**
         * コンストラクタ。
         * 非同期でお天気情報Web APIにアクセスするのに必要な情報を取得する。
         *
         * @param urlFull お天気情報を取得するURL。
         */
        public WeatherInfoBackgroundReceiver(String urlFull) {
            _urlFull = urlFull;
        }

        @WorkerThread
        @Override
        public String call() {
            // 天気情報サービスから取得したJSON文字列。天気情報が格納されている。
            String result = "";
            // HTTP接続を行うHttpURLConnectionオブジェクトを宣言。finallyで解放するためにtry外で宣言。
            HttpURLConnection con = null;
            // HTTP接続のレスポンスデータとして取得するInputStreamオブジェクトを宣言。同じくtry外で宣言。
            InputStream is = null;
            try {
                // URLオブジェクトを生成。
                URL url = new URL(_urlFull);
                // URLオブジェクトからHttpURLConnectionオブジェクトを取得。
                con = (HttpURLConnection) url.openConnection();
                // 接続に使ってもよい時間を設定。
                con.setConnectTimeout(1000);
                // データ取得に使ってもよい時間。
                con.setReadTimeout(1000);
                // HTTP接続メソッドをGETに設定。
                con.setRequestMethod("GET");
                // 接続。
                con.connect();
                // HttpURLConnectionオブジェクトからレスポンスデータを取得。
                is = con.getInputStream();
                // レスポンスデータであるInputStreamオブジェクトを文字列に変換。
                result = is2String(is);
            }
            catch(MalformedURLException ex) {
                Log.e(DEBUG_TAG, "URL変換失敗", ex);
            }
            // タイムアウトの場合の例外処理。
            catch(SocketTimeoutException ex) {
                Log.w(DEBUG_TAG, "通信タイムアウト", ex);
            }
            catch(IOException ex) {
                Log.e(DEBUG_TAG, "通信失敗", ex);
                // 失敗時にエラー内容をログに出力
                ex.printStackTrace();
            }
            finally {
                // HttpURLConnectionオブジェクトがnullでないなら解放。
                if(con != null) {
                    con.disconnect();
                }
                // InputStreamオブジェクトがnullでないなら解放。
                if(is != null) {
                    try {
                        is.close();
                    }
                    catch(IOException ex) {
                        Log.e(DEBUG_TAG, "InputStream解放失敗", ex);
                    }
                }
            }
            return result;
        }

        /**
         * InputStreamオブジェクトを文字列に変換するメソッド。 変換文字コードはUTF-8。
         *
         * @param is 変換対象のInputStreamオブジェクト。
         * @return 変換された文字列。
         * @throws IOException 変換に失敗した時に発生。
         */
        private String is2String(InputStream is) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();
            char[] b = new char[1024];
            int line;
            while(0 <= (line = reader.read(b))) {
                sb.append(b, 0, line);
            }
            return sb.toString();
        }
    }

}
