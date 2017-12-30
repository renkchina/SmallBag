package bag.small.ui.fragment;

import com.hyphenate.chat.EMMessage;

/**
 * Created by Administrator on 2017/12/18.
 *
 */

public class TeacherChatFragment extends MyBaseShowChatFragment  {

    public void setMessages(EMMessage[] messages) {
        messageList.messageAdapter.clearAndAddMessage(messages);
    }

}