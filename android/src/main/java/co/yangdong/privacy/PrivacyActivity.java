package co.yangdong.privacy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.BuildConfig;
import com.yl.lib.sentry.hook.PrivacySentry;

import co.yangdong.privacy.dialog.PrivacyDialog;
import co.yangdong.privacy.utils.SharedPrefUtils;


public class PrivacyActivity extends AppCompatActivity {
    public Class<?> mainActivityClass;

    public void setMainActivityClass() {}


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_layout);
        boolean firstInstall = SharedPrefUtils.getBooleanData(getApplicationContext(),"AGREE_PRIVACY");
        if (!firstInstall) {
            String privacyTitle = getResources().getString(R.string.privacyTitle);
            String privacyText = getResources().getString(R.string.privacyText);
            String onePolicyURL = getResources().getString(R.string.registerURL);
            String twoPolicyURL = getResources().getString(R.string.privacyURL);
            PrivacyDialog privacyDialog = new PrivacyDialog();
            privacyDialog.show(getSupportFragmentManager(), "PrivacyDialog");
            privacyDialog.setPrivacyTitle(privacyTitle);
            privacyDialog.setPrivacyText(privacyText);
            privacyDialog.setOnePolicyURL(onePolicyURL);
            privacyDialog.setTwoPolicyURL(twoPolicyURL);
            privacyDialog.setOnAgreeClickListener(() -> {
                SharedPrefUtils.saveData(getApplicationContext(), "AGREE_PRIVACY", true);
                privacyDialog.dismiss();
                onStartMainActivity();
            });
            privacyDialog.setOnRejectClickListener(this::stopApp);
        } else {
            onStartMainActivity();
        }
    }

    void onStartMainActivity() {
        setMainActivityClass();
        if (BuildConfig.DEBUG) {
            PrivacySentry.Privacy.INSTANCE.updatePrivacyShow();
        }
        Intent intent = new Intent(this, mainActivityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    void stopApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
