package amqo.com.comics.views.detail.activity;

import amqo.com.comics.api.ComicsEndpoint;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.views.BaseComicsPresenter;

public class ComicDetailPresenter extends BaseComicsPresenter {

    public ComicDetailPresenter(
            ComicsEndpoint comicsEndpoint,
            ComicsContract.View comicsView) {

        mComicsView = comicsView;
        mComicsEndpoint = comicsEndpoint;
    }

    @Override
    protected void refreshComics() {

    }
}
