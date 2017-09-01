package mohz.m13.wicompanion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Mohil Zalavadiya on 27-08-2017.
 */

public class FreqUsedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_freq_used, container, false);

        Button startButton = (Button) rootView.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendButtonInfo().execute("win");
            }
        });

        Button stopButton = (Button) rootView.findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendButtonInfo().execute("altf4");
            }
        });

        Button altabButton = (Button) rootView.findViewById(R.id.altabButton);
        altabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendButtonInfo().execute("alttab");
            }
        });

        Button tabButton = (Button) rootView.findViewById(R.id.tabButton);
        tabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendButtonInfo().execute("tab");
            }
        });

        Button upButton = (Button) rootView.findViewById(R.id.upButton);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendButtonInfo().execute("up");
            }
        });

        Button downButton = (Button) rootView.findViewById(R.id.downButton);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendButtonInfo().execute("down");
            }
        });

        Button leftButton = (Button) rootView.findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendButtonInfo().execute("left");
            }
        });

        Button rightButton = (Button) rootView.findViewById(R.id.rightButton);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendButtonInfo().execute("right");
            }
        });

        Button enterButton = (Button) rootView.findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendButtonInfo().execute("enter");
            }
        });

        return rootView;
    }

    private class SendButtonInfo extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                DatagramSocket ds = new DatagramSocket();
                String ip = AppConstants.ipForConnection;
                byte[] cmdBytes = params[0].getBytes();

                ds.send(new DatagramPacket(cmdBytes, cmdBytes.length, InetAddress.getByName(ip),1315));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
