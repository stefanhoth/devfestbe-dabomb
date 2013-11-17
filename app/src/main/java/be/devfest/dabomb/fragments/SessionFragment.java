package be.devfest.dabomb.fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;
import java.util.Random;

import be.devfest.dabomb.R;
import be.devfest.dabomb.data.QuizReader;
import be.devfest.dabomb.entities.Question;
import be.devfest.dabomb.helpers.Constants;

/**
 * TODO describe class
 *
 * @author Stefan Hoth <sh@jnamic.com>
 *         date: 17.11.13 12:52
 * @since TODO add version
 */
public class SessionFragment extends android.app.Fragment implements View.OnClickListener {

    private static final String TAG = SessionFragment.class.getName();
    ViewFlipper mFlipper;
    int flipperPosition = -1;

    private List<Question> questions;
    private Question currentQuestion;

    public static SessionFragment newInstance() {

        SessionFragment instance = new SessionFragment();

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_session, null);

        try {
            rootView.findViewById(R.id.btn_answer_0).setOnClickListener(this);
            rootView.findViewById(R.id.btn_answer_1).setOnClickListener(this);
            rootView.findViewById(R.id.btn_answer_2).setOnClickListener(this);
            rootView.findViewById(R.id.btn_answer_3).setOnClickListener(this);
            rootView.findViewById(R.id.bananaphone).setOnClickListener(this);

            ((AnimationDrawable) ((ImageView) rootView.findViewById(R.id.bananaphone)).getDrawable()).start();
            ((AnimationDrawable) ((ImageView) rootView.findViewById(R.id.bomb)).getDrawable()).start();
            mFlipper = (ViewFlipper) rootView.findViewById(R.id.session_viewflipper);
            mFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_down_in));
            mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_up_out));

            ((TextView) rootView.findViewById(R.id.session_question)).setText(currentQuestion.question);

            ((Button) rootView.findViewById(R.id.btn_answer_0)).setText(currentQuestion.options.get(0));
            ((Button) rootView.findViewById(R.id.btn_answer_1)).setText(currentQuestion.options.get(1));
            ((Button) rootView.findViewById(R.id.btn_answer_2)).setText(currentQuestion.options.get(2));
            ((Button) rootView.findViewById(R.id.btn_answer_3)).setText(currentQuestion.options.get(3));

            if (flipperPosition != -1) {
                mFlipper.setDisplayedChild(flipperPosition);
            }


        } catch (NullPointerException e) {
            Log.e(TAG, "Could not set up buttons", e);
        }

        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            flipperPosition = savedInstanceState.getInt(Constants.KEY_SESSION_ACTIVE_FLIP);
        }

        questions = QuizReader.load(getActivity());
        currentQuestion = questions.get(new Random().nextInt(questions.size()));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.bananaphone:
                mFlipper.showNext();
                break;
            case R.id.btn_answer_0:
                checkAnswer(0);
                break;
            case R.id.btn_answer_1:
                checkAnswer(1);
                break;
            case R.id.btn_answer_2:
                checkAnswer(2);
                break;
            case R.id.btn_answer_3:
                checkAnswer(3);
                break;
        }
    }

    private void checkAnswer(int i) {

        if (currentQuestion.answerId == i) {
            getView().findViewById(R.id.screen_player_active).setBackgroundColor(getActivity().getResources().getColor(android.R.color.holo_green_light));
            mFlipper.showNext();
        } else {
            getView().findViewById(R.id.screen_player_active).setBackgroundColor(getActivity().getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        int position = mFlipper.getDisplayedChild();
        savedInstanceState.putInt(Constants.KEY_SESSION_ACTIVE_FLIP, position);
    }
}
