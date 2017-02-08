package amqo.com.comics.model;

import com.google.gson.annotations.SerializedName;

public class ComicImage {

    @SerializedName("path")
    private String mPath;

    @SerializedName("extension")
    private String mExtension;

    public String getPath() {
        return mPath;
    }

    public String getExtension() {
        return mExtension;
    }
}
