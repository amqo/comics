package amqo.com.comics.model.contracts;

import amqo.com.comics.model.Comics;

public interface ComicsAdapter {

    void refreshComics(Comics comics);

    void addComics(Comics comics);

}
