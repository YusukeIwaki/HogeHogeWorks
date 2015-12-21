package jp.co.crowdworks.hogehogeworks;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.nifty.cloud.mb.core.LoginCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBUser;

public class UserLoginDialogFragment extends DialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setView(R.layout.input_id_pass)
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = getTextString(R.id.txt_username);
                        String password = getTextString(R.id.txt_password);

                        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) return;

                        try {
                            NCMBUser.loginInBackground(username, password, new LoginCallback() {
                                @Override
                                public void done(NCMBUser ncmbUser, NCMBException e) {
                                    if (e!=null) {
                                        Log.e("HogeHogeWorks", "error", e);
                                    }

                                }
                            });
                        } catch (NCMBException e) {
                            Log.e("HogeHogeWorks", "error", e);
                        }
                    }
                })
                .setNeutralButton("会員登録", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new UserRegistrationDialogFragment().show(getFragmentManager(), "register");
                    }
                })
                .create();
    }

    private String getTextString(@IdRes int txt) {
        return ((TextView) getDialog().findViewById(txt)).getText().toString();
    }
}
