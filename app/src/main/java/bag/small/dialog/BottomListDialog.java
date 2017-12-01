package bag.small.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import bag.small.R;
import bag.small.entity.ChoiceClassLists;
import bag.small.provider.ChoiceClassListsBinder;
import bag.small.view.RecycleViewDivider;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/12/1.
 */

public class BottomListDialog extends Dialog {


    @BindView(R.id.dialog_bottom_recyclerView)
    RecyclerView mRecyclerView;
    Context context;
    List items;
    MultiTypeAdapter multiTypeAdapter;

    public BottomListDialog(@NonNull Context context) {
        super(context, R.style.myNoFrame_Dialog);
        this.context = context;
        initData();
    }

    private void initData() {
        items = new Items();
        multiTypeAdapter = new MultiTypeAdapter(items);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_bottom_list_layout);
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
        multiTypeAdapter.register(ChoiceClassLists.KechenBean.class, new ChoiceClassListsBinder());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(context, R.color.un_enable_gray)));
        mRecyclerView.setAdapter(multiTypeAdapter);
    }

    public void setListData(List listData) {
        items.clear();
        items.add(new ChoiceClassLists.KechenBean("", "兴趣课名",
                "上课教室", "上课时间", "授课老师"));
        items.addAll(listData);
    }
}
