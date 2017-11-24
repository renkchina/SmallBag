package bag.small.ui.activity;


import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.interfaze.IDialog;
import butterknife.BindView;
import butterknife.OnClick;

public class CreateMemorandumActivity extends BaseActivity implements IDialog {
    @BindView(R.id.toolbar_title_tv)
    TextView title;
    @BindView(R.id.create_memorandum_cancel_tv)
    TextView mCancelTv;
    @BindView(R.id.create_memorandum_send_tv)
    TextView mSendTv;
    @BindView(R.id.create_memorandum_title_edt)
    EditText mTitleEdt;
    @BindView(R.id.create_memorandum_content_edt)
    EditText mContentEdt;
    @BindView(R.id.create_memorandum_subject_tv)
    TextView mSubjectTv;
    @BindView(R.id.create_memorandum_type_ll)
    LinearLayout mTypeLl;
    @BindView(R.id.create_memorandum_classes_tv)
    TextView mClassesTv;
    @BindView(R.id.create_memorandum_selected_class_ll)
    LinearLayout mSelectedClassLl;
    @BindView(R.id.create_memorandum_preview_tv)
    TextView mPreviewTv;
    NoticeDialogSnap noticeDialogSnap;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_create_memorandum;
    }

    @Override
    public void initData() {
        title.setText("");
        noticeDialogSnap = new NoticeDialogSnap(this);
    }

    @OnClick({R.id.create_memorandum_type_ll,
            R.id.create_memorandum_selected_class_ll,
            R.id.create_memorandum_preview_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_memorandum_type_ll:
                break;
            case R.id.create_memorandum_selected_class_ll:
                break;
            case R.id.create_memorandum_preview_tv:
                noticeDialogSnap.show();
                break;
        }
    }

    @Override
    public void callBackMethod(Object object, Object bean) {

    }

    @Override
    public void callBackMiddleMethod() {

    }
}
