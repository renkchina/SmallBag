package bag.small.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import bag.small.R;
import bag.small.interfaze.IDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/24.
 */

public class BottomDialog extends Dialog {

    @BindView(R.id.dialog_sort_by_subject_txt)
    TextView dialogSortBySubjectTxt;
    @BindView(R.id.dialog_sort_by_time_txt)
    TextView dialogSortByTimeTxt;
    @BindView(R.id.dialog_cancel_sort_txt)
    TextView dialogCancelSortTxt;
    private IDialog iDialog;
    String[] texts;

    public BottomDialog(@NonNull Context context) {
        super(context, R.style.myNoFrame_Dialog);
        if (context instanceof IDialog)
            iDialog = (IDialog) context;
    }


    public void setText(String... text) {
        texts = text;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_bottem_layout);

        ButterKnife.bind(this);
        initView();
        // 设置宽度
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialogstyle);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

    }

    private void initView() {
        if (texts != null && texts.length > 1) {
            dialogSortBySubjectTxt.setText(texts[0]);
            dialogSortByTimeTxt.setText(texts[1]);
        }
    }

    @OnClick({R.id.dialog_sort_by_subject_txt, R.id.dialog_sort_by_time_txt, R.id.dialog_cancel_sort_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_sort_by_subject_txt://上
                iDialog.callBackMethod(null, null);
                break;
            case R.id.dialog_sort_by_time_txt://下
                iDialog.callBackMiddleMethod();
                break;
            case R.id.dialog_cancel_sort_txt:
                break;
        }
        dismiss();
    }


}
