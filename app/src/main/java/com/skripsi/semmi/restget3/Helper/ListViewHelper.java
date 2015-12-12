package com.skripsi.semmi.restget3.Helper;

import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.ListView;

/**
 * Created by semmi on 12/12/2015.
 */
public class ListViewHelper {

    public void googleCardslistViewDesign(Resources resources ,ListView listView ){
        listView.setClipToPadding(false);
        listView.setDivider(null);
        Resources r = resources;
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);
        listView.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        listView.setPadding(px, px, px, px);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
    }
}
