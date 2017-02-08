package amqo.com.comics.views.utils.image;

import android.app.Activity;

import amqo.com.comics.views.utils.ScreenHelper;

public abstract class ImageUrlFactory {

    public static ImageCreator produceImageCreator(Activity activity, int ratio) {

        ImageCreator imageCreator = new SquareImageCreator(activity);

        switch (ratio) {
            case ScreenHelper.LANDSCAPE:
                imageCreator = new LandscapeImageCreator(activity);
                break;
            case ScreenHelper.PORTRAIT:
                imageCreator = new PortraitImageCreator(activity);
                break;
        }
        return imageCreator;
    }
}
