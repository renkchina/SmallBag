package bag.small.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import bag.small.R;
import bag.small.interfaze.IDialog;
import bag.small.provider.DialogSnapViewBinder;
import bag.small.utils.ListUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/11/24.
 */

public class NoticeDialogSnap extends Dialog {

    @BindView(R.id.notice_snap_title_tv)
    TextView noticeSnapTitleTv;
    @BindView(R.id.notice_snap_content_tv)
    TextView noticeSnapContentTv;
    @BindView(R.id.notice_snap_recycler)
    RecyclerView noticeSnapRecycler;
    @BindView(R.id.notice_snap_close_tv)
    TextView noticeSnapCloseTv;
    private IDialog iDialog;
    private Context context;
    private List items;
    private MultiTypeAdapter multiTypeAdapter;
    private String title;
    private String content;

    public NoticeDialogSnap(@NonNull Context context) {
        super(context, R.style.myNoFrame_Dialog);
        this.context = context;
        if (context instanceof IDialog) {
            iDialog = (IDialog) context;
        }
    }

    public void setiDialog(IDialog iDialog) {
        this.iDialog = iDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notice_snap_layout);
        ButterKnife.bind(this);

        initView();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.85); // 宽度设置为屏幕的0.8
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(false);

    }

    private void initView() {
        items = new Items(5);
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.register(String.class, new DialogSnapViewBinder());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        noticeSnapRecycler.setLayoutManager(linearLayoutManager);
        noticeSnapRecycler.setAdapter(multiTypeAdapter);
        noticeSnapTitleTv.setText(title);
        noticeSnapContentTv.setText(content);
    }

    public void setShowContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setShowContent(String title, Spanned content) {
        noticeSnapTitleTv.setText(title);
        noticeSnapContentTv.setText(content);
    }

    public void setList(List list) {
        items.clear();
        if (ListUtil.unEmpty(list)) {
            items.addAll(list);
            multiTypeAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.notice_snap_close_tv)
    public void onViewClicked() {
        dismiss();
    }
}
