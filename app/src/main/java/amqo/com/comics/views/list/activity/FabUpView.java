package amqo.com.comics.views.list.activity;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import javax.inject.Inject;

import amqo.com.comics.model.contracts.ComicsContract;
import amqo.com.comics.model.contracts.ComicsFabUp;

public class FabUpView implements ComicsFabUp {

    private FloatingActionButton mUpFAB;
    private ComicsContract.ListView mComicsView;

    @Inject
    public FabUpView(ComicsContract.View comicsView) {
        mComicsView = (ComicsContract.ListView) comicsView;
    }

    //  ComicsFabUp.View methods

    @Override
    public boolean isUpFABVisible() {
        return mUpFAB == null ? false : mUpFAB.isShown();
    }

    @Override
    public void setShownUpFAB(final boolean show) {
        if (mUpFAB == null) return;
        if (show) mUpFAB.show();
        else mUpFAB.hide();
    }

    @Override
    public void setUpFab(FloatingActionButton upFab) {
        mUpFAB = upFab;
        initUpFab();
    }

    private void initUpFab() {
        mUpFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUpFAB.hide();
                mComicsView.scrollUp();
            }
        });
    }
}
