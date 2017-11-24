package bag.small.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import bag.small.R;
import bag.small.interfaze.IDialog;
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

    private List items;
    private MultiTypeAdapter multiTypeAdapter;

    public NoticeDialogSnap(@NonNull Context context) {
        super(context, R.style.myNoFrame_Dialog);
        iDialog = (IDialog) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notice_snap_layout);

        ButterKnife.bind(this);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.85); // 宽度设置为屏幕的0.8
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(false);
        items = new Items(5);
        multiTypeAdapter = new MultiTypeAdapter(items);
    }




    @OnClick(R.id.notice_snap_close_tv)
    public void onViewClicked() {
        dismiss();
    }
}
