package amqo.com.comics.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Comics {

    @SerializedName("code")
    private int mResultCode;

    @SerializedName("status")
    private String mResultStatus;

    @SerializedName("data")
    private ComicsData mComicsData;

    public List<Comic> getComics() {

        return mComicsData == null ? new ArrayList<Comic>()
                : mComicsData.getComics();
    }

    public int getCurrentOffset() {

        return mComicsData == null ? 0
                : mComicsData.getCurrentOffset();
    }

    public boolean isInLastPage() {

        return mComicsData == null ? false
                : mComicsData.isInLastPage();
    }

    public boolean isResultOk() {
        return mResultCode == 200 && mResultStatus.equalsIgnoreCase("OK");
    }
}
