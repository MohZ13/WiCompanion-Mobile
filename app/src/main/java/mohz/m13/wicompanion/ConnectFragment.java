package mohz.m13.wicompanion;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Mohil Zalavadiya on 27-08-2017.
 */

public class ConnectFragment extends Fragment {
    EditText ipText;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_connect, container, false);

        ipText = (EditText) rootView.findViewById(R.id.ipAddressText);

        if (AppConstants.ipForConnection != null) {
            ipText.setText(AppConstants.ipForConnection);
            Toast.makeText(AppConstants.mContext, "Already connected...", Toast.LENGTH_LONG).show();
        }

        Button connectButton = (Button) rootView.findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipString = ipText.getText().toString();

                new IPValidator().execute(ipString);
            }
        });

        return rootView;
    }

    private class IPValidator extends AsyncTask<String, Void, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(AppConstants.mContext, "Wait!","Validating IP address!");
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

            TextView navIpText = (TextView) AppConstants.navigationView.findViewById(R.id.navIpText);
            progressDialog.dismiss();
            if (isValidIp) {
                AppConstants.ipForConnection = ipText.getText().toString();
                Toast.makeText(AppConstants.mContext, "IP address stored...", Toast.LENGTH_SHORT).show();

                navIpText.setText(AppConstants.ipForConnection);
            } else {
                Toast.makeText(AppConstants.mContext, "Invalid IP address!", Toast.LENGTH_LONG).show();
                ipText.setText("");
                navIpText.setText("Not connected");
            }
        }
    }
}
