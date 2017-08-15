package bag.small.rx;

import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

import bag.small.utils.StringUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/5/16.
 */

public class RxCountDown {

    public static Observable<Long> countdown(long time) {
        if (time < 0) time = 0;
        final long countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(aLong -> countTime - aLong)
                .take(countTime);

    }

    //验证码 倒计时
    public static void TimerDown(long time, final TextView button) {
        countdown(time)
                .doOnNext(integer -> button.setEnabled(false))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long integer) {
                        button.setText("剩余" + integer + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                        button.setEnabled(true);
                        button.setText("获取验证码");
                    }

                    @Override
                    public void onComplete() {
                        button.setEnabled(true);
                        button.setText("获取验证码");
                    }
                });
    }

    public static void examinationTimerDown(final TextView tv, long time) {
        countdown(time)
                .map(aLong -> StringUtil.getTimeFormatMS(aLong * 1000))
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                tv.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
