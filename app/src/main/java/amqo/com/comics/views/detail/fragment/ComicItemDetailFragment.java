package amqo.com.comics.views.detail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import amqo.com.comics.R;
import amqo.com.comics.model.Comic;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.model.view.ComicsContent;
import amqo.com.comics.views.BaseComicsView;
import amqo.com.comics.views.detail.activity.ComicItemDetailActivity;
import amqo.com.comics.views.list.activity.ComicsListActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single ComicItem detail screen.
 * This fragment is either contained in a {@link ComicsListActivity}
 * in two-pane mode (on tablets) or a {@link ComicItemDetailActivity}
 * on handsets.
 */
public class ComicItemDetailFragment extends Fragment implements BaseComicsView {

    public static final String COMIC_ARG = "COMIC_ARG";

    @Inject protected ComicsContent mComicsContent;
    @Inject protected ComicsContract.View mComicView;

    @BindView(R.id.comic_item_detail)
    protected TextView mComicDetailText;

    private Comic mComic;
    private int mComicId;

    public ComicItemDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(COMIC_ARG))
            mComicId = savedInstanceState.getInt(COMIC_ARG);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mComic != null)
            outState.putInt(COMIC_ARG, mComic.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.comicitem_detail, container, false);

        ButterKnife.bind(this, rootView);

        if (mComicsContent == null) return rootView;
        if (getArguments().containsKey(COMIC_ARG) && mComicsContent != null)
            mComic = mComicsContent.getById(getArguments().getInt(COMIC_ARG));

        if (mComic == null && mComicId > 0) mComic = mComicsContent.getById(mComicId);

        if (mComic == null) return rootView;
        mComicView.onComicLoaded(mComic);

        mComicDetailText.setText(mComic.getDescription());

        return rootView;
    }

    @Override
    public void createComponent() {

    }
}
