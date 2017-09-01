package mohz.m13.wicompanion;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private EditText commandText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_command, container, false);

        commandText = (EditText) rootView.findViewById(R.id.commandText);

        Button sendButton = (Button) rootView.findViewById(R.id.sendCommandButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmdText = commandText.getText().toString();
                if (!cmdText.equals("")) {
                    new SendText().execute(AppConstants.ipForConnection, cmdText);
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
                    Toast.makeText(getActivity().getApplicationContext(), "Your Device Don't Support Speech Input", Toast.LENGTH_LONG).show();
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
                    commandText.setText(result.get(0));

                    new SendText().execute(AppConstants.ipForConnection, result.get(0));
                }
                break;
        }
    }

    private class SendText extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                DatagramSocket ds = new DatagramSocket();
                byte[] cmdBytes = params[1].getBytes();
                String ip = params[0];

                ds.send(new DatagramPacket(cmdBytes, cmdBytes.length, InetAddress.getByName(ip),1316));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
