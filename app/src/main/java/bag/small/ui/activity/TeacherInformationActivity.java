package bag.small.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseActivity;
import bag.small.dialog.ListDialog;
import bag.small.interfaze.IListDialog;
import bag.small.interfaze.IViewBinder;
import bag.small.provider.TeachClass;
import bag.small.provider.TeachSubject;
import bag.small.provider.TeachSubjectClass;
import bag.small.provider.TeachSubjectClassViewBinder;
import bag.small.utils.ImageUtil;
import bag.small.view.RecycleViewDivider;
import butterknife.Bind;
import butterknife.OnClick;
import me.drakeet.multitype.MultiTypeAdapter;

public class TeacherInformationActivity extends BaseActivity
        implements IViewBinder, IListDialog {
    @Bind(R.id.ac_teacher_head_iv)
    ImageView mHeadImage;
    @Bind(R.id.activity_guardian_tv)
    TextView aGuardianTv;
    @Bind(R.id.activity_guardian_ll)
    LinearLayout aGuardianLl;
    @Bind(R.id.activity_charge_class_tv)
    TextView aChargeClassTv;
    @Bind(R.id.activity_is_teacher_tv)
    TextView isClassTeacher;
    @Bind(R.id.activity_charge_class_ll)
    LinearLayout aChargeClassLl;
    @Bind(R.id.ac_teacher_number_tv)
    TextView acTeacherNumberTv;
    @Bind(R.id.ac_teacher_grade_tv)
    TextView acTeacherGradeTv;
    @Bind(R.id.ac_teacher_class_tv)
    TextView acTeacherClassTv;
    @Bind(R.id.activity_teacher_subject_recycler)
    RecyclerView subjectRecycler;
    @Bind(R.id.activity_teacher_commit_btn)
    Button aTeacherCommitBtn;
    MultiTypeAdapter multiTypeAdapter;
    List<Object> items;
    ListDialog listDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_teacher_information;
    }

    @Override
    public void initView() {
        items = new ArrayList<>();
        items.add(new TeachSubjectClass(false));
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.register(TeachSubjectClass.class, new TeachSubjectClassViewBinder(this));
        subjectRecycler.setLayoutManager(new LinearLayoutManager(this));
        subjectRecycler.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.un_enable_gray)));
        subjectRecycler.setAdapter(multiTypeAdapter);
        listDialog = new ListDialog(this);
        ImageUtil.loadCircleImages(this, mHeadImage, "");
    }

    @OnClick({R.id.activity_guardian_ll, R.id.activity_charge_class_ll,
            R.id.ac_teacher_number_tv, R.id.ac_teacher_grade_tv,
            R.id.ac_teacher_class_tv, R.id.activity_teacher_commit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_guardian_ll:
//                listDialog.setListData();
                break;
            case R.id.activity_charge_class_ll://是否班主任
                break;
            case R.id.ac_teacher_number_tv:
                listDialog.setListData(getJieci());
                listDialog.show(view);
                listDialog.setiListDialog(((TextView) view)::setText);
                break;
            case R.id.ac_teacher_grade_tv:
                listDialog.setListData(getNianJi());
                listDialog.show(view);
                listDialog.setiListDialog(((TextView) view)::setText);
                break;
            case R.id.ac_teacher_class_tv:
                listDialog.setListData(getBanji());
                listDialog.show(view);
                listDialog.setiListDialog(((TextView) view)::setText);
                break;
            case R.id.activity_teacher_commit_btn:
                break;
        }
    }

    @Override
    public void add(int type, int position) {
        position += 1;
        if (type == 1) {
            items.add(position, new TeachSubject());
        } else if (type == 2) {
            items.add(position, new TeachClass());
        } else {
            items.add(new TeachSubjectClass(true));
        }
        multiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void delete(int position) {
        items.remove(position);
        multiTypeAdapter.notifyDataSetChanged();
    }


    private List<String> getJieci() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            lists.add(String.valueOf(i + 1990) + "届");
        }
        return lists;
    }

    private List<String> getNianJi() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            lists.add(String.valueOf(i + 1) + "年级");
        }
        return lists;
    }

    private List<String> getBanji() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            lists.add(String.valueOf(i + 1) + "班");
        }
        return lists;
    }

    @Override
    public void callListener(String content) {

    }
}
