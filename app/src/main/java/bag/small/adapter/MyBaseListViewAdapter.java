package bag.small.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */

public abstract class MyBaseListViewAdapter<T> extends BaseAdapter {
    private List<T> mDatas;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyBaseListViewAdapter(List<T> mDatas, Context context) {
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

}
