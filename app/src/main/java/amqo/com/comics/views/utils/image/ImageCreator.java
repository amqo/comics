package amqo.com.comics.views.utils.image;

import android.app.Activity;
import android.support.annotation.VisibleForTesting;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import amqo.com.comics.model.ComicImage;

public abstract class ImageCreator {

    protected Activity mActivity;

    public abstract String convertImageUrl(ComicImage comicImage);

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    protected int getScreenDensity() {

        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return (int)metrics.xdpi;
    }
}
