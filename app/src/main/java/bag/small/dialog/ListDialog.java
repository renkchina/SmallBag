package bag.small.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.provider.DialogListViewBinder;
import bag.small.utils.ListUtil;
import bag.small.view.RecycleViewDivider;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/7/29.
 */

public class ListDialog {

    private List<Object> items;
    private PopupWindow mPopupWindow;
    private Context context;
    private RecyclerView recyclerView;
    private MultiTypeAdapter multiTypeAdapter;

    public ListDialog(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list_popup, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.dialog_popup_recycler);
        items = new ArrayList<>();
        multiTypeAdapter = new MultiTypeAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        multiTypeAdapter.register(String.class, new DialogListViewBinder());
        recyclerView.addItemDecoration(new RecycleViewDivider(context,
                LinearLayoutManager.HORIZONTAL, 2,
                ContextCompat.getColor(context, R.color.button_green)));
        recyclerView.setAdapter(multiTypeAdapter);

        mPopupWindow = new PopupWindow(view);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));

    }

    public void setListData(List datas){
        if(ListUtil.unEmpty(datas)){
            items.clear();
            items.addAll(datas);
            multiTypeAdapter.notifyDataSetChanged();
        }
    }

    public void show(View view) {
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(view, 0, 5);
        }
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }
}
