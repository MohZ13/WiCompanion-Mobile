package mohz.m13.wicompanion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mohil Zalavadiya on 15-09-2017.
 */

public class KeyboardTabTwoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2_keyboard, container, false);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton(v.getId());
            }
        };

        rootView.findViewById(R.id.explorerButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.desktopButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.searchButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.minimizeButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.taskViewButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.cortanaButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.runButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.scrShotButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.settingsButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.winUpButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.winDownButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.winLeftButton).setOnClickListener(clickListener);
        rootView.findViewById(R.id.winRightButton).setOnClickListener(clickListener);

        return rootView;
    }

    private void clickButton(int vId) {
        switch (vId) {
            case R.id.explorerButton:
                new SendButtonInfo().execute("explorer");
                break;
            case R.id.desktopButton:
                new SendButtonInfo().execute("desktop");
                break;
            case R.id.searchButton:
                new SendButtonInfo().execute("search");
                break;
            case R.id.minimizeButton:
                new SendButtonInfo().execute("minimize");
                break;
            case R.id.taskViewButton:
                new SendButtonInfo().execute("task_view");
                break;
            case R.id.cortanaButton:
                new SendButtonInfo().execute("cortana");
                break;
            case R.id.runButton:
                new SendButtonInfo().execute("run");
                break;
            case R.id.scrShotButton:
                new SendButtonInfo().execute("screenshot");
                break;
            case R.id.settingsButton:
                new SendButtonInfo().execute("settings");
                break;
            case R.id.winUpButton:
                new SendButtonInfo().execute("winUp");
                break;
            case R.id.winDownButton:
                new SendButtonInfo().execute("winDown");
                break;
            case R.id.winLeftButton:
                new SendButtonInfo().execute("winLeft");
                break;
            case R.id.winRightButton:
                new SendButtonInfo().execute("winRight");
                break;
        }
    }
}
