package be.devfest.dabomb;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by maui on 17.11.13.
 */
public class GameActivity extends Activity {

    @InjectView(R.id.imageView)
    ImageView mBomb;

    private AnimationDrawable mBombAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        Views.inject(this);

        mBombAnimation = (AnimationDrawable) mBomb.getDrawable();
        mBombAnimation.start();
    }

}
