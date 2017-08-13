package bag.small.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import bag.small.R;
import bag.small.interfaze.IActivity;
import bag.small.interfaze.IRegister;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/7/22.
 */

public abstract class BaseActivity extends AppCompatActivity implements IActivity, IRegister {

    private BaseFragment mCurrentFragment;
    private SharedPreferences sharedPreferences;
    private Toolbar mToolbar;
    private TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initPre();
        BaseActivityStack.getInstance().addActivity(this);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("renkchina", Activity.MODE_PRIVATE);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        onFirst();
        initData();
        initView();
        register();
    }

    public void setToolTitle(String title, boolean isTurnLeft) {
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
            if (isTurnLeft) {
                mToolbar.setNavigationOnClickListener(view -> finish());
            } else {
                mToolbar.setNavigationIcon(null);
            }
        }
    }

    public void setToolTitle(boolean isTurnLeft) {
        if (isTurnLeft) {
            mToolbar.setNavigationOnClickListener(view -> finish());
        }
    }

    @Override
    public void register() {

    }

    @Override
    public void unRegister() {

    }

    @Override
    public void onFirst() {

    }

    @Override
    public void initPre() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    public boolean getBoolValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void setBoolValue(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public BaseFragment getFragment() {
        return mCurrentFragment;
    }

    /**
     * 用Fragment替换视图
     *
     * @param resView        将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     */
    public void changeFragment(int resView, @NonNull BaseFragment targetFragment) {
        if (targetFragment.equals(mCurrentFragment)) {
            return;
        }
        android.support.v4.app.FragmentTransaction transaction
                = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
            targetFragment.onFragmentShow();
        }
        if (mCurrentFragment != null && mCurrentFragment.isVisible()) {
            transaction.hide(mCurrentFragment);
            mCurrentFragment.onFragmentHide();
        }
        mCurrentFragment = targetFragment;
        transaction.commit();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    protected void showDialog(String title, String message, DialogInterface.OnClickListener yes) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("确定", yes);
        builder.show();
    }

    @Override
    public void skipActivity(@NonNull Class<?> cls) {
        startActivity(new Intent(this, cls));
        finish();
    }

    @Override
    public void goActivity(@NonNull Intent it) {
        startActivity(it);
    }

    @Override
    public void goActivity(@NonNull Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    @Override
    public void goActivity(@NonNull Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        BaseActivityStack.getInstance().finishActivity(this);
        unRegister();
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void toast(Object object) {
        if (object == null) {
            return;
        }
        if (TextUtils.isEmpty(object.toString())) {
            Toast.makeText(this, " ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, object.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
