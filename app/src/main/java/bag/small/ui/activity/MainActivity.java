package bag.small.ui.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.base.BaseFragment;
import bag.small.entity.LoginResult;
import bag.small.interfaze.IViewBinder;
import bag.small.provider.AccountViewBinder;
import bag.small.ui.fragment.FamiliesSchoolConnectionFragment;
import bag.small.ui.fragment.GrowthDiaryFragment;
import bag.small.ui.fragment.TreasureChestFragment;
import bag.small.utils.ListUtil;
import butterknife.Bind;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, IViewBinder {

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
    @Bind(R.id.main_drawer_left_exit_tv)
    TextView mdlExitTv;

    BaseFragment[] fragments;
    private MenuItem lastItem;
    List<Object> itemDatas;
    MultiTypeAdapter multiTypeAdapter;

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
        changeFragment(0);
        mBottomNav.setOnNavigationItemSelectedListener(this);
        lastItem = mBottomNav.getMenu().getItem(0);
        itemDatas = new Items();
        if (ListUtil.unEmpty(MyApplication.loginResults)) {
            itemDatas.clear();
            itemDatas.addAll(MyApplication.loginResults);
        }
    }

    @Override
    public void initView() {
        multiTypeAdapter = new MultiTypeAdapter(itemDatas);
        multiTypeAdapter.register(LoginResult.RoleBean.class, new AccountViewBinder(this));
        mdlRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mdlRecycler.setAdapter(multiTypeAdapter);
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
    }

    //    private void disableShiftMode(BottomNavigationView navigationView) {
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
//        try {
//            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
//            shiftingMode.setAccessible(true);
//            shiftingMode.setBoolean(menuView, false);
//            shiftingMode.setAccessible(false);
//            int counts = menuView.getChildCount();
//            for (int i = 0; i < counts; i++) {
//                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
//                itemView.setShiftingMode(false);
//                itemView.setChecked(itemView.getItemData().isChecked());
//            }
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
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
                goActivity(RegisterActivity.class);
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
                finish();
                break;
        }
    }

    @Override
    public void click(int position) {
//        LoginResult.RoleBean bean = (LoginResult.RoleBean) itemDatas.get(position);
    }

    @Override
    public void add(int type, int position) {
    }

    @Override
    public void delete(int position) {
    }
}
