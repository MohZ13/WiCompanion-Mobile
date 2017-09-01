package mohz.m13.wicompanion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Mohil Zalavadiya on 27-08-2017.
 */

public class MouseFragment extends Fragment {
    Button leftButton, middleButton,rightButton;
    float prevX = 0, prevY = 0;
    DatagramSocket coordSocket, clickSocket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mouse, container, false);

        leftButton = (Button) rootView.findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendClick().execute(AppConstants.ipForConnection, "left");
            }
        });

        middleButton = (Button) rootView.findViewById(R.id.middleButton);
        middleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendClick().execute(AppConstants.ipForConnection, "middle");
            }
        });

        rightButton = (Button) rootView.findViewById(R.id.rightButton);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendClick().execute(AppConstants.ipForConnection, "right");
            }
        });

        try {
            coordSocket = new DatagramSocket();
            clickSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float curX = event.getX();
                float curY = event.getY();
                String x = String.valueOf(curX - prevX);
                String y = String.valueOf(curY - prevY);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        new SendText().execute(x + "," + y);
                        prevX = curX;
                        prevY = curY;
                        break;
                    case MotionEvent.ACTION_DOWN:
                        prevX = event.getX();
                        prevY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                }
                return true;
            }
        });

        return rootView;
    }

    private class SendText extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                byte[] cmdBytes = params[0].getBytes();
                String ip = AppConstants.ipForConnection;
                coordSocket.send(new DatagramPacket(cmdBytes, cmdBytes.length, InetAddress.getByName(ip),1313));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class SendClick extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                byte[] cmdBytes = params[1].getBytes();
                String ip = params[0];

                clickSocket.send(new DatagramPacket(cmdBytes, cmdBytes.length, InetAddress.getByName(ip),1314));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
