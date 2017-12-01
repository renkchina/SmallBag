package bag.small.http.IApi;

import android.app.ProgressDialog;
import android.net.ParseException;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.json.JSONException;

import java.net.ConnectException;

import bag.small.app.MyApplication;
import bag.small.utils.LogUtil;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/5/22.
 */

public class HttpError implements Consumer<Throwable> {
    private SwipeRefreshLayout swipeRefresh;
    private RefreshLayout refreshLayout;
    private ProgressDialog progressDialog;
    public HttpError() {
    }

//    public HttpError(SwipeRefreshLayout swipeRefresh) {
//        this.swipeRefresh = swipeRefresh;
//    }
//
//    public HttpError(RefreshLayout refreshLayout) {
//        this.refreshLayout = refreshLayout;
//    }
//
//    public HttpError(ProgressDialog progressDialog) {
//        this.progressDialog = progressDialog;
//    }

    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
//        if (swipeRefresh != null && swipeRefresh.isEnabled()) {
//            swipeRefresh.setRefreshing(false);
//        }
//        if (refreshLayout != null) {
//            refreshLayout.finishRefresh();
//        }
//        if (progressDialog != null) {
//            progressDialog.dismiss();
//        }
        String exce;
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            switch (httpException.code()) {
                case 400:
                    exce = "参数错误！";
                    break;
                case 404:
                    exce = "未找到服务器！";
                    break;
                case 500:
                    exce = "服务器错误！";
                    break;
                default:
                    exce = "请求错误！";
                    break;
            }
        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof ParseException) {
            exce = "解析错误！";
        } else if (throwable instanceof ConnectException) {
            exce = "连接失败!";
        } else {
            exce = "未知错误!";
        }
        LogUtil.show(throwable.getMessage());
        Toast.makeText(MyApplication.getContext(), exce, Toast.LENGTH_SHORT).show();
    }


}
