package bag.small.provider.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.DateUtils;

import java.util.Date;

import bag.small.R;
import bag.small.utils.ImageUtil;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/12/29.
 */
public class ChatImageViewBinder extends ItemViewBinder<EMMessage, ChatImageViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(com.hyphenate.easeui.R.layout.ease_row_received_picture, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EMMessage chatImageBean) {
        EMImageMessageBody imgBody = (EMImageMessageBody) chatImageBean.getBody();
        Context context = holder.root.getContext();
        ImageUtil.loadImagesOnThumbnail(context, holder.imageView, imgBody.getRemoteUrl(), 0.2f);
        if (holder.timestamp != null) {
            if (getPosition(holder) == 0) {
                holder.timestamp.setText(DateUtils.getTimestampString(new Date(chatImageBean.getMsgTime())));
                holder.timestamp.setVisibility(View.VISIBLE);
            } else {
                // show time stamp if interval with last message is > 30 seconds
                EMMessage prevMessage = (EMMessage) getAdapter().getItems().get(getPosition(holder) - 1);
                if (prevMessage != null && DateUtils.isCloseEnough(chatImageBean.getMsgTime(), prevMessage.getMsgTime())) {
                    holder.timestamp.setVisibility(View.GONE);
                } else {
                    holder.timestamp.setText(DateUtils.getTimestampString(new Date(chatImageBean.getMsgTime())));
                    holder.timestamp.setVisibility(View.VISIBLE);
                }
            }
        }
        String userName = chatImageBean.getStringAttribute("ChatUserNick", "");
        String userHead = chatImageBean.getStringAttribute("ChatUserPic", "");
        EaseUserUtils.setUserAvatar(context, userHead, holder.userAvaterView);
        if (!TextUtils.isEmpty(userName)) {
            holder.usernickView.setVisibility(View.VISIBLE);
            EaseUserUtils.setUserNick(userName, holder.usernickView);
        } else {
            holder.usernickView.setVisibility(View.GONE);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView percentageView;
        private ImageView imageView;
        View root;
        TextView timestamp;
        TextView usernickView;
        ImageView userAvaterView;

        ViewHolder(View view) {
            super(view);
            root = view;
            imageView = (ImageView) itemView.findViewById(com.hyphenate.easeui.R.id.image);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            userAvaterView = (ImageView) view.findViewById(R.id.iv_userhead);
            usernickView = (TextView) view.findViewById(R.id.tv_userid);
            percentageView = (TextView) view.findViewById(R.id.percentage);
            percentageView.setVisibility(View.INVISIBLE);
        }

    }
}
