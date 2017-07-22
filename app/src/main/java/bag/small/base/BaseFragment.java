package bag.small.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import bag.small.interfaze.IFragment;
import bag.small.interfaze.IRegister;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/22.
 */

public abstract class BaseFragment extends Fragment implements IFragment, IRegister {
    private static final String STATE_IS_HIDDEN = "isHidden";


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_IS_HIDDEN, isHidden());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onFirst();
        View parentView = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, parentView);
        initData();
        initView();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            boolean isHidden = savedInstanceState.getBoolean(STATE_IS_HIDDEN);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (isHidden) {
                transaction.hide(this);
                onFragmentHide();
            } else {
                transaction.show(this);
                onFragmentShow();
            }
            transaction.commit();
        }
        register();
    }

    public void toast(Object object) {
        if (object == null) {
            return;
        }
        if (TextUtils.isEmpty(object.toString())) {
            Toast.makeText(getActivity(), " ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), object.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        unRegister();
    }

    @Override
    public void register() {

    }

    @Override
    public void unRegister() {

    }

    @Override
    public void onFragmentHide() {

    }

    @Override
    public void onFirst() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    public void goActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }
}
