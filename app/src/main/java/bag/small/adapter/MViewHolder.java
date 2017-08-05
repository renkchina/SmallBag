package bag.small.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Administrator on 2017/8/5.
 */

class MViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private MViewHolder(Context context, ViewGroup parent, int layoutId,
                        int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId,
                parent, false);
        this.mConvertView.setTag(this);
    }

    public static MViewHolder get(Context context, View convertView,
                                  ViewGroup parent, int layoutId, int position) {
        return convertView == null ? new MViewHolder(context, parent, layoutId,
                position) : (MViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return this.mConvertView;
    }

    <T extends View> T getView(int viewId) {
        View view = this.mViews.get(viewId);
        if (view == null) {
            view = this.mConvertView.findViewById(viewId);
            this.mViews.put(viewId, view);
        }

        return (T) view;
    }

    public MViewHolder setText(int viewId, CharSequence text) {
        TextView view = this.getView(viewId);
        view.setText(text);
        return this;
    }

    public MViewHolder setText(int viewId, int resId) {
        return setText(viewId, getConvertView().getResources().getString(resId));
    }

    public MViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = this.getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    public MViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = this.getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    public MViewHolder setViewVisible(int viewId, int visibility) {
        View v = this.getView(viewId);
        v.setVisibility(visibility);
        return this;
    }

    public MViewHolder setTextViewColor(int viewId, int color) {
        TextView textView = this.getView(viewId);
        textView.setTextColor(color);
        return this;
    }

    public ToggleButton getButton(int viewId) {
        ToggleButton button = this.getView(viewId);
        return button;
    }

    public int getPosition() {
        return this.mPosition;
    }
}
