package seccompat.com.samsung.android.game;

import seccompat.Reflection;
import seccompat.android.util.Log;

public class SemGameManager {
    private static final String TAG = "Attic_SemGameManager";
    private Object mSemGameManagerInstance;

    public SemGameManager() {
        try {
            this.mSemGameManagerInstance = Reflection.getConstructor("com.samsung.android.game.SemGameManager", new Class[0]).newInstance(new Object[0]);
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean isForegroundGame() {
        Object obj = this.mSemGameManagerInstance;
        Boolean bool = obj != null ? (Boolean) Reflection.callMethod(obj, "isForegroundGame", new Object[0]) : null;
        Log.d(TAG, "isForegroundGame() : " + bool);
        return bool;
    }
}
