package bag.small.ui.activity;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.base.BaseFragment;
import bag.small.entity.LoginResult;
import bag.small.provider.AccountViewBinder;
import bag.small.ui.fragment.FamiliesSchoolConnectionFragment;
import bag.small.ui.fragment.GrowthDiaryFragment;
import bag.small.ui.fragment.TreasureChestFragment;
import bag.small.utils.ListUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.Bind;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolTitle;
    @Bind(R.id.activity_main_content_frame)
    FrameLayout mContentFrame;
    @Bind(R.id.activity_main_bottom_nav)
    BottomNavigationView mBottomNav;
    @Bind(R.id.activity_main_content_ll)
    LinearLayout mContentLl;
    @Bind(R.id.activity_main_drawer)
    DrawerLayout mDrawer;
    @Bind(R.id.main_drawer_left_recycler)
    RecyclerView mdlRecycler;
    @Bind(R.id.main_drawer_left_add_account_btn)
    Button mdlAddAccountBtn;
    @Bind(R.id.main_drawer_left_account_manager_tv)
    TextView mdlAccountManagerTv;
    @Bind(R.id.main_drawer_left_soft_setting_tv)
    TextView mdlSoftSettingTv;
    @Bind(R.id.main_drawer_left_help_tv)
    TextView mdlHelpTv;
    @Bind(R.id.main_drawer_left_about_tv)
    TextView mdlAboutTv;
    @Bind(R.id.toolbar_right_iv)
    ImageView toolbarRightIv;
    @Bind(R.id.main_drawer_left_exit_tv)
    TextView mdlExitTv;
    BaseFragment[] fragments;
    private MenuItem lastItem;
    List<Object> itemDatas;
    MultiTypeAdapter multiTypeAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        fragments = new BaseFragment[3];
        fragments[0] = new TreasureChestFragment();
        fragments[1] = new FamiliesSchoolConnectionFragment();
        fragments[2] = new GrowthDiaryFragment();
        mBottomNav.setOnNavigationItemSelectedListener(this);
        changeFragment(1);
        lastItem = mBottomNav.getMenu().getItem(1);
        lastItem.setChecked(true);
        mBottomNav.setSelectedItemId(R.id.item_family);
        itemDatas = new Items();
        if (ListUtil.unEmpty(MyApplication.loginResults)) {
            itemDatas.clear();
            itemDatas.addAll(MyApplication.loginResults);
        }
    }

    @Override
    public void initView() {

        multiTypeAdapter = new MultiTypeAdapter(itemDatas);
        multiTypeAdapter.register(LoginResult.RoleBean.class, new AccountViewBinder());
        mdlRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mdlRecycler.setAdapter(multiTypeAdapter);
        toolbar.setTitle("");
        toolTitle.setText("小书包");//设置Toolbar标题
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        mDrawer.addDrawerListener(mDrawerToggle);
    }

    @Override
    public void setToolTitle(String title, boolean isTurnLeft) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (lastItem != item) {
            lastItem = item;
            switch (item.getItemId()) {
                case R.id.item_treasure:
                    changeFragment(0);
                    break;
                case R.id.item_family:
                    changeFragment(1);
                    break;
                case R.id.item_growth:
                    changeFragment(2);

                    break;
            }
            return true;
        }
        return false;
    }

    private void changeFragment(int index) {
        changeFragment(R.id.activity_main_content_frame, fragments[index]);
        if (index == 2) {
            toolbarRightIv.setVisibility(View.VISIBLE);
            toolbarRightIv.setImageResource(R.mipmap.icon_riji_blue);
        } else {
            toolbarRightIv.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MobclickAgent.onKillProcess(this);
    }

    @OnClick({R.id.main_drawer_left_add_account_btn,
            R.id.main_drawer_left_account_manager_tv,
            R.id.main_drawer_left_soft_setting_tv,
            R.id.main_drawer_left_help_tv,
            R.id.main_drawer_left_about_tv,
            R.id.main_drawer_left_exit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_drawer_left_add_account_btn:
                showChoiceDialog();
                break;
            case R.id.main_drawer_left_account_manager_tv:
                break;
            case R.id.main_drawer_left_soft_setting_tv:
                break;
            case R.id.main_drawer_left_help_tv:
                break;
            case R.id.main_drawer_left_about_tv:
                break;
            case R.id.main_drawer_left_exit_tv:
                UserPreferUtil.getInstanse().clear();
                skipActivity(LoginActivity.class);
                break;
        }
    }

    @MySubscribe(code = 9527)
    public void showDrawerLayout() {
        if (!mDrawer.isDrawerOpen(Gravity.LEFT)) {
            mDrawer.openDrawer(Gravity.LEFT);
        } else {
            mDrawer.closeDrawer(Gravity.LEFT);
        }
    }

    protected void showChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] strings = new String[]{"我是学生", "我是老师"};
        builder.setItems(strings, (dialog, which) -> {
            switch (which) {
                case 0:
                    goActivity(ParentInformationActivity.class);
                    break;
                case 1:
                    goActivity(TeacherInformationActivity.class);
                    break;
            }
            dialog.dismiss();
        });
        builder.show();
    }


    @OnClick(R.id.toolbar_right_iv)
    public void onViewClicked() {
        goActivity(PublishMsgActivity.class, null);
    }


    @Override
    public void register() {
        RxBus.get().register(this);
    }

    @Override
    public void unRegister() {
        RxBus.get().unRegister(this);
    }
}
