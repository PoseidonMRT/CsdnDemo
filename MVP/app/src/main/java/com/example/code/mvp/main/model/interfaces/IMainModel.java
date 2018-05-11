package com.example.code.mvp.main.model.interfaces;

import java.util.List;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/5/11
 */
public interface IMainModel {

    /**
     * load spinner data
     * @return content list of spinner data
     */
    List<String> loadSpinnerData();

    /**
     * load list data
     * @param type which kind of content
     * @return content list of ListView
     */
    List<String> loadListData(String type);
}
