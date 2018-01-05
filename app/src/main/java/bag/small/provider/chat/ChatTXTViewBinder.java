package bag.small.provider.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.DateUtils;

import java.util.Date;

import bag.small.R;
import bag.small.utils.StringUtil;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/12/29.
 */
public class ChatTXTViewBinder extends ItemViewBinder<EMMessage, ChatTXTViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(com.hyphenate.easeui.R.layout.ease_row_received_message, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EMMessage chatTXT) {
        EMTextMessageBody txtBody = (EMTextMessageBody) chatTXT.getBody();
        StringUtil.setTextView(holder.contentView, txtBody.getMessage());
        Context context = holder.root.getContext();
        if (holder.timestamp != null) {
            if (getPosition(holder) == 0) {
                holder.timestamp.setText(DateUtils.getTimestampString(new Date(chatTXT.getMsgTime())));
                holder.timestamp.setVisibility(View.VISIBLE);
            } else {
                // show time stamp if interval with last message is > 30 seconds
                EMMessage prevMessage = (EMMessage) getAdapter().getItems().get(getPosition(holder) - 1);
                if (prevMessage != null && DateUtils.isCloseEnough(chatTXT.getMsgTime(), prevMessage.getMsgTime())) {
                    holder.timestamp.setVisibility(View.GONE);
                } else {
                    holder.timestamp.setText(DateUtils.getTimestampString(new Date(chatTXT.getMsgTime())));
                    holder.timestamp.setVisibility(View.VISIBLE);
                }
            }
        }
        String userName = chatTXT.getStringAttribute("ChatUserNick", "");
        String userHead = chatTXT.getStringAttribute("ChatUserPic", "");
        //显示头像
        EaseUserUtils.setUserAvatar(context, userHead, holder.userAvaterView);
        //显示昵称
        EaseUserUtils.setUserNick(userName, holder.usernickView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView contentView;
        TextView timestamp;
        TextView usernickView;
        ImageView userAvaterView;
        View root;

        ViewHolder(View view) {
            super(view);
            contentView = (TextView) itemView.findViewById(com.hyphenate.easeui.R.id.tv_chatcontent);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            userAvaterView = (ImageView) view.findViewById(R.id.iv_userhead);
            usernickView = (TextView) view.findViewById(R.id.tv_userid);
            root = view;
        }
    }
}
