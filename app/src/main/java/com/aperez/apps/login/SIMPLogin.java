package com.aperez.apps.login;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aperez.apps.androidfunwithflags.MainActivity;
import com.aperez.apps.androidfunwithflags.R;
import com.aperez.apps.data.DatabaseContentProvider;
import com.aperez.apps.data.DatabaseDescription.Contact;

import java.util.Objects;

public class SIMPLogin extends Fragment
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
    private boolean addingNewContact = true;
    private Uri contactUri;
    private EditText SIMPuser;
    private EditText SIMPpasswd;
    private FrameLayout SIMPfL;
    private Button SIMPButtonRegister;
    private Button SIMPButtonLogin;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.simp_login_layout, container, false);
        SIMPuser = (EditText) view.findViewById(R.id.SIMPuserEditText);
        //SIMPuser.addTextChangedListener(nameChangedListener);
        SIMPpasswd = (EditText) view.findViewById(R.id.SIMPpaswwdEditText);
        SIMPButtonRegister = (Button) view.findViewById(R.id.SIMPloginButton);
        SIMPButtonRegister.setOnClickListener(saveContactButtonClicked);
        SIMPfL = (FrameLayout) getActivity().findViewById(R.id.frameLyout);

        return view;
    }
    private final View.OnClickListener saveContactButtonClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getView().getWindowToken(), 0);
            saveContact();
        }
    };

    public void saveContact() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contact.COLUMN_NAME,
                SIMPuser.getText().toString());
        contentValues.put(Contact.COLUMN_PASSWD,
                SIMPpasswd.getText().toString());

        Uri newContactUri = Objects.requireNonNull(getActivity(), "D")
                .getContentResolver().insert(Contact.CONTENT_URI, contentValues);
        if(newContactUri != null){
                Snackbar.make(SIMPfL, R.string.contact_added, Snackbar.LENGTH_LONG).show();
                listener.onAddEditCompleted(newContactUri);
            } else {
                Snackbar.make(SIMPfL, R.string.contact_not_added, Snackbar.LENGTH_LONG).show();
            }

        int updateRows = getActivity().getContentResolver().update(contactUri, contentValues, null, null);
            if(updateRows > 0){
                listener.onAddEditCompleted(contactUri);
                Snackbar.make(SIMPfL, R.string.contact_updated, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(SIMPfL, R.string.contact_not_updated, Snackbar.LENGTH_LONG).show();
            }

    }
}
