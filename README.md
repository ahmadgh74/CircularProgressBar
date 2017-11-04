# custom circular progress
#### create a custom circular progress in android

![Alt text](https://zerotohero.ir/wp-content/uploads/2017/11/circularprogress.jpg "")

### activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="ir.zerotohero.ahmadghorbi.progressbar.MainActivity">

    <ir.zerotohero.ahmadghorbi.progressbar.ZHProgress
        android:id="@+id/zhProgress"
        android:layout_centerHorizontal="true"
        android:layout_width="200dp"
        android:layout_height="200dp" />

</RelativeLayout>
```
### ActivityMain.java
```java
package ir.zerotohero.ahmadghorbi.progressbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ZHProgress zhProgress;
    private boolean isTrue=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zhProgress = (ZHProgress) findViewById(R.id.zhProgress);
        zhProgress.setOnClickListener((v) -> {
            if (isTrue) {
                zhProgress.stopProgress();
                isTrue=false;
            }else {
                zhProgress.startProgress();
                isTrue=true;
            }
        });
        zhProgress.setCycleColor(Color.parseColor("#7B1FA2") );
        zhProgress.setBackColor(Color.parseColor("#BDBDBD"));
    }
}
```
