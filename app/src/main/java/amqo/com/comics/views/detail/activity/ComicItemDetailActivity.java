package amqo.com.comics.views.detail.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import javax.inject.Inject;

import amqo.com.comics.R;
import amqo.com.comics.injection.ComicDetailParentComponent;
import amqo.com.comics.injection.modules.ComicsDetailModule;
import amqo.com.comics.model.Comic;
import amqo.com.comics.model.ComicImage;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.model.view.ComicViewContext;
import amqo.com.comics.model.view.ComicsContent;
import amqo.com.comics.views.BaseComicsActivity;
import amqo.com.comics.views.detail.fragment.ComicItemDetailFragment;
import amqo.com.comics.views.list.activity.ComicsListActivity;
import amqo.com.comics.views.utils.ScreenHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static amqo.com.comics.R.id.fab;

/**
 * An activity representing a single ComicItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ComicsListActivity}.
 */
public class ComicItemDetailActivity
        extends BaseComicsActivity
        implements ComicsContract.DetailView {

    @Inject ComicsContent mComicsContent;

    @BindView(fab)
    protected FloatingActionButton mFab;
    @BindView(R.id.detail_toolbar)
    protected Toolbar mToolbar;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comicitem_detail);

        mUnbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putInt(ComicItemDetailFragment.COMIC_ARG,
                    getIntent().getIntExtra(ComicItemDetailFragment.COMIC_ARG, 0));

            ComicItemDetailFragment fragment = new ComicItemDetailFragment();
            fragment.setArguments(arguments);

            ((ComicDetailParentComponent)mComponent).getComicDetailComponent(
                    new ComicsDetailModule(mComicsContent)).inject(fragment);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.comicitem_detail_container, fragment)
                    .commit();
        }

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
            navigateUpTo(new Intent(this, ComicsListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setLoading(boolean loading) {

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
        return mScreenHelper.convertImageUrl(comicImage, ScreenHelper.PORTRAIT);
    }

    @Override
    public void onComicLoaded(Comic comic) {

    }
}
