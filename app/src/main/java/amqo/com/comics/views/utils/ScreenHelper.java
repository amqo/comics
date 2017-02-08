package amqo.com.comics.views.utils;

import android.app.Activity;
import android.content.res.Configuration;

import javax.inject.Inject;

import amqo.com.comics.model.ComicImage;
import amqo.com.comics.views.utils.image.ImageCreator;
import amqo.com.comics.views.utils.image.ImageUrlFactory;

public class ScreenHelper {

    public static final int PORTRAIT = 0;
    public static final int SQUARE = 1;
    public static final int LANDSCAPE = 2;

    private Activity mActivity;

    @Inject
    public ScreenHelper(Activity activity) {
        mActivity = activity;
    }

    public String convertImageUrl(ComicImage comicImage) {

        String imageUrl = "";

        int orientation = getScreenOrientation();
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                imageUrl = convertImageUrl(comicImage, PORTRAIT);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                imageUrl = convertImageUrl(comicImage, LANDSCAPE);
                break;
        }
        return imageUrl;
    }

    public String convertImageUrl(ComicImage comicImage, int ratio) {
        ImageCreator imageCreator = ImageUrlFactory.produceImageCreator(mActivity, ratio);
        return imageCreator.convertImageUrl(comicImage);
    }

    public boolean isPortrait() {
        return getScreenOrientation() == Configuration.ORIENTATION_PORTRAIT;
    }


    protected int getScreenOrientation() {
        return mActivity.getResources().getConfiguration().orientation;
    }

}
