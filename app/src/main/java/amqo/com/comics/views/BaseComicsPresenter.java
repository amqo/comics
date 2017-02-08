package amqo.com.comics.views;

import android.support.design.widget.Snackbar;

import amqo.com.comics.api.ComicsEndpoint;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.views.utils.NotificationsHelper;

public abstract class BaseComicsPresenter implements ComicsContract.Presenter {

    protected static final String CHARACTER_ID = "1009220";

    protected ComicsContract.View mComicsView;
    protected ComicsEndpoint mComicsEndpoint;

    protected boolean mNeedRefresh = false;
    protected Snackbar mConnectivitySnackbar;

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if (!isConnected) {
            mNeedRefresh = true;
            mConnectivitySnackbar = NotificationsHelper
                    .showSnackConnectivity(mComicsView);
        } else {
            if (mConnectivitySnackbar != null) {
                mConnectivitySnackbar.dismiss();
                mConnectivitySnackbar = null;
            }
            if (mNeedRefresh) {
                mNeedRefresh = false;
                refreshComics();
            }
        }
    }

    protected abstract void refreshComics();
}
