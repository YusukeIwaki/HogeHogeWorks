package jp.co.crowdworks.hogehogeworks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.nifty.cloud.mb.core.NCMBUser;

public class HogeHogeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: すごくてきとう
        NCMBUser user = NCMBUser.getCurrentUser();
        if (user==null || TextUtils.isEmpty(user.getUserName())) {
            new UserLoginDialogFragment().show(getSupportFragmentManager(),"login");
        }
        else {
            new UserLogoutDialogFragment().show(getSupportFragmentManager(), "logout");
        }
    }
}
