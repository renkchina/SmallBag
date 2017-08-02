package bag.small.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.caimuhao.rxpicker.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.interfaze.IListDialog;
import bag.small.provider.DialogListViewBinder;
import bag.small.utils.ListUtil;
import bag.small.view.RecycleViewDivider;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/7/29.
 */

public class ListDialog implements IListDialog {

    private List<Object> items;
    private PopupWindow mPopupWindow;
    private MultiTypeAdapter multiTypeAdapter;
    private IListDialog iListDialog;


    public void setListDialog(IListDialog iListDialog) {
        this.iListDialog = iListDialog;
    }

    public ListDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list_popup, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dialog_popup_recycler);
        items = new ArrayList<>();
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.register(String.class, new DialogListViewBinder(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new RecycleViewDivider(context,
                LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(context, R.color.button_green)));
        recyclerView.setAdapter(multiTypeAdapter);

        mPopupWindow = new PopupWindow(view);
        mPopupWindow.setWidth(DensityUtil.dp2px(context, 200));
        mPopupWindow.setHeight(DensityUtil.dp2px(context, 250));

        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
    }

    public void setListData(List datas) {
        items.clear();
        if (ListUtil.unEmpty(datas)) {
            items.addAll(datas);
        }
        multiTypeAdapter.notifyDataSetChanged();
    }

    public void show(View view) {
        if (!mPopupWindow.isShowing() && items.size() > 0) {
            mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//            mPopupWindow.showAsDropDown(view, 0, 5);
        }
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    @Override
    public void callListener(int position, String content) {
        if (iListDialog != null) {
            iListDialog.callListener(position, content);
        }
        if (isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
