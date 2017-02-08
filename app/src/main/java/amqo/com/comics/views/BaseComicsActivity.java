package amqo.com.comics.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import amqo.com.comics.ComicsApplication;
import amqo.com.comics.injection.BaseComicsComponent;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.receivers.ConnectivityNotifier;
import amqo.com.comics.views.detail.fragment.ComicItemDetailFragment;
import amqo.com.comics.views.utils.ScreenHelper;

public class BaseComicsActivity extends AppCompatActivity implements BaseComicsView {

    @Inject protected ScreenHelper mScreenHelper;
    @Inject protected ComicsContract.Presenter mComicsPresenter;
    @Inject protected ConnectivityNotifier mConnectivityNotifier;

    protected ComicItemDetailFragment mFragment;

    protected BaseComicsComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createComponent();
    }

    @Override
    public void createComponent() {
        ComicsApplication.getInstance().createComicsComponent(this);
        mComponent = ComicsApplication.getInstance().getActiveComicComponent();
        mComponent.inject(this);
    }
}
