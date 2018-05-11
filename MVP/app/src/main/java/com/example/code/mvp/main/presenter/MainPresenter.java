package com.example.code.mvp.main.presenter;

import android.support.annotation.VisibleForTesting;

import com.example.code.mvp.main.model.interfaces.IMainModel;
import com.example.code.mvp.main.presenter.interfaces.IMainPresenter;
import com.example.code.mvp.main.view.interfaces.IMainView;
import com.example.code.mvp.main.model.MainModel;

import java.util.List;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/5/11
 */
public class MainPresenter implements IMainPresenter {
    private IMainModel mMainModel;
    private IMainView mMainView;
    private List<String> mSpinnerData;

    public MainPresenter(IMainView mainView) {
        mMainModel = new MainModel();
        mMainView = mainView;
    }

    @VisibleForTesting
    public MainPresenter(IMainView mainView,IMainModel mainModel){
        mMainView = mainView;
        mMainModel = mainModel;
    }

    @Override
    public void loadSpinnerData() {
        mSpinnerData = mMainModel.loadSpinnerData();
        if (mSpinnerData == null || mSpinnerData.size() == 0){
            mMainView.showErrorMsg();
            return;
        }
        mMainView.updateSpinnerData(mSpinnerData);
    }

    @Override
    public void loadListData(int position) {
        String type = mSpinnerData.get(position);
        List<String> listData = mMainModel.loadListData(type);
        if (listData == null || listData.size() == 0){
            mMainView.showErrorMsg();
            return;
        }
        mMainView.updateListData(listData);
    }
}
