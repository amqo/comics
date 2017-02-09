package amqo.com.comics.model.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import amqo.com.comics.model.Comic;
import amqo.com.comics.model.Comics;

public class ComicsContent {

    private final List<Comic> mComics = new ArrayList<>();

    private final Map<Integer, Comic> mComicsMap = new HashMap<>();

    public ComicsContent(Comics comics) {
        addAll(comics);
    }

    public void addComic(Comic comic) {
        mComics.add(comic);
        mComicsMap.put(comic.getId(), comic);
    }

    public int size() {
        return mComics.size();
    }

    public Comic get(int position) {
        return mComics.get(position);
    }

    public void clear() {
        mComics.clear();
        mComicsMap.clear();
    }

    public void addAll(Comics comics) {
        for (Comic comic : comics.getComics())
            addComic(comic);
    }

    public Comic getById(int id) {
        return mComicsMap.get(id);
    }
}
