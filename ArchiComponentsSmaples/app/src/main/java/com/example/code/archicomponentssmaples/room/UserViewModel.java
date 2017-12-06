package com.example.code.archicomponentssmaples.room;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

/**
 * Created by tuozhaobing on 17/12/6.
 */

public class UserViewModel extends ViewModel {

  private MutableLiveData<Integer> mUserCounts;
  MutableLiveData<Integer> getUserCounts(){
    if (mUserCounts == null){
      mUserCounts = new MutableLiveData<>();
    }
    return mUserCounts;
  }

  void putUsers(User user,Context context){
    AppDatabase _appDatabase = AppDatabase.getInMemoryDatabase(context);
    _appDatabase.userModal().insertUser(user);
    if (mUserCounts != null){
      mUserCounts.postValue(_appDatabase.userModal().loadAllUsers().size());
    }
  }

}
