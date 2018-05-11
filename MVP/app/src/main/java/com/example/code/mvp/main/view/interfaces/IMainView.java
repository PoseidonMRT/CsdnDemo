package com.example.code.mvp.main.view.interfaces;

import java.util.List;

/**
 * Main Page View Interface
 *
 * @author tuozhaobing
 * @date 2018/5/11
 */
public interface IMainView {
    /**
     * show error msg when load data error
     */
    void showErrorMsg();

    /**
     * update Spinner data when load finished
     * @param data data that can be displayed in Spinner
     */
    void updateSpinnerData(List<String> data);

    /**
     * update ListView data when load finished
     * @param data data that can be displayed in ListView
     */
    void updateListData(List<String> data);
}
