package bag.small.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import com.caimuhao.rxpicker.utils.DensityUtil;
import java.util.ArrayList;
import java.util.List;
import bag.small.R;
import bag.small.entity.ImageString;
import bag.small.interfaze.IListDialog;
import bag.small.provider.AdvertisingImageViewBinder;
import bag.small.provider.AdvertisingTxtViewBinder;
import bag.small.utils.ListUtil;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/11/2.
 */

public class AdvertisingDialog implements IListDialog {

    private List<Object> items;
    private PopupWindow mPopupWindow;
    private MultiTypeAdapter multiTypeAdapter;
    private IListDialog iListDialog;

    public void setListDialog(IListDialog iListDialog) {
        this.iListDialog = iListDialog;
    }

    public AdvertisingDialog(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_advertising_layout, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dialog_advertising_recycler_list);
        View close  = view.findViewById(R.id.dialog_advertising_close_view);
        items = new ArrayList<>();
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.register(String.class, new AdvertisingTxtViewBinder());
        multiTypeAdapter.register(ImageString.class, new AdvertisingImageViewBinder());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(multiTypeAdapter);

        mPopupWindow = new PopupWindow(view);
        mPopupWindow.setWidth(DensityUtil.dp2px(context, 300));
        mPopupWindow.setHeight(DensityUtil.dp2px(context, 350));

        close.setOnClickListener(v -> {
            if (isShowing()) {
                mPopupWindow.dismiss();
            }
        });

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
            mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, -20);
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
