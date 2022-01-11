package com.aperez.apps.login;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aperez.apps.androidfunwithflags.MainActivity;
import com.aperez.apps.androidfunwithflags.R;
import com.aperez.apps.data.DatabaseContentProvider;
import com.aperez.apps.data.DatabaseDescription.Contact;
import com.aperez.apps.data.DatabaseHelper;

import java.util.Objects;

public class SIMPLogin extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private static final int CONTACT_LOADER = 0;
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
        DatabaseHelper SIMPdbHelper = new DatabaseHelper(this, "AddressBook.db", null, 1);
        SQLiteDatabase SIMPsql = SIMPdbHelper.getReadableDatabase();

        String SIMPusername = SIMPuser.getText().toString();
        String SIMPpasswdd = SIMPpasswd.getText().toString();
        String SIMPconsulta = "SELECT name " +
                "FROM contacts " +
                "WHERE name = '" + SIMPusername + "'" + " AND passwd = '" + SIMPpasswdd + "'";

        Cursor cursor = SIMPsql.rawQuery(SIMPconsulta, null);

        if (cursor.moveToFirst()){
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            Intent SIMPmainGame = new Intent(this, MainActivity.class);
            startActivity(SIMPmainGame);
        } else {
            Toast.makeText(this, "Usuario y/o contraseñas incorrectos", Toast.LENGTH_SHORT).show();
        }
        SIMPsql.close();
    }

    public void onClicRegister(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contact.COLUMN_NAME,
                SIMPuser.getText().toString());
        contentValues.put(Contact.COLUMN_PASSWD,
                SIMPpasswd.getText().toString());

        Uri newContactUri = getContentResolver().insert(Contact.CONTENT_URI, contentValues);
        if(newContactUri != null){
                Snackbar.make(SIMPfL, R.string.contact_added, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(SIMPfL, R.string.contact_not_added, Snackbar.LENGTH_LONG).show();
            }
    }
}