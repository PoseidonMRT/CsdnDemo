package com.example.code.mvp.main.presenter.interfaces;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/5/11
 */
public interface IMainPresenter {

    /**
     * load data that can be displayed in the spinner
     */
    void loadSpinnerData();

    /**
     * load data that can be displayed in ListView
     * @param position user selected position in the spinner
     */
    void loadListData(int position);
}
