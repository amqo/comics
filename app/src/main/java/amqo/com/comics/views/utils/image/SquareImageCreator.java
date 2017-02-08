package amqo.com.comics.views.utils.image;

import android.app.Activity;

import amqo.com.comics.model.ComicImage;

public class SquareImageCreator extends ImageCreator {

    public SquareImageCreator(Activity activity) {
        mActivity = activity;
    }

    @Override
    public String convertImageUrl(ComicImage comicImage) {
        String imageUrl = comicImage.getPath();
        imageUrl += "/standard_" + getImageSize() + "." + comicImage.getExtension();
        return imageUrl;
    }

    private String getImageSize() {

        int screenDensity = getScreenDensity();

        if (screenDensity < 160) return "small";
        if (screenDensity < 240) return "medium";
        if (screenDensity < 320) return "large";
        if (screenDensity < 480) return "xlarge";
        if (screenDensity < 640) return "fantastic";
        return "amazing";
    }
}
