package mohz.m13.wicompanion;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mohil Zalavadiya on 27-08-2017.
 */

public class CommandFragment extends Fragment {
    private AutoCompleteTextView commandText;
    private TextView prevCmdText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_command, container, false);

        commandText = (AutoCompleteTextView) rootView.findViewById(R.id.commandText);
        prevCmdText = (TextView) rootView.findViewById(R.id.prevCmdText);

        String[] commandsArray = getResources().getStringArray(R.array.commandsArray);
        ArrayAdapter<String> cmdAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, commandsArray);
        commandText.setAdapter(cmdAdapter);
        commandText.setThreshold(1);

        Button sendButton = (Button) rootView.findViewById(R.id.sendCommandButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmdText = commandText.getText().toString();
                if (!cmdText.equals("")) {
                    new SendText().execute(cmdText);
                }
            }
        });

        ImageButton voiceButton = (ImageButton) rootView.findViewById(R.id.voiceImageButton);
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, 10);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Your Device Doesn't Support Speech Input", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    new SendText().execute(result.get(0));
                }
                break;
        }
    }

    private class SendText extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                DatagramSocket ds = new DatagramSocket();
                byte[] cmdBytes = params[0].getBytes();
                String ip = AppConstants.ipForConnection;

                ds.send(new DatagramPacket(cmdBytes, cmdBytes.length, InetAddress.getByName(ip),1316));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return params[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            commandText.setText("");
            prevCmdText.setText(s);
        }
    }
}
