package amqo.com.comics.views.list.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;
import java.util.Map;

import amqo.com.comics.ComicsApplication;
import amqo.com.comics.R;
import amqo.com.comics.model.Comic;
import amqo.com.comics.model.contracts.ComicsContract;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicListItemView {

    @BindView(R.id.title)
    protected TextView mTitle;
    @BindView(R.id.image)
    protected ImageView mImage;
    @BindView(R.id.image_container)
    protected View mImageContainer;

    private int mImageWidth = 0;
    private int mImageHeight = 0;

    private Map<ImageView, View.OnLayoutChangeListener>
            mCurrentLayoutListeners = new HashMap<>();

    private View mParentView;

    ComicsContract.ListView mListView;

    public ComicListItemView(View view, ComicsContract.ListView listView) {

        mParentView = view;
        mListView = listView;

        ButterKnife.bind(this, view);
    }

    public Comic mItem;

    public void bindViewHolder(Comic comic) {

        mItem = comic;

        mTitle.setText(comic.getTitle());

        mParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView.onComicInteraction(mItem);
            }
        });

        loadImageForComic();
    }

    private void loadImageForComic() {

        String imageUrl = mListView.convertImageUrl(mItem.getComicThumbnail());

        setHolderImageLayout();

        DrawableRequestBuilder builder = Glide.with(ComicsApplication.getInstance())
                .load(imageUrl)
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(getDrawableRequestListener());

//        builder.placeholder(R.drawable.comic_placeholder);

        builder.into(mImage);
    }

    private void setHolderImageLayout() {
        boolean auto = mImageWidth == 0 || mImageHeight == 0;
        if (auto) {
            // Add layout change listener only if a previous listener didn't fire before
            View.OnLayoutChangeListener layoutChangeListener = getOnLayoutChangeListener();

            mCurrentLayoutListeners.put(mImage, layoutChangeListener);
            mImage.addOnLayoutChangeListener(layoutChangeListener);
        } else {
            // If we got the width and height of images, we force the layout to avoid "jumps"
            ViewGroup.LayoutParams params = mImage.getLayoutParams();
            params.height = mImageHeight;
            params.width = mImageWidth;
            mImage.setLayoutParams(params);
        }
    }

    private View.OnLayoutChangeListener getOnLayoutChangeListener() {
        return new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int width, int height,
                                       int i4, int i5, int i6, int i7) {

                if (width > 0 && height > 0) {
                    float ratio = mListView.getImageRatio();
                    if (ratio < 1.60 && ratio > 1.40) {
                        mImageWidth = width;
                        mImageHeight = height;

                        // Ass we already have what we wanted, we can remove all listeners
                        for (ImageView imageView : mCurrentLayoutListeners.keySet()) {
                            imageView.removeOnLayoutChangeListener(mCurrentLayoutListeners.get(imageView));
                        }
                        mCurrentLayoutListeners.clear();
                    }
                }
            }
        };
    }

    private RequestListener<String, GlideDrawable> getDrawableRequestListener() {

        return new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(
                    Exception e, String model, Target<GlideDrawable> target,
                    boolean isFirstResource) {

                mImageContainer.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(
                    GlideDrawable resource, String model,
                    Target<GlideDrawable> target, boolean isFromMemoryCache,
                    boolean isFirstResource) {

                mImageContainer.setVisibility(View.VISIBLE);
                return false;
            }
        };
    }
}
