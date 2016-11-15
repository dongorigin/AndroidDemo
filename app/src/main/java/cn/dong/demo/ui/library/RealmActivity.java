package cn.dong.demo.ui.library;

import android.os.Bundle;
import android.os.SystemClock;

import cn.dong.demo.R;
import cn.dong.demo.model.realm.Dog;
import cn.dong.demo.ui.common.BaseActivity;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * @author dong on 16/1/7.
 */
public class RealmActivity extends BaseActivity {
    private Realm realm;
    private Thread backgroundThread;
    private RealmResults<Dog> dogs;

    private RealmChangeListener<RealmResults<Dog>> dogsListener = new RealmChangeListener<RealmResults<Dog>>() {
        @Override
        public void onChange(RealmResults<Dog> element) {
            Timber.d("main: auto update, dog number=%d", dogs.size());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
        realm.close();
    }

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_realm;
    }

    @Override
    protected void onResume() {
        super.onResume();
        dogs = realm.where(Dog.class).findAll();
        dogs.addChangeListener(dogsListener);

        backgroundThread = new Thread() {
            @Override
            public void run() {
                final Realm backgroundThreadRealm = Realm.getDefaultInstance();
//                while (!backgroundThread.isInterrupted()) {
                backgroundThreadRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Dog dog = realm.createObject(Dog.class, "dong");
                        Timber.d("background: update, dog number=%d", backgroundThreadRealm.where(Dog.class).findAll().size());
                    }
                });
                Observable.just(1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
//                                    Timber.d("main: query, dog number=%d", dogs.size());
//                                    Timber.d("main: query, dog number=%d", realm.where(Dog.class).findAll().size());
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
//                                            Timber.d("main: query, dog number=%d", dogs.size());
                                        Timber.d("main: query, dog number=%d", realm.where(Dog.class).findAll().size());
                                    }
                                });
                            }
                        });
                SystemClock.sleep(100000);
//                }
                backgroundThreadRealm.close();
            }
        };
        backgroundThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dogs.removeChangeListener(dogsListener);
        backgroundThread.interrupt();
    }
}
