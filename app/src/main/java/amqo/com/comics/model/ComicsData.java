package amqo.com.comics.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComicsData {

    @SerializedName("offset")
    private int mOffset;

    @SerializedName("limit")
    private int mLimit;

    @SerializedName("total")
    private int mTotal;

    @SerializedName("count")
    private int mCount;

    @SerializedName("results")
    private List<Comic> mComics;

    public List<Comic> getComics() {
        return mComics;
    }

    public int getCurrentOffset() {
        return mOffset;
    }

    public boolean isInLastPage() {
        return mOffset + mCount == mTotal;
    }

    public int getCount() {
        return mCount;
    }
}
