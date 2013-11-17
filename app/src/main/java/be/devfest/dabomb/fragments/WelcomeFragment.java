package be.devfest.dabomb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.devfest.dabomb.R;
import be.devfest.dabomb.activities.LobbyActivity;
import be.devfest.dabomb.helpers.Constants;

/**
 * TODO describe class
 *
 * @author Stefan Hoth <sh@jnamic.com>
 *         date: 17.11.13 12:52
 * @since TODO add version
 */
public class WelcomeFragment extends android.app.Fragment implements View.OnClickListener {

    private static final String TAG = WelcomeFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_welcome, null);

        try {
            rootView.findViewById(R.id.btn_game_start).setOnClickListener(this);
            rootView.findViewById(R.id.btn_game_join).setOnClickListener(this);
        } catch (NullPointerException e) {
            Log.e(TAG, "Could not set up buttons", e);
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {

        Bundle extras = new Bundle();

        switch (view.getId()) {

            case R.id.btn_game_start:
                extras.putInt(Constants.KEY_GAME_MODE, Constants.GAME_MODE_MASTER);
                break;
            case R.id.btn_game_join:
                extras.putInt(Constants.KEY_GAME_MODE, Constants.GAME_MODE_CLIENT);
                break;
        }

        if (extras.isEmpty()) {
            Log.e(TAG, "Bundle has no settings. Can't start game.");
            return;
        }

        Intent start = new Intent(getActivity(), LobbyActivity.class);
        start.putExtras(extras);
        startActivity(start);
    }
}
