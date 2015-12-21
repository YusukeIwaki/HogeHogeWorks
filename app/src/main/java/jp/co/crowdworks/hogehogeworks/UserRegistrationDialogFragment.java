package jp.co.crowdworks.hogehogeworks;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBUser;

public class UserRegistrationDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setView(R.layout.input_id_pass)
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = getTextString(R.id.txt_username);
                        String password = getTextString(R.id.txt_password);

                        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) return;

                        NCMBUser user = new NCMBUser();
                        user.setUserName(username);
                        user.setPassword(password);
                        user.signUpInBackground(new DoneCallback() {
                            @Override
                            public void done(NCMBException e) {
                                if (e!=null) {
                                    Toast.makeText(getContext(),"signup error!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                .create();
    }

    private String getTextString(@IdRes int txt) {
        return ((TextView) getDialog().findViewById(txt)).getText().toString();
    }
}
