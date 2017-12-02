package com.example.code.archicomponentssmaples;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/12/1
 */
public class LifeCycleObserverDemo extends AppCompatActivity {

    interface CallBack{
        void start();
        void stop();
    }

    class MyLifeCycleObserver implements LifecycleObserver{
        private  Context context;
        private  CallBack callBack;
        MyLifeCycleObserver(Context context,CallBack callBack){
            this.context = context;
            this.callBack = callBack;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void start(){
            Log.e("LifeCycleObserverDemo","Activity started,do init things");
            callBack.start();
        }
        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void stop(){
            Log.e("LifeCycleObserverDemo","Activity stoped,do stop things");
            callBack.stop();
        }
    }

    private MyLifeCycleObserver myLifeCycleObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myLifeCycleObserver = new MyLifeCycleObserver(this, new CallBack() {
            @Override
            public void start() {
                Log.e("LifeCycleObserverDemo","Activity started,do init things end,update ui");
            }

            @Override
            public void stop() {
                Log.e("LifeCycleObserverDemo","Activity stoped,do stop things end,update ui");
            }
        });
        getLifecycle().addObserver(myLifeCycleObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(myLifeCycleObserver);
    }
}
