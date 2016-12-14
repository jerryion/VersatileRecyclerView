package com.crazypumpkin.versatilerecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.crazypumpkin.versatilerecyclerview.citypicker.CityPicker;

public class MainActivity extends AppCompatActivity {

    private CityPicker mCityPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wheelview:
                if (mCityPicker == null) {
                    mCityPicker = new CityPicker(this, findViewById(R.id.rl_container))
                            .setOnCitySelectListener(new CityPicker.OnCitySelectListener() {
                                @Override
                                public void onCitySelect(String province, String city, String county) {
                                    Toast.makeText(MainActivity.this, province + city + county, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                mCityPicker.show();
                break;
        }
    }
}
