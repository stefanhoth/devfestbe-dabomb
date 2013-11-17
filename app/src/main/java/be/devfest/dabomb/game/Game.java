package be.devfest.dabomb.game;

import com.unclouded.android.TypeTag;
import com.unclouded.android.UObject;

import java.util.UUID;

/**
 * Created by maui on 17.11.13.
 */
public class Game implements UObject {

    private String mName;
    private boolean mCanJoin = true;
    private boolean mStarted = false;
    private TypeTag mGameTag;

    public Game(String name) {
        mName = name;
        mGameTag = new TypeTag("dabomb-"+ UUID.randomUUID().toString());
    }

    public String getName() {
        return mName;
    }

    public TypeTag getGameTypeTag() {
        return mGameTag;
    }

    public boolean canJoin() {
        return mCanJoin;
    }

    public boolean started() {
        return mStarted;
    }
}
