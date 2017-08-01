package bag.small.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bag.small.R;
import bag.small.interfaze.IViewBinder;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/8/1.
 */
public class TeachSubjectViewBinder extends ItemViewBinder<TeachSubject, TeachSubjectViewBinder.ViewHolder> {

    private IViewBinder iViewBinder;

    public TeachSubjectViewBinder(IViewBinder iViewBinder) {
        this.iViewBinder = iViewBinder;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_teach_subject_ll, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TeachSubject teachSubject) {
        holder.itemSubjectAddIv.setOnClickListener(v -> {
            if(iViewBinder!=null){
                iViewBinder.add(1,getPosition(holder));
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_subject_tv)
        TextView itemSubjectTv;
        @Bind(R.id.item_subject_add_iv)
        ImageView itemSubjectAddIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
