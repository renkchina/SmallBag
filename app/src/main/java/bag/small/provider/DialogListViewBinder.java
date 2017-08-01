package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bag.small.R;
import bag.small.interfaze.IListDialog;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/7/29.
 */
public class DialogListViewBinder extends ItemViewBinder<String, DialogListViewBinder.ViewHolder> {
    private IListDialog iListDialog;

    public DialogListViewBinder(IListDialog iListDialog) {
        this.iListDialog = iListDialog;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.dialog_item_layout, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String content) {
        holder.textView.setText(content);
        holder.textView.setOnClickListener(v -> iListDialog.callListener(content));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.dialog_popup_item_content_tv);
        }
    }
}
