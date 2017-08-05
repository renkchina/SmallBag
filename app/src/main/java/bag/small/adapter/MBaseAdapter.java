package bag.small.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bag.small.ui.activity.MainActivity;
import bag.small.utils.ListUtil;

/**
 * Created by Administrator on 2017/8/5.
 */

public abstract class MBaseAdapter<T> extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<T> mDatas;
    private int mItemLayoutId;
    protected int position;
    private Bundle mBundle;

    public MBaseAdapter(Context context, int itemLayoutId) {
        this(context, null, itemLayoutId);
    }

    MBaseAdapter(Context context, List<T> datas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mDatas = datas;
        if (datas == null) {
            this.mDatas = new ArrayList<>();
        }
        this.mItemLayoutId = itemLayoutId;
        mBundle = new Bundle();
    }

    protected Context getContext() {
        return mContext;
    }

    public List<T> getDatas() {
        return this.mDatas;
    }

    public int getCount() {
        return ListUtil.isEmpty(mDatas) ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        if (position <= mDatas.size() - 1) {
            return this.mDatas.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public void addAll(List<T> list) {
        if (ListUtil.isEmpty(list)) {
            return;
        }
        this.mDatas.addAll(list);
        this.notifyDataSetChanged();
    }

    public void addFirst(List<T> list) {
        if (ListUtil.isEmpty(list)) {
            return;
        }
        if (mDatas != null) {
            mDatas.addAll(0, list);
            notifyDataSetChanged();
        }
    }

    public void addFirst(T item) {
        this.mDatas.add(0, item);
        this.notifyDataSetChanged();
    }

    public void addItem(T item) {
        this.mDatas.add(item);
        this.notifyDataSetChanged();
    }

    public void remove(T item) {
        if (item != null) {
            if (!ListUtil.isEmpty(this.mDatas)) {
                if (this.mDatas.contains(item)) {
                    this.mDatas.remove(item);
                    this.notifyDataSetChanged();
                }
            }
        }
    }

    public void clear() {
        if (!ListUtil.isEmpty(this.mDatas)) {
            this.mDatas.clear();
        }
        this.notifyDataSetChanged();
    }

    public void setList(List<T> list) {
        mDatas.clear();
        if (!ListUtil.isEmpty(list)) {
            addAll(list);
        } else {
            notifyDataSetChanged();
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MViewHolder holder = this.getViewHolder(position, convertView,
                parent);
        this.position = position;
        this.convert(holder, this.getItem(position), position);
        return holder.getConvertView();
    }

    public abstract void convert(MViewHolder holder, T bean, int position);

    private MViewHolder getViewHolder(int position, View convertView,
                                      ViewGroup parent) {
        return MViewHolder.get(this.mContext, convertView, parent,
                this.mItemLayoutId, position);
    }

    protected void toast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转到另一个页面
     */
    public void gotoActivity(Class<? extends Activity> cls, boolean finish,
                             Bundle bundle) {

        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
        if (finish) {
            ((Activity) mContext).finish();
        }
    }

    protected void gotoMainActivity(int page) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        if(page >= 0) {
            intent.putExtra("page", page);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getContext().startActivity(intent);
    }
}
