package bag.small.ui.activity;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import com.china.rxbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.ListDialog;
import bag.small.dialog.NoticeDialogSnap;
import bag.small.entity.GradeClass;
import bag.small.entity.RelateBanjiBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.IGradeClass;
import bag.small.interfaze.IDialog;
import bag.small.interfaze.IListDialog;
import bag.small.rx.RxUtil;
import bag.small.utils.StringUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;

public class CreateMemorandumActivity extends BaseActivity implements IDialog {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    IGradeClass iGradeClass;
    private ListDialog listDialog;

    private List<RelateBanjiBean> banjiList;
    private List<String> subjectList;
    private List<String> classList;
    private List<GradeClass> beanList;

    private String subjectId;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_create_memorandum;
    }

    @Override
    public void initData() {
        title.setText("新建备忘录");
        toolbar.setNavigationIcon(null);
        noticeDialogSnap = new NoticeDialogSnap(this);
        iGradeClass = HttpUtil.getInstance().createApi(IGradeClass.class);
        listDialog = new ListDialog(this);
        classList = new ArrayList<>();
        subjectList = new ArrayList<>();
        RxBus.get().register(this);
    }


    @Override
    public void initView() {
        iGradeClass.getPublishGradeClass(UserPreferUtil.getInstanse().getRoleId(),
                UserPreferUtil.getInstanse().getUserId(), UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(listBaseBean -> {
                    if (listBaseBean.isSuccess() &&
                            listBaseBean.getData() != null &&
                            !listBaseBean.getData().isEmpty()) {
                        beanList = listBaseBean.getData();
                        for (GradeClass gradeClass : beanList) {
                            subjectList.add(gradeClass.getKemuName());
                        }
                    }
                }, new HttpError());
    }

    @OnClick({R.id.create_memorandum_type_ll,
            R.id.create_memorandum_selected_class_ll,
            R.id.create_memorandum_preview_tv,
            R.id.create_memorandum_send_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_memorandum_type_ll:
                if (!subjectList.isEmpty()) {
                    setListDialog(subjectList, mSubjectTv);
                    listDialog.setListDialog((position, content) -> {
                        StringUtil.setTextView(mSubjectTv, content);
                        GradeClass bean = beanList.get(position);
                        subjectId = bean.getKemuId();
                        if (bean.getRelate() != null && !bean.getRelate().isEmpty()) {
                            banjiList = bean.getRelate();
                        }
                    });
                }
                break;
            case R.id.create_memorandum_selected_class_ll:
                if (!banjiList.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) banjiList);
                    goActivity(CheckClassActivity.class, bundle);
                } else {
                    toast("请先选择学科");
                }
                break;
            case R.id.create_memorandum_preview_tv:
                noticeDialogSnap.show();
                break;
            case R.id.create_memorandum_send_tv:
                String title = StringUtil.EditGetString(mTitleEdt);
                String content = StringUtil.EditGetString(mContentEdt);
                if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(title)) {

                }
                break;
        }
    }

    @MySubscribe(code = 333333, threadMode = ThreadMode.MAIN)
    public void getCheckedClass(List<RelateBanjiBean> list) {
        banjiList = list;
        StringBuilder sb = new StringBuilder();
        for (RelateBanjiBean bean : list) {
            if (bean.isChecked()) {
                sb.append(bean.getNianji());
                sb.append("年级");
                sb.append(bean.getBanci());
                sb.append("班");
                sb.append(",");
            }
        }
        String string = sb.toString();
        string = string.substring(0, string.length() - 1);
        mClassesTv.setText(string);
    }

    private void setListDialog(List<String> list, TextView view) {
        listDialog.setListData(list);
        listDialog.show(view);
    }

    @Override
    public void callBackMethod(Object object, Object bean) {

    }

    @Override
    public void callBackMiddleMethod() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }
}
