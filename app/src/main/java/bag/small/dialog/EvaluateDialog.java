package bag.small.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import bag.small.R;
import bag.small.interfaze.IDialog;
import bag.small.utils.LogUtil;
import bag.small.utils.StringUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/9.
 */

public class EvaluateDialog extends Dialog {

    @BindView(R.id.dialog_fragment_user_note_evaluation_edt)
    EditText dEvaluationEdt;
    @BindView(R.id.dialog_fragment_user_note_evluation_btn)
    Button dEvaluationBtn;
    @BindView(R.id.dialog_fragment_user_note_evaluation_ll)
    LinearLayout dEvaluationLl;
    private IDialog iDialog;
    private Context mContext;
    private String hint = "";

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setiDialog(IDialog iDialog) {
        this.iDialog = iDialog;
    }

    public EvaluateDialog(@NonNull Context context) {
        super(context, R.style.myNoFrame_Dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.show("onCreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_send_evluate_ll);
        ButterKnife.bind(this);
        // 设置宽度
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        // 弹出键盘
        dEvaluationEdt.setFocusable(true);
        dEvaluationEdt.setFocusableInTouchMode(true);
        dEvaluationEdt.requestFocus();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(hint)) {
            dEvaluationEdt.setHint(hint);
        } else {
            hint = "说点什么吧";
        }
        new Handler().postDelayed(() -> {
            InputMethodManager inputManager = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(dEvaluationEdt, 0);
        }, 200);
        LogUtil.show("onStart");
    }


    @OnClick(R.id.dialog_fragment_user_note_evluation_btn)
    public void onViewClicked() {
        if (iDialog != null) {
            iDialog.callBackMethod(StringUtil.EditGetString(dEvaluationEdt), null);
        }
    }

    public void clear() {
        dEvaluationEdt.setText("");
        hint = "";
    }

    @Override
    public void cancel() {
        dEvaluationEdt.setText("");
        super.cancel();
    }

}
