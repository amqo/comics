package amqo.com.comics.views.list.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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

        DrawableRequestBuilder builder = Glide.with(ComicsApplication.getInstance())
                .load(imageUrl)
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(getDrawableRequestListener());

//        builder.placeholder(R.drawable.comic_placeholder);

        builder.into(mImage);
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
