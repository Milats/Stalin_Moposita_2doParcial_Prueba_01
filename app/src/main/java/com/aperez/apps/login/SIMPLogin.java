package com.aperez.apps.login;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aperez.apps.androidfunwithflags.R;
import com.aperez.apps.data.DatabaseDescription.Contact;

import java.util.Objects;

public class SIMPLogin extends AppCompatActivity {
    private EditText SIMPuser;
    private EditText SIMPpasswd;
    private FrameLayout SIMPfL;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simp_login_layout);

        SIMPpasswd = (EditText) findViewById(R.id.SIMPpaswwdEditText);
        SIMPuser = (EditText) findViewById(R.id.SIMPuserEditText);
        SIMPfL = (FrameLayout) findViewById(R.id.frameLyout);

    }

    public void onClicLogin(View view) {

    }

    public void onClicRegister(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contact.COLUMN_NAME,
                SIMPuser.getText().toString());
        contentValues.put(Contact.COLUMN_PASSWD,
                SIMPpasswd.getText().toString());
        if(addingNewContact){
            Uri newContactUri = getContentResolver().insert(Contact.CONTENT_URI, contentValues);
            if(newContactUri != null){
                Snackbar.make(SIMPfL, R.string.contact_added, Snackbar.LENGTH_LONG).show();
                listener.onAddEditCompleted(newContactUri);
            } else {
                Snackbar.make(coordinatorLayout, R.string.contact_not_added, Snackbar.LENGTH_LONG).show();
            }
        } else {
            int updateRows = getActivity().getContentResolver().update(contactUri, contentValues, null, null);
            if(updateRows > 0){
                listener.onAddEditCompleted(contactUri);
                Snackbar.make(coordinatorLayout, R.string.contact_updated, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(coordinatorLayout, R.string.contact_not_updated, Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
