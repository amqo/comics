package amqo.com.comics.injection;

import amqo.com.comics.receivers.ConnectivityReceiver;
import amqo.com.comics.views.BaseComicsView;

public interface BaseComicsComponent<T extends BaseComicsView> {

    void inject(ConnectivityReceiver connectivityReceiver);
    void inject(T childComicsView);
}
