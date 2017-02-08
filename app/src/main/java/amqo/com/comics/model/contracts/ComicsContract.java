package amqo.com.comics.model.contracts;

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

        void setLoading(boolean loading);

        ComicViewContext getComicViewContext();

        String convertImageUrl(ComicImage comicImage);

        void createComponent();
    }

    interface ListView extends View {

        ComicViewContext getComicViewContext();

        void onComicsLoaded(Comics comics);

        void onComicInteraction(Comic comic);

        void clearComics();
    }

    interface DetailView extends View {

        void onComicLoaded(Comic comic);
    }
}
