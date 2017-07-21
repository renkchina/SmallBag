package bag.small.http.IApi;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.edriving.base.CustomerApplication;
import com.edriving.dialog.CommonProgressDialog;
import com.edriving.utils.LogUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/5/22.
 */

public class HttpError implements Consumer<Throwable> {
    private CommonProgressDialog dialog;
    private SwipeRefreshLayout swipeRefresh;

    public HttpError(SwipeRefreshLayout swipeRefresh) {
        this.swipeRefresh = swipeRefresh;
    }

    public HttpError(CommonProgressDialog dialog) {
        this.dialog = dialog;
    }

    public HttpError() {
    }

    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (swipeRefresh != null && swipeRefresh.isEnabled()) {
            swipeRefresh.setRefreshing(false);
        }
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            String exce;
            switch (httpException.code()) {
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
            Toast.makeText(CustomerApplication.getAppContext(), exce, Toast.LENGTH_SHORT).show();
            LogUtil.show(exce);
        } else {
            LogUtil.show(throwable.getMessage());
            Toast.makeText(CustomerApplication.getAppContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
