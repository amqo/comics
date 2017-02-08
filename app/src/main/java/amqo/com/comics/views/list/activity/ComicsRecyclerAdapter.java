package amqo.com.comics.views.list.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import amqo.com.comics.R;
import amqo.com.comics.model.Comic;
import amqo.com.comics.model.Comics;
import amqo.com.comics.model.contracts.ComicsAdapter;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.model.view.ComicsContent;

public class ComicsRecyclerAdapter
        extends RecyclerView.Adapter<ComicsRecyclerAdapter.ComicItemViewHolder>
        implements ComicsAdapter{

    private ComicsContent mComicsContent;
    private ComicsContract.View mComicsView;

    public ComicsRecyclerAdapter(
            ComicsContract.View comicsView,
            ComicsContent comicsContent) {

        mComicsView = comicsView;
        mComicsContent = comicsContent;
    }

    @Override
    public ComicItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comic_list_item, parent, false);
        return new ComicItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mComicsContent.size();
    }

    @Override
    public void onBindViewHolder(ComicItemViewHolder holder, int position) {

        Comic comic = mComicsContent.get(position);
        holder.mView.bindViewHolder(comic);
    }

    @Override
    public void refreshComics(Comics comics) {

        int previousSize = mComicsContent.size();
        mComicsContent.clear();
        if (previousSize > 0) notifyItemRangeRemoved(0, previousSize);
        if (comics == null || comics.getComics().isEmpty()) return;
        addComics(comics);
    }

    @Override
    public void addComics(Comics comics) {
        if (comics == null || comics.getComics().isEmpty()) return;
        int previousSize = mComicsContent.size();
        mComicsContent.addAll(comics);
        notifyItemRangeInserted(previousSize, mComicsContent.size());
    }

    protected class ComicItemViewHolder extends RecyclerView.ViewHolder {

        protected final ComicListItemView mView;

        public ComicItemViewHolder(View view) {
            super(view);
            mView = new ComicListItemView(view, (ComicsContract.ListView) mComicsView);
        }
    }
}
