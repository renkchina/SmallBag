package bag.small.provider.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.DateUtils;
import com.hyphenate.util.TextFormater;

import java.io.File;
import java.util.Date;

import bag.small.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2017/12/29.
 */
public class ChatFileViewBinder extends ItemViewBinder<EMMessage, ChatFileViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(com.hyphenate.easeui.R.layout.ease_row_received_file, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EMMessage chatFileBean) {
        Context context = holder.root.getContext();
        EMFileMessageBody body = (EMFileMessageBody) chatFileBean.getBody();
        holder.fileNameView.setText(body.getFileName());
//        holder.fileSizeView.setText(TextFormater.getDataSize(body.getFileSize()));
        String filePath = "";
        File file = new File(filePath);
        if (file.exists()) {
            holder.fileStateView.setText(com.hyphenate.easeui.R.string.Have_downloaded);
        } else {
            holder.fileStateView.setText(com.hyphenate.easeui.R.string.Did_not_download);
        }


        if (holder.timestamp != null) {
            if (getPosition(holder) == 0) {
                holder.timestamp.setText(DateUtils.getTimestampString(new Date(chatFileBean.getMsgTime())));
                holder.timestamp.setVisibility(View.VISIBLE);
            } else {
                // show time stamp if interval with last message is > 30 seconds
                EMMessage prevMessage = (EMMessage) getAdapter().getItems().get(getPosition(holder) - 1);
                if (prevMessage != null && DateUtils.isCloseEnough(chatFileBean.getMsgTime(), prevMessage.getMsgTime())) {
                    holder.timestamp.setVisibility(View.GONE);
                } else {
                    holder.timestamp.setText(DateUtils.getTimestampString(new Date(chatFileBean.getMsgTime())));
                    holder.timestamp.setVisibility(View.VISIBLE);
                }
            }
        }
        String userName = chatFileBean.getStringAttribute("ChatUserNick", "");
        String userHead = chatFileBean.getStringAttribute("ChatUserPic", "");
        EaseUserUtils.setUserAvatar(context, userName, holder.userAvaterView);
        EaseUserUtils.setUserNick(userHead, holder.usernickView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView percentageView;
        TextView fileSizeView;
        TextView fileStateView;
        TextView fileNameView;

        TextView timestamp;
        TextView usernickView;
        ImageView userAvaterView;
        View root;

        ViewHolder(View view) {
            super(view);
            fileNameView = (TextView) view.findViewById(com.hyphenate.easeui.R.id.tv_file_name);
            fileSizeView = (TextView) view.findViewById(com.hyphenate.easeui.R.id.tv_file_size);
            fileStateView = (TextView) view.findViewById(com.hyphenate.easeui.R.id.tv_file_state);
            percentageView = (TextView) view.findViewById(com.hyphenate.easeui.R.id.percentage);

            timestamp = (TextView) view.findViewById(R.id.timestamp);
            userAvaterView = (ImageView) view.findViewById(R.id.iv_userhead);
            usernickView = (TextView) view.findViewById(R.id.tv_userid);
            root = view;
        }

        public void onMessageSuccess() {
            if (percentageView != null)
                percentageView.setVisibility(View.INVISIBLE);
        }
    }


}
