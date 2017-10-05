package mohz.m13.wicompanion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Mohil Zalavadiya on 27-08-2017.
 */

public class ConnectFragment extends Fragment {
    AutoCompleteTextView ipText;
    NavigationView navigationView;
    View rootView;
    Context mContext;
    WiCompDBHandler appDB;

    public static ConnectFragment newInstance(Context mContext) {
        ConnectFragment connectFragment = new ConnectFragment();
        connectFragment.mContext = mContext;

        return connectFragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_connect, container, false);

        appDB = new WiCompDBHandler(mContext);

        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        ipText = (AutoCompleteTextView) rootView.findViewById(R.id.ipAddressText);

        if (AppConstants.ipForConnection != null) {
            ipText.setText(AppConstants.ipForConnection);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, appDB.getAllIPAddress());
        ipText.setAdapter(adapter);

        ipText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    new IPValidator().execute(ipText.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });

        Button connectButton = (Button) rootView.findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IPValidator().execute(ipText.getText().toString());
            }
        });

        return rootView;
    }

    private class IPValidator extends AsyncTask<String, Void, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(mContext, "Wait!","Validating IP address!");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean isValidIp = true;

            if (params[0].equals(""))   return false;
            try {
                InetAddress.getByName(params[0]);
            } catch (UnknownHostException e) {
                isValidIp = false;
            }
            return isValidIp;
        }

        @Override
        protected void onPostExecute(Boolean isValidIp) {
            super.onPostExecute(isValidIp);

            TextView navIpText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navIpText);
            progressDialog.dismiss();
            if (isValidIp) {
                String validIP = ipText.getText().toString();
                AppConstants.ipForConnection = validIP;

                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                Toast.makeText(mContext, "Connected!", Toast.LENGTH_SHORT).show();
                navIpText.setText(AppConstants.ipForConnection);
                if (appDB.isDistinctIPAddress(validIP)) {
                    appDB.addIPAddress(validIP);
                }
            } else {
                Toast.makeText(mContext, "Invalid IP address!", Toast.LENGTH_LONG).show();
                ipText.setText("");
                navIpText.setText(R.string.navigation_drawer_ip_negative_status);
            }
        }
    }
}
