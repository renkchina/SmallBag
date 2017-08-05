package bag.small.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */

public abstract class MyBaseListViewAdapter<T> extends BaseAdapter {
    List<T> mDatas;
    private Context context;
    LayoutInflater layoutInflater;

    MyBaseListViewAdapter(List<T> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void gotoActivity(Class<? extends Activity> cls, boolean finish,
                             Bundle bundle) {

        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        if (finish) {
            ((Activity) context).finish();
        }
    }
}
