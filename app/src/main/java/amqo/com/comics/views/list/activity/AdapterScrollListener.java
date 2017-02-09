package amqo.com.comics.views.list.activity;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import javax.inject.Inject;

import amqo.com.comics.ComicsApplication;
import amqo.com.comics.R;
import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.model.contracts.ComicsFabUp;

public class AdapterScrollListener extends RecyclerView.OnScrollListener {

    protected final ComicsContract.ListView mComicsView;
    protected final ComicsContract.ListPresenter mComicsPresenter;
    protected final int THRESHOLD;

    private final int TIMER_DELAY = 800;

    private Handler mShowFabHandler = new Handler();
    private Runnable mShowFabRunnable;

    private boolean mScrollingUp = false;

    private ComicsFabUp mComicsFabUpView;

    @Inject
    public AdapterScrollListener(
            ComicsApplication context,
            ComicsContract.View comicsView,
            ComicsContract.Presenter comicsPresenter) {

        THRESHOLD = context.getResources().getInteger(R.integer.grid_columns) * 2;

        mComicsView = (ComicsContract.ListView) comicsView;
        mComicsPresenter = (ComicsContract.ListPresenter) comicsPresenter;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (mComicsView.isLoading()) return;

        RecyclerView.LayoutManager layoutManager = mComicsView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        int pastVisibleItems = getPastVisibleItems(layoutManager);

        if (pastVisibleItems + visibleItemCount >= totalItemCount - THRESHOLD) {
            mComicsPresenter.loadMoreComics();
        }

        onScrolledFabUp(dy, pastVisibleItems);
    }

    private void onScrolledFabUp(int dy, int pastVisibleItems) {
        if (pastVisibleItems <= THRESHOLD) {
            mShowFabHandler.removeCallbacks(mShowFabRunnable);
            mComicsFabUpView.setShownUpFAB(false);
        } else manageFABVisibility(dy, pastVisibleItems);
    }

    public void setFabUpView(ComicsFabUp comicsFabUpView) {
        mComicsFabUpView = comicsFabUpView;
    }
    private void manageFABVisibility(int dy, final int pastItems) {

        boolean wasScrollingUp = mScrollingUp;
        if (dy != 0) mScrollingUp = dy < 0;

        // If it was a change in scrolling direction, then cancel it,
        // to avoid showing and hiding FAB too frecuently
        if (mShowFabRunnable != null && wasScrollingUp != mScrollingUp) {
            mShowFabHandler.removeCallbacks(mShowFabRunnable);
        }

        // If FAB is visible and is scrolling down,
        // or reach a top item position, make FAB gone automatically
        if ((!mScrollingUp || pastItems < THRESHOLD + 1) &&
                mComicsFabUpView.isUpFABVisible()) {
            mComicsFabUpView.setShownUpFAB(false);
        }

        // If no change in scroll direction, keep FAB visibility
        if (wasScrollingUp == mScrollingUp) return;

        mShowFabRunnable = new Runnable() {
            @Override
            public void run() {
                // If scrolling up and FAB is not already visible, and not in top item position,
                // then make FAB visible, unless a change in scroll direction occurs before timer delay
                if (mScrollingUp && !mComicsFabUpView.isUpFABVisible() && pastItems > THRESHOLD) {
                    mComicsFabUpView.setShownUpFAB(true);
                }
            }
        };
        mShowFabHandler.postDelayed(mShowFabRunnable, TIMER_DELAY);
    }


    private int getPastVisibleItems(RecyclerView.LayoutManager layoutManager) {

        int pastVisibleItems = 0;

        if (layoutManager instanceof LinearLayoutManager)
            pastVisibleItems = ((LinearLayoutManager)layoutManager)
                    .findFirstVisibleItemPosition();

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] visiblePositions = new int[2];
            ((StaggeredGridLayoutManager)layoutManager)
                    .findFirstVisibleItemPositions(visiblePositions);
            pastVisibleItems = visiblePositions[0];
        }

        if (layoutManager instanceof GridLayoutManager)
            pastVisibleItems = ((GridLayoutManager)layoutManager).findFirstVisibleItemPosition();

        return pastVisibleItems;
    }
}
