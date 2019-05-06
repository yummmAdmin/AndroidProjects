package com.digitaldestino.fcmClass;
import android.util.Log;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.Constants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FireIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("fcm_token",refreshedToken);
        // sendRegistrationToServer(refreshedToken);
        BMSPrefs.putString(getApplicationContext(), Constants.DEVICE_TOKEN, refreshedToken);
    }
}
