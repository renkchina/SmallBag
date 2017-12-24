package bag.small.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.china.rxbus.MySubscribe;
import com.china.rxbus.RxBus;
import com.china.rxbus.ThreadMode;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
//import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.app.MyApplication;
import bag.small.base.BaseActivity;
import bag.small.base.BaseFragment;
import bag.small.entity.IMLoginEntity;
import bag.small.entity.LoginResult;
import bag.small.entity.MainLeftBean;
import bag.small.http.HttpUtil;
import bag.small.http.IApi.HttpError;
import bag.small.http.IApi.ILoginRequest;
import bag.small.http.IApi.IRegisterReq;
import bag.small.provider.AccountViewBinder;
import bag.small.provider.MainLeftBtnViewBinder;
import bag.small.provider.RoleAddBeanViewBinder;
import bag.small.rx.RxUtil;
import bag.small.ui.fragment.FamiliesSchoolConnectionFragment;
import bag.small.ui.fragment.GrowthDiaryFragment;
import bag.small.ui.fragment.TreasureChestFragment;
import bag.small.utils.ListUtil;
import bag.small.utils.LogUtil;
import bag.small.utils.UserPreferUtil;
import butterknife.BindView;
import butterknife.OnClick;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolTitle;
    @BindView(R.id.activity_main_content_frame)
    FrameLayout mContentFrame;
    @BindView(R.id.activity_main_bottom_nav)
    BottomNavigationView mBottomNav;
    @BindView(R.id.activity_main_content_ll)
    LinearLayout mContentLl;
    @BindView(R.id.main_left_ll)
    LinearLayout mainLeftLl;
    @BindView(R.id.activity_main_drawer)
    DrawerLayout mDrawer;
    @BindView(R.id.main_drawer_left_recycler)
    RecyclerView mdlRecycler;
    @BindView(R.id.toolbar_right_iv)
    ImageView toolbarRightIv;
    BaseFragment[] fragments;
    private MenuItem lastItem;
    List<Object> itemDatas;
    MultiTypeAdapter multiTypeAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean isDrawer;
    int[][] states = new int[][]{
            new int[]{-android.R.attr.state_checked},
            new int[]{android.R.attr.state_checked}
    };
    private int[] colors;
    ILoginRequest iLoginRequest;
    List<MainLeftBean> leftBeen;
    private IRegisterReq iRegisterReq;
    private ProgressDialog progressDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        fragments = new BaseFragment[3];
        fragments[0] = new FamiliesSchoolConnectionFragment();
        fragments[1] = new TreasureChestFragment();
        fragments[2] = new GrowthDiaryFragment();
        mBottomNav.setOnNavigationItemSelectedListener(this);
        changeFragment(0);
        lastItem = mBottomNav.getMenu().getItem(0);
        lastItem.setChecked(true);
        mBottomNav.setSelectedItemId(R.id.item_family);
        leftBeen = new ArrayList<>();
        leftBeen.add(new MainLeftBean());
        leftBeen.add(new MainLeftBean(1, R.mipmap.manager_account, R.string.main_account_manager));
        leftBeen.add(new MainLeftBean(2, R.mipmap.setting_icon, R.string.main_soft_setting));
        leftBeen.add(new MainLeftBean(3, R.mipmap.help, R.string.main_help_str));
        leftBeen.add(new MainLeftBean(4, R.mipmap.about_icon, R.string.main_account_about));
        leftBeen.add(new MainLeftBean(5, R.mipmap.exit_icon, R.string.main_account_exit));
        leftBeen.add(new MainLeftBean(6, 0, R.string.main_account_http));
        itemDatas = new Items();
        if (ListUtil.unEmpty(MyApplication.loginResults)) {
            itemDatas.addAll(MyApplication.loginResults);
//            itemDatas.add("");
            itemDatas.addAll(leftBeen);
        }
        iLoginRequest = HttpUtil.getInstance().createApi(ILoginRequest.class);
        iRegisterReq = HttpUtil.getInstance().createApi(IRegisterReq.class);
        colors = new int[]{ContextCompat.getColor(this, R.color.main_bottom_gray),
                ContextCompat.getColor(this, R.color.main_bottom)};
        ColorStateList csl = new ColorStateList(states, colors);
        mBottomNav.setItemTextColor(csl);
        mBottomNav.setItemIconTintList(csl);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在切换，请等待...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        iLoginRequest.updateToken(UserPreferUtil.getInstanse().getUserId(),
                "", MyApplication.deviceToken, "", "android")
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(bean -> {
                });
        multiTypeAdapter = new MultiTypeAdapter(itemDatas);
        multiTypeAdapter.register(LoginResult.RoleBean.class, new AccountViewBinder());
        multiTypeAdapter.register(MainLeftBean.class, new MainLeftBtnViewBinder());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (itemDatas.get(position) instanceof LoginResult.RoleBean) {
                    return 1;
                } else {
                    return 2;
                }
            }
        });
        mdlRecycler.setHasFixedSize(true);
        mdlRecycler.setLayoutManager(layoutManager);
        mdlRecycler.setAdapter(multiTypeAdapter);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.open, R.string.close);
        mDrawerToggle.setHomeAsUpIndicator(R.mipmap.menu);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.syncState();
        mDrawer.addDrawerListener(mDrawerToggle);
        toolbar.setNavigationOnClickListener(v -> {
            if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
                toolbar.setNavigationIcon(R.mipmap.turn_left);
            } else {
                mDrawer.openDrawer(GravityCompat.START);
                toolbar.setNavigationIcon(R.mipmap.menu);
            }
        });
        mContentLl.setOnTouchListener((view, motionEvent) ->
                isDrawer && mainLeftLl.dispatchTouchEvent(motionEvent));
        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer = true;
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left
                // 左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                mContentLl.layout(mainLeftLl.getRight(), 0,
                        mainLeftLl.getRight() + display.getWidth(),
                        display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (lastItem.getItemId() == R.id.item_family)
                    toolbar.setNavigationIcon(R.mipmap.turn_left);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer = false;
                if (lastItem.getItemId() == R.id.item_family)
                    toolbar.setNavigationIcon(R.mipmap.menu);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    public void setToolTitle(String title, boolean isTurnLeft) {

    }

    //返回
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //bottom选中
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (lastItem != item) {
            lastItem = item;
            switch (item.getItemId()) {
                case R.id.item_family:
                    changeFragment(0);
                    break;
                case R.id.item_treasure:
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

    //切换fragment
    private void changeFragment(int index) {
        changeFragment(R.id.activity_main_content_frame, fragments[index]);
        switch (index) {
            case 0:
                toolTitle.setText(UserPreferUtil.getInstanse().getSchoolName());
                toolbarRightIv.setVisibility(View.GONE);
                mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.open, R.string.close);
                mDrawerToggle.setDrawerIndicatorEnabled(false);
                mDrawerToggle.setHomeAsUpIndicator(R.mipmap.menu);
                mDrawerToggle.syncState();
                mDrawer.addDrawerListener(mDrawerToggle);
                toolbar.setNavigationOnClickListener(v -> {
                    if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                        mDrawer.closeDrawer(GravityCompat.START);
                        toolbar.setNavigationIcon(R.mipmap.turn_left);
                    } else {
                        mDrawer.openDrawer(GravityCompat.START);
                        toolbar.setNavigationIcon(R.mipmap.menu);
                    }
                });
                break;
            case 1:
                toolTitle.setText("百宝箱");
                break;
            case 2:
                toolTitle.setText("成长日记");//设置Toolbar标题
                toolbarRightIv.setVisibility(View.VISIBLE);
                toolbarRightIv.setImageResource(R.mipmap.publish_icon);
                toolbar.setNavigationIcon(null);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.toolbar_right_iv)
    public void onViewClicked() {
        goActivity(PublishMsgActivity.class, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ListUtil.unEmpty(MyApplication.loginResults)) {
            itemDatas.clear();
            itemDatas.addAll(MyApplication.loginResults);
            itemDatas.add(new LoginResult.RoleBean());
            itemDatas.addAll(leftBeen);
            multiTypeAdapter.notifyDataSetChanged();
        }
        changeRole();
    }

    @MySubscribe(code = 9527)
    public void showDrawerLayout() {
        if (!mDrawer.isDrawerOpen(Gravity.START)) {
            mDrawer.openDrawer(Gravity.START);
        } else {
            mDrawer.closeDrawer(Gravity.START);
        }
    }

    @MySubscribe(code = 301)
    public void showChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] strings = new String[]{"我是学生", "我是老师"};
        builder.setItems(strings, (dialog, which) -> {
            Bundle bundle = new Bundle();
            switch (which) {
                case 0:
                    iRegisterReq.addStudentInfo(UserPreferUtil.getInstanse().getUserId(), "student")
                            .compose(RxLifecycleCompact.bind(this).withObservable())
                            .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                            .subscribe(bean -> {
                                if (bean.isSuccess() && bean.getData() != null) {
                                    bundle.putString("login", bean.getData().getLogin_id());
                                    bundle.putString("role", bean.getData().getRole_id());
                                    bundle.putString("school", bean.getData().getSchool_id());
                                    goActivity(AddStudentActivity.class, bundle);
                                } else {
                                    toast(bean.getMsg());
                                }
                            }, new HttpError());
                    break;
                case 1:
                    iRegisterReq.addTeacherInfo(UserPreferUtil.getInstanse().getUserId(), "teacher")
                            .compose(RxLifecycleCompact.bind(this).withObservable())
                            .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                            .subscribe(bean -> {
                                if (bean.isSuccess() && bean.getData() != null) {
                                    bundle.putString("login", bean.getData().getLogin_id());
                                    bundle.putString("role", bean.getData().getRole_id());
                                    bundle.putString("school", bean.getData().getSchool_id());
                                    goActivity(AddTeacherActivity.class, bundle);
                                } else {
                                    toast(bean.getMsg());
                                }
                            }, new HttpError());
                    break;
            }
            dialog.dismiss();
        });
        builder.show();
    }


    @MySubscribe(code = 300)
    public void setUserImage() {
        if (mDrawer.isDrawerOpen(Gravity.START)) {
            mDrawer.closeDrawer(Gravity.START);
        }
        progressDialog.show();
        HttpUtil.getInstance().createApi(ILoginRequest.class)
                .loginIM(UserPreferUtil.getInstanse().getRoleId(), UserPreferUtil.getInstanse().getUserId(),
                        UserPreferUtil.getInstanse().getSchoolId())
                .compose(RxLifecycleCompact.bind(this).withObservable())
                .compose(RxUtil.applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(beans -> {
                    IMLoginEntity entity = beans.getData();
                    EMClient.getInstance().login(entity.getUsername(), entity.getPwd(),
                            new EMCallBack() {//回调
                                @Override
                                public void onSuccess() {
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                    UserPreferUtil.getInstanse().setUseChatId(entity.getUsername());
                                    LogUtil.show("登录聊天服务器成功！");
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onProgress(int progress, String status) {

                                }

                                @Override
                                public void onError(int code, String message) {
                                    new Handler(Looper.getMainLooper()).post(() -> toast("登录聊天服务器失败！"));
                                    LogUtil.show("登录聊天服务器失败！");
                                    progressDialog.dismiss();
                                }
                            });
                }, new HttpError());
        changeRole();
    }

    private void changeRole() {
        changeColor(UserPreferUtil.getInstanse().isTeacher());
        switch (lastItem.getItemId()) {
            case R.id.item_family:
                fragments[0].onFragmentShow();
                toolTitle.setText(UserPreferUtil.getInstanse().getSchoolName());
                break;
            case R.id.item_treasure:
                fragments[1].onFragmentShow();
                break;
            case R.id.item_growth:
                fragments[2].onFragmentShow();
                break;
        }
    }

    //改变主题颜色
    public void changeColor(boolean isTeacher) {
        int color;
        if (isTeacher) {
            color = ContextCompat.getColor(this, R.color.main_bottom_green);
        } else {
            color = ContextCompat.getColor(this, R.color.main_bottom);
        }
        colors = new int[]{ContextCompat.getColor(this, R.color.main_bottom_gray), color};
        ColorStateList csl = new ColorStateList(states, colors);
        mBottomNav.setItemTextColor(csl);
        mBottomNav.setItemIconTintList(csl);
        lastItem.setChecked(true);
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
