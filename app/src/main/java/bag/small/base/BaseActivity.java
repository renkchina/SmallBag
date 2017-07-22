package bag.small.base;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Toast;

import bag.small.interfaze.IActivity;
import bag.small.interfaze.IRegister;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/22.
 */

public abstract class BaseActivity extends AppCompatActivity implements IActivity, IRegister {

    private BaseFragment mCurrentFragment;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initPre();
        BaseActivityStack.getInstance().addActivity(this);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("renkchina", Activity.MODE_PRIVATE);
        onFirst();
        initData();
        initView();
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
    public void goActivity(@NonNull Class clazz, @NonNull Bundle bundle) {
        Intent intent = new Intent(this, clazz);
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
