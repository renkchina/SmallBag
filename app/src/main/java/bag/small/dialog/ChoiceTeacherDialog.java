package bag.small.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import bag.small.R;
import bag.small.entity.IMChoiceTeacher;
import bag.small.interfaze.IDialog;
import bag.small.provider.DialogSnapViewBinder;
import bag.small.provider.IMChoiceTeacherViewBinder;
import bag.small.utils.ListUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/12/18.
 */

public class ChoiceTeacherDialog extends Dialog {
    @BindView(R.id.dialog_choice_teacher_recyclerView)
    RecyclerView dRecyclerView;
    @BindView(R.id.dialog_reset_tv)
    TextView dialogResetTv;
    @BindView(R.id.dialog_right_tv)
    TextView dialogRightTv;
    private IDialog iDialog;
    private Context context;
    private List items;
    private MultiTypeAdapter multiTypeAdapter;
    private String title;
    private String content;

    public ChoiceTeacherDialog(@NonNull Context context) {
        super(context, R.style.myNoFrame_Dialog);
        this.context = context;
        if (context instanceof IDialog) {
            iDialog = (IDialog) context;
        }
        items = new Items(5);
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.register(IMChoiceTeacher.class, new IMChoiceTeacherViewBinder());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choice_teacher_layout);
        ButterKnife.bind(this);

        initView();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics();
        lp.width = d.widthPixels;
        lp.gravity = Gravity.TOP;
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(true);
    }

    private void initView() {
        dRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        dRecyclerView.setAdapter(multiTypeAdapter);
    }


    @OnClick({R.id.dialog_reset_tv, R.id.dialog_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_reset_tv:
                setAllUnChecked();
                if (iDialog != null) {
                    iDialog.callBackMiddleMethod();
                }
                break;
            case R.id.dialog_right_tv:
                if (iDialog != null) {
                    iDialog.callBackMethod(getAllChecked(), null);
                }
                dismiss();
                break;
        }

    }

    private void setAllUnChecked() {
        if (ListUtil.unEmpty(items)) {
            for (Object item : items) {
                if (item instanceof IMChoiceTeacher) {
                    ((IMChoiceTeacher) item).setChecked(false);
                }
            }
            multiTypeAdapter.notifyDataSetChanged();
        }
    }

    private String getAllChecked() {
        if (ListUtil.unEmpty(items)) {
            StringBuilder builder = new StringBuilder();
            for (Object item : items) {
                if (item instanceof IMChoiceTeacher) {
                    if (((IMChoiceTeacher) item).isChecked()) {
                        builder.append(((IMChoiceTeacher) item).getItem_id()).append(",");
                    }
                }
            }
            if (TextUtils.isEmpty(builder.toString())) {
                return "";
            } else
                return builder.toString().substring(0, builder.toString().length() - 1);
        } else {
            return "";
        }
    }

    public void initData(List list) {
        items.clear();
        items.addAll(list);
    }

    public void setListData(List list) {
        items.clear();
        items.addAll(list);
        multiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void show() {
        super.show();
//        multiTypeAdapter.notifyDataSetChanged();
    }
}
