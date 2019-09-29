package tech.march.submission1.activity.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.march.submission1.R;
import tech.march.submission1.alarm.DailyAlarmReceiver;
import tech.march.submission1.alarm.DailyReleaseAlarmReceiver;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Switch dailySwitch, releaseSwitch;
    DailyReleaseAlarmReceiver releaseAlarmReceiver = new DailyReleaseAlarmReceiver();
    DailyAlarmReceiver dailyAlarmReceiver = new DailyAlarmReceiver();
    SharedPreferences dailySp, releaseSp;

    private String keyDaily, keyRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        keyDaily = getResources().getString(R.string.key_daily);
        keyRelease = getResources().getString(R.string.key_release);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dailySwitch = findViewById(R.id.switch_daily);
        dailySwitch.setOnCheckedChangeListener(this);

        releaseSwitch = findViewById(R.id.switch_release);
        releaseSwitch.setOnCheckedChangeListener(this);

        dailySp = getSharedPreferences(keyDaily, MODE_PRIVATE);
        dailySwitch.setChecked(dailySp.getBoolean(keyDaily, false));

        releaseSp = getSharedPreferences(keyRelease, MODE_PRIVATE);
        releaseSwitch.setChecked(releaseSp.getBoolean(keyRelease, false));

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean flag) {
        switch (compoundButton.getId()) {
            case R.id.switch_release:
                if (flag) {
                    releaseAlarmReceiver.setAlarm(getApplicationContext());
                    SharedPreferences.Editor editor = getSharedPreferences(keyRelease, MODE_PRIVATE).edit();
                    editor.putBoolean(keyRelease, true);
                    editor.apply();
                } else {
                    releaseAlarmReceiver.cancelAlarm(getApplicationContext());
                    SharedPreferences.Editor editor = getSharedPreferences(keyRelease, MODE_PRIVATE).edit();
                    editor.remove(keyRelease);
                    editor.apply();
                }
                break;

            case R.id.switch_daily:
                if (flag) {
                    dailyAlarmReceiver.setAlarm(getApplicationContext());
                    SharedPreferences.Editor editor = getSharedPreferences(keyDaily, MODE_PRIVATE).edit();
                    editor.putBoolean(keyDaily, true);
                    editor.apply();
                } else {
                    dailyAlarmReceiver.cancelAlarm(getApplicationContext());
                    SharedPreferences.Editor editor = getSharedPreferences(keyDaily, MODE_PRIVATE).edit();
                    editor.remove(keyDaily);
                    editor.apply();
                }
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



