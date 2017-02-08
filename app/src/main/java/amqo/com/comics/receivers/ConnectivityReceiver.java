package amqo.com.comics.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import amqo.com.comics.ComicsApplication;
import amqo.com.comics.injection.BaseComicsComponent;

public class ConnectivityReceiver extends BroadcastReceiver {

    @Inject ConnectivityNotifier mConnectivityNotifier;

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        BaseComicsComponent baseComicsComponent =
                ComicsApplication.getInstance().getActiveComicComponent();

        if (baseComicsComponent != null) {

            baseComicsComponent.inject(this);
            mConnectivityNotifier.notifyConnectivityView(false);
        }
    }
}
