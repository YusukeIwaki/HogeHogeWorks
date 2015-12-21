package jp.co.crowdworks.hogehogeworks;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;
import com.nifty.cloud.mb.core.NCMBUser;

import java.util.List;

public class HogeHogeActivity extends AppCompatActivity {

    private static final String MESSAGE_STORE = "message";
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(mAdapter);

        setupComposer();
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
            loadMessageList();
        }
    }

    private void loadMessageList(){

        NCMBQuery<NCMBObject> query = new NCMBQuery<>(MESSAGE_STORE);
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> list, NCMBException e) {
                if(e!=null) {
                    Log.e("HogeHogeWorks","error",e);
                    return;
                }

                mAdapter.clear();

                for (NCMBObject obj : list) {
                    String username = obj.getString("username");
                    String content = obj.getString("content");

                    mAdapter.add(username+": "+content);
                }
            }
        });
    }

    private void setupComposer() {
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = getTextString(R.id.txt_content);
                if(TextUtils.isEmpty(content)) return;

                sendMessage(content);
            }
        });
    }

    private String getTextString(@IdRes int txt) {
        return ((TextView) findViewById(txt)).getText().toString();
    }

    private void sendMessage(String content) {
        NCMBUser user = NCMBUser.getCurrentUser();

        // TODO: すごくてきとう
        if(user==null || TextUtils.isEmpty(user.getUserName())) return;

        NCMBObject obj = new NCMBObject(MESSAGE_STORE);
        obj.put("username", user.getUserName());
        obj.put("content", content);
        obj.saveInBackground(new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                if(e!=null) {
                    Log.e("HogeHogeWorks","error",e);
                    return;
                }

                loadMessageList();
                ((TextView) findViewById(R.id.txt_content)).setText("");
            }
        });
    }
}
