package bag.small.download;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by pcdalao on 2017/12/10.
 */

public class RetryWithDelay implements
        Function<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retrydelay;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retrydelay) {
        this.maxRetries = maxRetries;
        this.retrydelay = retrydelay;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable
                .flatMap((Function<Throwable, Observable<Long>>) throwable -> {
                    if (++retryCount <= maxRetries) {
                        return Observable.timer(retrydelay, TimeUnit.SECONDS);
                    }
                    return Observable.error(throwable);
                });
    }
}