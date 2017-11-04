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
