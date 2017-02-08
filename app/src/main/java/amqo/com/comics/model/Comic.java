package amqo.com.comics.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Comic {

    @SerializedName("id")
    private int mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("thumbnail")
    private ComicImage mThumbnail;

    @SerializedName("images")
    private List<ComicImage> mImages;

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public ComicImage getComicThumbnail() {
        return mThumbnail;
    }

    public List<ComicImage> getComicImages() {
        return mImages;
    }
}
