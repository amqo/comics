package amqo.com.comics.model.contracts;

import android.support.v7.widget.RecyclerView;

import amqo.com.comics.model.Comic;
import amqo.com.comics.model.ComicImage;
import amqo.com.comics.model.Comics;
import amqo.com.comics.model.view.ComicViewContext;

public interface ComicsContract {

    interface Presenter {

        void onNetworkConnectionChanged(boolean isConnected);
    }

    interface ListPresenter extends Presenter {

        void loadMoreComics();

        void refreshComics();
    }

    interface View {

        ComicViewContext getComicViewContext();

        String convertImageUrl(ComicImage comicImage);

        float getImageRatio();

        void onComicLoaded(Comic comic);
    }

    interface ListView extends View {

        void onComicsLoaded(Comics comics);

        void onComicInteraction(Comic comic);

        void clearComics();

        RecyclerView.LayoutManager getLayoutManager();

        void scrollUp();

        void setLoading(boolean loading);

        boolean isLoading();
    }
}
