package com.example.code.archicomponentssmaples;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/11/27
 */
public class UserNameViewModal extends ViewModel {

    private MutableLiveData<String> mUserName;
    public MutableLiveData<String> getCurrentUserName(){
        if (mUserName == null) {
            mUserName = new MutableLiveData<String>();
        }
        return mUserName;

    }

}
