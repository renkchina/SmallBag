package bag.small.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import bag.small.R;
import bag.small.base.BaseFragment;
import bag.small.provider.chat.ChatFileViewBinder;
import bag.small.provider.chat.ChatImageViewBinder;
import bag.small.provider.chat.ChatTXTViewBinder;
import bag.small.utils.ListUtil;
import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/12/29.
 *
 */

public class FragmentTeacherHistoryChatList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.fragment_chat_list_re)
    RecyclerView chatListView;
    @BindView(R.id.chat_swipe_layout)
    SwipeRefreshLayout refreshLayout;
    MultiTypeAdapter multiTypeAdapter;
    List items;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_teacher_history_layout;
    }

    @Override
    public void initData() {
        items = new ArrayList();
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.register(EMMessage.class)
                .to(new ChatTXTViewBinder(), new ChatImageViewBinder(), new ChatFileViewBinder())
                .withClassLinker(emMessage -> {
                    if (emMessage.getType().equals(EMMessage.Type.TXT)) {
                        return ChatTXTViewBinder.class;
                    } else if (emMessage.getType().equals(EMMessage.Type.IMAGE)) {
                        return ChatImageViewBinder.class;
                    } else {
                        return ChatFileViewBinder.class;
                    }
                });
        chatListView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatListView.setAdapter(multiTypeAdapter);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }


    public void setChatListDatas(List<EMMessage> messages) {
        if (ListUtil.unEmpty(messages)) {
            items.clear();
            items.addAll(messages);
            multiTypeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFragmentShow() {

    }

}
