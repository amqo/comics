package amqo.com.comics.receivers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

import amqo.com.comics.ComicsApplication;
import amqo.com.comics.model.contracts.ComicsContract;

public class ConnectivityNotifier {

    private ComicsContract.Presenter mComicsPresenter;

    private static boolean isConnected = false;

    private static boolean needConnectivityInit = true;

    @Inject
    public ConnectivityNotifier(ComicsContract.Presenter comicsPresenter) {

        needConnectivityInit = true;
        this.mComicsPresenter = comicsPresenter;
        notifyConnectivityView(true);
    }

    public boolean isConnected() {

        if (needConnectivityInit) {
            needConnectivityInit = false;
            ConnectivityManager cm = (ConnectivityManager)
                    ComicsApplication.getInstance().getApplicationContext()
                            .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            isConnected = activeNetwork != null
                    && activeNetwork.isConnectedOrConnecting();
        }

        return isConnected;
    }

    public void notifyConnectivityView(boolean firstNotify) {

        needConnectivityInit = true;
        boolean isConnected = isConnected();

        if (firstNotify && isConnected) return;

        if (mComicsPresenter != null) {
            mComicsPresenter.onNetworkConnectionChanged(isConnected);
        }
    }
}
