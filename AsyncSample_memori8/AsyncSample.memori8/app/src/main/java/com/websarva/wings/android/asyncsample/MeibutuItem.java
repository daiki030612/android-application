package com.websarva.wings.android.asyncsample;

public class MeibutuItem {
    private String name;
    private String description;

    // 3つの引数を受け付けるコンストラクタ
    public MeibutuItem(String name, String description, String url) {
        this.name = name;
        this.description = description;
        // urlの処理も行う場合はここで行う
    }

    // 2つの引数を受け付けるコンストラクタ
    public MeibutuItem(String name, String description) {
        this.name = name;
        this.description = description;
        // urlの初期化などが必要な場合はここで行う
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

