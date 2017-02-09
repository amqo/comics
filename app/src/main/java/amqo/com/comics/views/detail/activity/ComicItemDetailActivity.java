package amqo.com.comics.views.detail.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import javax.inject.Inject;

import amqo.com.comics.ComicsApplication;
import amqo.com.comics.R;
import amqo.com.comics.injection.ComicDetailParentComponent;
import amqo.com.comics.injection.modules.ComicsDetailModule;
import amqo.com.comics.model.Comic;
import amqo.com.comics.model.ComicImage;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.model.view.ComicViewContext;
import amqo.com.comics.views.BaseComicsActivity;
import amqo.com.comics.views.detail.fragment.ComicItemDetailFragment;
import amqo.com.comics.views.list.activity.ComicsListActivity;
import amqo.com.comics.views.utils.ScreenHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * An activity representing a single ComicItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ComicsListActivity}.
 */
public class ComicItemDetailActivity
        extends BaseComicsActivity
        implements ComicsContract.View {

    @Inject ScreenHelper mScreenHelper;

    @BindView(R.id.detail_toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.app_bar)
    protected AppBarLayout mAppBarLayout;
    @Nullable @BindView(R.id.collapsing_image)
    protected ImageView mCollapsingImage;
    @Nullable @BindView(R.id.toolbar_layout)
    protected CollapsingToolbarLayout mCollapsingToolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comicitem_detail);

        mUnbinder = ButterKnife.bind(this);

        initActionBar();

        initFragment(savedInstanceState == null);

        boolean connected = mConnectivityNotifier.isConnected();
        mComicsPresenter.onNetworkConnectionChanged(connected);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ComicsContract.View methods

    @Override
    public ComicViewContext getComicViewContext() {

        ComicViewContext comicViewContext = new ComicViewContext();
        comicViewContext.context = this;
        comicViewContext.view = mFragment.getView();
        return comicViewContext;
    }

    @Override
    public String convertImageUrl(ComicImage comicImage) {
        return mScreenHelper.convertImageUrl(comicImage, ScreenHelper.PORTRAIT);
    }

    @Override
    public float getImageRatio() {
        return mScreenHelper.getImageRatio(ScreenHelper.PORTRAIT);
    }

    @Override
    public void onComicLoaded(Comic comic) {

        if (mCollapsingToolbar != null) {
            mCollapsingToolbar.setTitle(comic.getTitle());

            if (mCollapsingImage != null) {
                Glide.with(ComicsApplication.getInstance())
                        .load(mScreenHelper.convertImageUrl(
                                comic.getRandomImage(), ScreenHelper.LANDSCAPE))
                        .crossFade()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mCollapsingImage);
            }
        }
    }

    private void initActionBar() {

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (mCollapsingToolbar != null) {
            mCollapsingToolbar.setTitle("");

            if (!mScreenHelper.isPortrait()) {
                mAppBarLayout.setExpanded(false);
            }
        }
    }

    private void initFragment(boolean first) {

        if (first) {
            Bundle arguments = new Bundle();
            arguments.putInt(ComicItemDetailFragment.COMIC_ARG,
                    getIntent().getIntExtra(ComicItemDetailFragment.COMIC_ARG, 0));

            mFragment = new ComicItemDetailFragment();
            mFragment.setArguments(arguments);

            injectFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.comicitem_detail_container, mFragment)
                    .commit();
        } else {
            mFragment = (ComicItemDetailFragment)
                    getSupportFragmentManager().findFragmentById(R.id.comicitem_detail_container);
            injectFragment();
        }
    }

    private void injectFragment() {
        ((ComicDetailParentComponent)mComponent).getComicDetailComponent(
                new ComicsDetailModule()).inject(mFragment);
    }
}
