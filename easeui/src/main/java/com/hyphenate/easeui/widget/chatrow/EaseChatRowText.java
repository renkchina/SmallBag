package com.hyphenate.easeui.widget.chatrow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.exceptions.HyphenateException;

@SuppressLint("ViewConstructor")
public class EaseChatRowText extends EaseChatRow {

    private TextView contentView;

    public EaseChatRowText(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate((message.getIntAttribute("isteacher", 0) > 0) ?
                R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
    }

    @Override
    protected void onFindViewById() {
        contentView = (TextView) findViewById(R.id.tv_chatcontent);
    }

    @Override
    public void onSetUpView() {
        try {
            EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
            Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
            // 设置内容
            contentView.setText(span, BufferType.SPANNABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
        }
        if (!msg.isDelivered()) {
            onMessageSuccess();
        }
    }

    private void onMessageCreate() {
        if(progressBar!=null)
        progressBar.setVisibility(View.VISIBLE);
        if(statusView!=null)
        statusView.setVisibility(View.GONE);
    }

    private void onMessageSuccess() {
        if(progressBar!=null)
        progressBar.setVisibility(View.GONE);
        if(statusView!=null)
        statusView.setVisibility(View.GONE);
    }

    private void onMessageError() {
        if(progressBar!=null)
        progressBar.setVisibility(View.GONE);
        if(statusView!=null)
        statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        if(progressBar!=null)
        progressBar.setVisibility(View.VISIBLE);
        if(statusView!=null)
        statusView.setVisibility(View.GONE);
    }
}
