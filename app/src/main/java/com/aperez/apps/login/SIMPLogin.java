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

    public interface SIMPLoginListener{
        void onAddEditCompleted(Uri contactUri);
    }
    private static final int CONTACT_LOADER = 0;
    private SIMPLoginListener listener;
    private Uri contactUri;
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
        DatabaseHelper dbHelper = new DatabaseHelper(this, "AddressBook.db", null, 1);
        //Open DB only to read
        SQLiteDatabase sql = dbHelper.getReadableDatabase();

        String user = SIMPuser.getText().toString();
        String passwd = SIMPpasswd.getText().toString();

        //String consulta = "SELECT * FROM Clientes";
        //String consulta = "SELECT Codigo, Nombre, Apellido, Correo FROM Clientes ORDER BY Codigo";
        //Indices
        //Importante de dejar espacios entre FROM CLIENTES WHERE ETC...
        String consulta = "SELECT name " +
                "FROM contacts " +
                "WHERE name = '" + user + "'" + " AND passwd = '" + passwd + "'";

        Cursor cursor = sql.rawQuery(consulta, null);
        //Name cicle

        if (cursor.moveToFirst()){
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            Intent mainGame = new Intent(this, MainActivity.class);
            startActivity(mainGame);
        } else {
            Toast.makeText(this, "Usuario y/o contraseñas incorrectos", Toast.LENGTH_SHORT).show();
        }
        sql.close();
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