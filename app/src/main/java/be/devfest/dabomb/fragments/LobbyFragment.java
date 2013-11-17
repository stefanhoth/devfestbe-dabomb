package be.devfest.dabomb.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unclouded.android.Network;
import com.unclouded.android.NetworkListener;
import com.unclouded.android.Promise;
import com.unclouded.android.PromiseListener;
import com.unclouded.android.RemoteReference;
import com.unclouded.android.ServiceListener;
import com.unclouded.android.TypeTag;
import com.unclouded.android.Unclouded;

import java.net.InetAddress;
import java.util.ArrayList;

import be.devfest.dabomb.R;
import be.devfest.dabomb.game.Game;
import be.devfest.dabomb.helpers.Constants;
import butterknife.InjectView;
import butterknife.Views;

/**
 * TODO describe class
 *
 * @author Stefan Hoth <sh@jnamic.com>
 *         date: 17.11.13 13:48
 * @since TODO add version
 */
public class LobbyFragment extends Fragment {

    private static final String LOG_TAG = "Lobby";

    private static final TypeTag GAME_TAG = new TypeTag(Constants.GAME_TAG);
    private TypeTag mSelectedGameTag;

    private Unclouded mUnclouded;
    private Network mNetwork;

    private RemoteReference mGameMaster;
    private ArrayList<RemoteReference> mGameClients;

    private boolean mIsOnline = false;
    private String mGameTag;

    private int mGameMode;

    private Game mGame;

    @InjectView(R.id.list_lobby)
    ListView mList;

    private ArrayAdapter<String> mAdapter;

    private Handler mHandler = new Handler();

    public static LobbyFragment newInstance(int gameMode) {
        LobbyFragment frag = new LobbyFragment(gameMode);
        return frag;
    }

    public LobbyFragment() {
        mGameMode = Constants.GAME_MODE_CLIENT;
    }

    public LobbyFragment(int gameMode) {
        mGameMode = gameMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lobby, container, false);
        Views.inject(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        mList.setAdapter(mAdapter);

        // Obtain Unclouded event loop
        mUnclouded = Unclouded.getInstance();

        // Go online and connected to the network
        mNetwork = mUnclouded.goOnline();

        mNetwork.whenever(new NetworkListener() {
            @Override
            public void isOnline(InetAddress ip){

                if(!mIsOnline) {
                    if(mGameMode == Constants.GAME_MODE_CLIENT) {
                        setupClient();
                    } else {
                        setupMaster();
                    }
                }

                mIsOnline = true;
                Log.d(LOG_TAG, "I'm online " + ip.toString());
            }

            @Override
            public void isOffline(InetAddress ip){
                mIsOnline = false;
                mGameClients.clear();
                mGameMaster = null;
            }
        });

    }

    private void setupMaster() {
        Log.d(LOG_TAG, "setupMaster()");
        mGame = new Game("Testgame");

        // This makes a remote reference to this object to be spread across the network
        mUnclouded.broadcast(GAME_TAG, mGame);

        mUnclouded.whenever(mGame.getGameTypeTag(), new ServiceListener<RemoteReference>(){

            @Override
            public void isDiscovered(RemoteReference remoteReference) {
                // Client discovered....
                Log.d(LOG_TAG, "Discovered client");
            }
        });
    }

    private void setupClient() {
        Log.d(LOG_TAG, "setupClient()");
        mUnclouded.whenever(GAME_TAG, new ServiceListener<RemoteReference>(){

            @Override
            public void isDiscovered(RemoteReference remoteReference) {
                Log.d(LOG_TAG, "Discovered a game");
                // Game discovered
                Promise promise = remoteReference.asyncInvoke("getName");
                // Listen for the name to be returned
                promise.when(new PromiseListener<String>() {

                    @Override
                    public void isResolved(final String name) {
                        Log.d(LOG_TAG, "Discovered game..."+ name);

                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                mAdapter.add(name);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }

            @Override
            public void isDisconnected(RemoteReference value) {
                super.isDisconnected(value);
                Log.d(LOG_TAG, "Disconnected");
            }

            @Override
            public void isReconnected(RemoteReference value) {
                super.isReconnected(value);
                Log.d(LOG_TAG, "Reconnected");
            }
        });
    }
}