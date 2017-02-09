package amqo.com.comics.views.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import amqo.com.comics.R;

public abstract class LayoutHelper {

    public static RecyclerView.LayoutManager getCustomLayoutManager(Context context) {
        int gridColumns = context.getResources().getInteger(R.integer.grid_columns);

        RecyclerView.LayoutManager layoutManager;
        if (gridColumns <= 1) {
            layoutManager = new LinearLayoutManager(context);
            return layoutManager;
        }

        boolean isPortrait = context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;

        if (isPortrait) {
            layoutManager = new StaggeredGridLayoutManager(
                    StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS,
                    StaggeredGridLayoutManager.VERTICAL);
        } else {
            layoutManager = new GridLayoutManager(context, gridColumns);
        }
        return layoutManager;
    }
}
