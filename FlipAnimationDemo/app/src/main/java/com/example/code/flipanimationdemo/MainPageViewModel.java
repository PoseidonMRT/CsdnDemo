package com.example.code.flipanimationdemo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/12/19
 */
public class MainPageViewModel extends ViewModel {
    private MutableLiveData<Boolean> mIsShowingBack;
    public MutableLiveData<Boolean> isShowingBack(){
        if (mIsShowingBack == null){
            mIsShowingBack = new MutableLiveData<>();
        }
        return mIsShowingBack;
    }
}
