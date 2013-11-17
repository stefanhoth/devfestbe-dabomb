package be.devfest.dabomb.fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import be.devfest.dabomb.R;
import be.devfest.dabomb.activities.WelcomeActivity;
import be.devfest.dabomb.helpers.Constants;

/**
 * TODO describe class
 *
 * @author Stefan Hoth <sh@jnamic.com>
 *         date: 17.11.13 12:52
 * @since TODO add version
 */
public class SessionFragment extends android.app.Fragment implements View.OnClickListener {

    private static final String TAG = WelcomeActivity.class.getName();
    ViewFlipper mFlipper;


    public static SessionFragment newInstance() {

        SessionFragment instance = new SessionFragment();

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_session, null);

        try {
            rootView.findViewById(R.id.btn_answer).setOnClickListener(this);
            rootView.findViewById(R.id.bananaphone).setOnClickListener(this);

            ((AnimationDrawable) ((ImageView) rootView.findViewById(R.id.bananaphone)).getDrawable()).start();
            ((AnimationDrawable) ((ImageView) rootView.findViewById(R.id.bomb)).getDrawable()).start();
            mFlipper = (ViewFlipper) rootView.findViewById(R.id.session_viewflipper);
            mFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_down_in));
            mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_up_out));
        } catch (NullPointerException e) {
            Log.e(TAG, "Could not set up buttons", e);
        }

        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mFlipper != null && savedInstanceState != null) {
            int flipperPosition = savedInstanceState.getInt(Constants.KEY_SESSION_ACTIVE_FLIP);
            mFlipper.setDisplayedChild(flipperPosition);
        }
    }

    @Override
    public void onClick(View view) {

        Bundle extras = new Bundle();

        switch (view.getId()) {

            case R.id.btn_answer:
            case R.id.bananaphone:
                mFlipper.showNext();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        int position = mFlipper.getDisplayedChild();
        savedInstanceState.putInt(Constants.KEY_SESSION_ACTIVE_FLIP, position);
    }
}
