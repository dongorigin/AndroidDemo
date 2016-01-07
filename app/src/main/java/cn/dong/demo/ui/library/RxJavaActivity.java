package cn.dong.demo.ui.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import timber.log.Timber;

/**
 * RxJava测试
 * <p/>
 * author DONG 2015/11/15.
 */
public class RxJavaActivity extends BaseActivity {
    @InjectView(R.id.image)
    ImageView mImageView;
    @InjectView(R.id.click)
    Button mButton;

    private PublishSubject<Void> actionPublishSubject;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_rxjava;
    }

    @Override
    protected void process(Bundle savedInstanceState) {
        super.process(savedInstanceState);

        Observable.just(R.mipmap.ic_launcher) // io线程，subscribeOn指定
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<Integer, Bitmap>() { // io线程，observeOn指定
                    @Override
                    public Bitmap call(Integer resId) {
                        return BitmapFactory.decodeResource(getResources(), resId);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() { // main线程，observeOn指定
                    @Override
                    public void call(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                });

        actionPublishSubject = PublishSubject.create();
        actionPublishSubject
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Timber.d("click");
                    }
                });

    }

    @OnClick(R.id.click)
    void click() {
        actionPublishSubject.onNext(null);
    }
}
