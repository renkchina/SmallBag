package bag.small.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import bag.small.R;
import bag.small.entity.MomentsBean;
import bag.small.interfaze.IDialog;
import bag.small.utils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/9.
 */

public class EvluateDialog extends Dialog {

    @Bind(R.id.dialog_fragment_user_note_evaluation_edt)
    EditText dEvaluationEdt;
    @Bind(R.id.dialog_fragment_user_note_evluation_btn)
    Button dEvluationBtn;
    @Bind(R.id.dialog_fragment_user_note_evaluation_ll)
    LinearLayout dEvaluationLl;
    private IDialog iDialog;
    private MomentsBean.RepayBean bean;

    public MomentsBean.RepayBean getBean() {
        return bean;
    }

    public void setBean(MomentsBean.RepayBean bean) {
        this.bean = bean;
    }

    public void setiDialog(IDialog iDialog) {
        this.iDialog = iDialog;
    }

    public EvluateDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_send_evluate_ll);
        this.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog不消失
        ButterKnife.bind(this);
    }

    @OnClick(R.id.dialog_fragment_user_note_evluation_btn)
    public void onViewClicked() {
        if (iDialog != null){
            iDialog.callBackMethod(StringUtil.EditGetString(dEvaluationEdt),bean);
        }
    }

    @Override
    public void dismiss() {
        this.cancel();
        super.dismiss();
        ButterKnife.unbind(this);
    }

    @Override
    public void show() {
        Window window = this.getWindow();
        //重新设置
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.y = 10; // 新位置Y坐标
        lp.alpha = 0.7f; // 透明度
        window.setAttributes(lp);
        this.show();
    }
}
