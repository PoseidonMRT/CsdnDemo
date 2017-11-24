package com.example.code.archicomponentssmaples;

import android.arch.lifecycle.ViewModel;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/11/24
 */
public class ClickCounterViewModal extends ViewModel {
    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
