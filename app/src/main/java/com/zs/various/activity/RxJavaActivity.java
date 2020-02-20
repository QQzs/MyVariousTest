package com.zs.various.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.zs.various.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zs
 * Date：2018年 09月 21日
 * Time：15:27
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class RxJavaActivity extends AppCompatActivity{

    private ImageView iv_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        iv_image = findViewById(R.id.iv_image);


        /**
         * create  just  fromArray  区别
         *
         * 1.create( ),最基本的创建方式 , 手动发射数据 , 有耗时操作用create
         *
         * 2.just( )，将为你创建一个Observable并自动为你调用onNext( )发射数据
         *
         * 3.fromArray( )，遍历集合，接受一个集合作为输入，然后每次输出一个元素给subscriber
         */
        Observable.just("s")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return "";
                    }
                })
                .subscribeOn(Schedulers.io()) // 事件执行的线程
                .observeOn(AndroidSchedulers.mainThread()) // 事件回调的线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        /**
         * 如果fromArray()里面执行了耗时操作，即使使用了subscribeOn(Schedulers.io())，
         * 仍然是在主线程执行，可能会造成界面卡顿甚至崩溃，
         * 所以耗时操作还是使用Observable.create(…);
         */
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(ObservableEmitter<Drawable> emitter) throws Exception {
                // 发射数据
                emitter.onNext(getDrawableFromUrl(""));
                // 发射完成,这种方法需要手动调用onCompleted，才会回调Observer的onCompleted方法
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Drawable>() {
                    @Override
                    public void accept(Drawable drawable) throws Exception {
                        iv_image.setBackground(drawable);
                    }
                });


        /**
         * 总结
         *
         * Observable 被观察者
         * Observer 观察者
         * subscribeOn ： 事件执行的线程
         * observeOn ： 事件回调的线程
         * 如果不关心什么时候处理结束，可以直接重载subscribe(Consumer<? spuer T> onNext)这个方法，会减少代码
         * 默认订阅 subscribe(new Observer<String>()
         */


    }

    // 模拟网络请求图片
    private Drawable getDrawableFromUrl(String url){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getResources().getDrawable(R.mipmap.ic_default_avatar);
    }

    private void action(){

        Observable.just(1)
                .filter(new Predicate<Integer>() {

                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return false;
                    }
                })
                .map(new Function<Integer, String>() {

                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf(integer);
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> e) throws Exception {
                                e.onNext("2");
                                e.onComplete();
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String s) throws Exception {

                    }
                });

    }

}
