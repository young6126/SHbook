package com.example.shbook;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NetworkStatus {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean getConnectivityStatus(Context context){ //해당 context의 서비스를 사용하기위해서 context객체를 받는다.
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        return manager.isDefaultNetworkActive();
    }
}