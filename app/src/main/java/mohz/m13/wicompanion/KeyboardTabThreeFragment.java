package mohz.m13.wicompanion;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mohil Zalavadiya on 16-09-2017.
 */

public class KeyboardTabThreeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab3_keyboard, container, false);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem(v.getId());
            }
        };

        rootView.findViewById(R.id.brightnessDownButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.brightnessUpButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.volDownButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.volUpButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.offMonitorButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.onMonitorButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.lockButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.sleepButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.shutDownButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.restartButton).setOnClickListener(clickListener);

        return rootView;
    }

    private void clickItem(int vId) {
        switch (vId) {
            case R.id.brightnessUpButton:
                new SendButtonInfo().execute("brightnessUp");
                break;
            case R.id.brightnessDownButton:
                new SendButtonInfo().execute("brightnessDown");
                break;

            case R.id.volUpButton:
                new SendButtonInfo().execute("volumeUp");
                break;
            case R.id.volDownButton:
                new SendButtonInfo().execute("volumeDown");
                break;

            case R.id.offMonitorButton:
                new SendButtonInfo().execute("offMonitor");
                break;
            case R.id.onMonitorButton:
                new SendButtonInfo().execute("onMonitor");
                break;

            case R.id.lockButton:
            case R.id.sleepButton:
            case R.id.shutDownButton:
            case R.id.restartButton:
                confirmOperation(vId);
                break;
        }
    }

    private void confirmOperation(final int vId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Confirm")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (vId) {
                            case R.id.lockButton:
                                new SendButtonInfo().execute("lockComputer");
                                break;
                            case R.id.sleepButton:
                                new SendButtonInfo().execute("sleepComputer");
                                break;
                            case R.id.shutDownButton:
                                new SendButtonInfo().execute("shutDownComputer");
                                break;
                            case R.id.restartButton:
                                new SendButtonInfo().execute("restartComputer");
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }
}
