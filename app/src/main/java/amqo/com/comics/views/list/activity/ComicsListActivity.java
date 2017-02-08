package amqo.com.comics.views.list.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import amqo.com.comics.R;
import amqo.com.comics.model.Comic;
import amqo.com.comics.model.ComicImage;
import amqo.com.comics.model.Comics;
import amqo.com.comics.model.contracts.ComicsAdapter;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.model.view.ComicViewContext;
import amqo.com.comics.views.BaseComicsActivity;
import amqo.com.comics.views.detail.activity.ComicItemDetailActivity;
import amqo.com.comics.views.detail.fragment.ComicItemDetailFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * An activity representing a list of ComicItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ComicItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ComicsListActivity extends BaseComicsActivity implements ComicsContract.ListView {

    @Inject protected ComicsAdapter mComicsAdapter;

    @BindView(R.id.fab)
    protected FloatingActionButton mFab;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.comic_item_list)
    protected RecyclerView mRecyclerView;
    @Nullable
    @BindView(R.id.comicitem_detail_container)
    protected View mItemDetailContainer;

    private Unbinder mUnbinder;

    protected boolean mIsRefreshing = false;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comicitem_list);

        mUnbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());

        setupRecyclerView();

        if (mItemDetailContainer != null) {
            mTwoPane = true;
        }

        boolean connected = mConnectivityNotifier.isConnected();
        if (connected) ((ComicsContract.ListPresenter)mComicsPresenter)
                .refreshComics();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onComicsLoaded(Comics comics) {

        if (mIsRefreshing) {
            mIsRefreshing = false;
            mComicsAdapter.refreshComics(comics);
        } else mComicsAdapter.addComics(comics);
    }

    @Override
    public void setLoading(boolean loading) {

    }

    @Override
    public void clearComics() {
        mComicsAdapter.refreshComics(new Comics());
    }

    @Override
    public void onComicInteraction(Comic comic) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(ComicItemDetailFragment.COMIC_ARG, comic.getId());
            ComicItemDetailFragment fragment = new ComicItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.comicitem_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ComicItemDetailActivity.class);
            intent.putExtra(ComicItemDetailFragment.COMIC_ARG, comic.getId());

            startActivity(intent);
        }
    }

    @Override
    public ComicViewContext getComicViewContext() {
        ComicViewContext comicViewContext = new ComicViewContext();
        comicViewContext.context = this;
        comicViewContext.view = mFab;
        return comicViewContext;
    }

    @Override
    public String convertImageUrl(ComicImage comicImage) {
        return mScreenHelper.convertImageUrl(comicImage);
    }

    private void setupRecyclerView() {
        mRecyclerView.setAdapter((RecyclerView.Adapter) mComicsAdapter);
    }
}
