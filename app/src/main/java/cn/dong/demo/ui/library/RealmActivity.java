package cn.dong.demo.ui.library;

import android.os.Bundle;
import android.os.SystemClock;

import butterknife.OnClick;
import cn.dong.demo.R;
import cn.dong.demo.model.realm.Cat;
import cn.dong.demo.model.realm.Dog;
import cn.dong.demo.model.realm.Person;
import cn.dong.demo.ui.common.BaseActivity;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
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

    private Person person;
    private RealmResults<Dog> dogs;
    private Dog dog;
    private Cat cat1;
    private Cat cat2;

    private RealmChangeListener<RealmResults<Dog>> dogsListener = new RealmChangeListener<RealmResults<Dog>>() {
        @Override
        public void onChange(RealmResults<Dog> element) {
            Timber.d("main: dogs query, auto update");
//            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                dog = realm.where(Dog.class).equalTo("name", "dong").findFirst();
                if (dog == null) {
                    dog = realm.createObject(Dog.class, "dong");
                }

                person = realm.createObject(Person.class);
                person.setName("Claire");

                cat1 = realm.createObject(Cat.class);
                cat1.setName("Sunday");

                cat2 = realm.createObject(Cat.class);
                cat2.setName("Friday");
            }
        });

        dog.addChangeListener(new RealmChangeListener<Dog>() {
            @Override
            public void onChange(Dog element) {
                Timber.d("main: dog update");
            }
        });
        cat1.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {
                Timber.d("main: cat1 update");
            }
        });
        cat2.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {
                Timber.d("main: cat2 update");
            }
        });
        person.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {
                Timber.d("main: person update");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dog.removeChangeListeners();
        cat1.removeChangeListeners();
        person.removeChangeListeners();
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
                while (!backgroundThread.isInterrupted()) {
                    backgroundThreadRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Dog dog = realm.where(Dog.class).equalTo("name", "dong").findFirst();
                            if (dog == null) {
                                dog = realm.createObject(Dog.class, "dong");
                            }
                            dog.setAge(dog.getAge() + 1);
                            Timber.d("background: update, dog age=%d", dog.getAge());
                        }
                    });
                    mainThreadQuery();
                    SystemClock.sleep(10000 * 1000);
                }
                backgroundThreadRealm.close();
            }
        };
        backgroundThread.start();
    }

    private void mainThreadQuery() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Timber.d("main: origin, dog age=%d", dog.getAge());
                        Timber.d("main: sync query, dog age=%d", realm.where(Dog.class).equalTo("name", "dong").findFirst().getAge());
                        Dog asyncDog = realm.where(Dog.class).equalTo("name", "dong").findFirstAsync();
                        asyncDog.addChangeListener(new RealmChangeListener<Dog>() {
                            @Override
                            public void onChange(Dog dog) {
                                dog.removeChangeListener(this);
                                Timber.d("main: async query, dog age=%d", dog.getAge());
                                Timber.d("main: async query complete, origin dog age=%d", RealmActivity.this.dog.getAge());
                            }
                        });

//                        realm.executeTransaction(new Realm.Transaction() {
//                            @Override
//                            public void execute(Realm realm) {
//                                Timber.d("main: query, dog number=%d", dogs.size());
//                                Timber.d("main: query, dog number=%d", realm.where(Dog.class).findAll().size());
//                            }
//                        });
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        dogs.removeChangeListener(dogsListener);
        backgroundThread.interrupt();
    }

    @OnClick(R.id.modify_person)
    public void modifyPerson() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                person.setName("test");
            }
        });
    }

    @OnClick(R.id.modify_dog)
    public void modifyDog() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                dog.setAge(dog.getAge() + 1);
            }
        });
    }

    @OnClick(R.id.modify_cat1)
    public void modifyCat1() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                cat1.setName("Sunday");
            }
        });
    }

    @OnClick(R.id.modify_cat2)
    public void modifyCat2() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                cat2.setName("Friday");
            }
        });
    }
}
