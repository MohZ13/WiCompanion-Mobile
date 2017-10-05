package mohz.m13.wicompanion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Mohil Zalavadiya on 27-08-2017.
 */

public class KeyboardTabOneFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1_keyboard, container, false);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton(v.getId());
            }
        };

        rootView.findViewById(R.id.startButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.stopButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.altabButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.tabButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.upButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.downButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.leftButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.rightButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.enterButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.pgupButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.pgdnButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.homeButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.endButton).setOnClickListener(clickListener);

        return rootView;
    }

    private void clickButton(int vId) {
        switch (vId) {
            case R.id.startButton:
                new SendButtonInfo().execute("win");
                break;
            case R.id.stopButton:
                new SendButtonInfo().execute("altf4");
                break;
            case R.id.tabButton:
                new SendButtonInfo().execute("tab");
                break;
            case R.id.altabButton:
                new SendButtonInfo().execute("alttab");
                break;
            case R.id.upButton:
                new SendButtonInfo().execute("up");
                break;
            case R.id.downButton:
                new SendButtonInfo().execute("down");
                break;
            case R.id.leftButton:
                new SendButtonInfo().execute("left");
                break;
            case R.id.rightButton:
                new SendButtonInfo().execute("right");
                break;
            case R.id.enterButton:
                new SendButtonInfo().execute("enter");
                break;
            case R.id.pgdnButton:
                new SendButtonInfo().execute("pgdn");
                break;
            case R.id.pgupButton:
                new SendButtonInfo().execute("pgup");
                break;
            case R.id.homeButton:
                new SendButtonInfo().execute("home");
                break;
            case R.id.endButton:
                new SendButtonInfo().execute("end");
                break;
        }
    }

    private class SendButtonInfo extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                DatagramSocket ds = new DatagramSocket();
                String ip = AppConstants.ipForConnection;
                byte[] cmdBytes = params[0].getBytes();

                ds.send(new DatagramPacket(cmdBytes, cmdBytes.length, InetAddress.getByName(ip), 1315));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

