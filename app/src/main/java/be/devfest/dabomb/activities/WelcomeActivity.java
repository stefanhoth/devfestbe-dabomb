package be.devfest.dabomb.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import be.devfest.dabomb.R;
import be.devfest.dabomb.fragments.NavigationDrawerFragment;
import be.devfest.dabomb.fragments.WelcomeFragment;
import be.devfest.dabomb.helpers.Constants;

import static be.devfest.dabomb.helpers.Constants.KEY_GAME_MODE;

;

public class WelcomeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    private static final String TAG = WelcomeActivity.class.getName();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Intent start = new Intent(WelcomeActivity.this, GameActivity.class);
        startActivity(start);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new WelcomeFragment())
                .commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        Bundle extras = new Bundle();

        switch (view.getId()) {

            case R.id.btn_game_start:
                extras.putInt(KEY_GAME_MODE, Constants.GAME_MODE_MASTER);
                break;
            case R.id.btn_game_join:
                extras.putInt(KEY_GAME_MODE, Constants.GAME_MODE_CLIENT);
                break;
        }

        if (extras.isEmpty()) {
            Log.e(TAG, "Bundle has no settings. Can't start game.");
            return;
        }


    }
}
