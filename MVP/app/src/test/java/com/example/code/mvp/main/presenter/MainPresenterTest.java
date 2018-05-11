package com.example.code.mvp.main.presenter;

import com.example.code.mvp.main.model.MainModel;
import com.example.code.mvp.main.model.interfaces.IMainModel;
import com.example.code.mvp.main.presenter.interfaces.IMainPresenter;
import com.example.code.mvp.main.view.MainActivity;
import com.example.code.mvp.main.view.interfaces.IMainView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/5/11
 */
public class MainPresenterTest {
    private IMainModel mMainModel;
    private IMainView mMainView;
    private IMainPresenter mMainPresenter;
    @Before
    public void setUp() throws Exception {
        mMainModel = Mockito.mock(MainModel.class);
        mMainView = Mockito.mock(MainActivity.class);
        mMainPresenter = new MainPresenter(mMainView,mMainModel);
    }

    @Test
    public void loadSpinnerDataWhenDataEmpty() throws Exception {
        mMainPresenter.loadSpinnerData();
        Mockito.verify(mMainModel).loadSpinnerData();
        List<String> data = new ArrayList<>();
        Mockito.when(mMainModel.loadSpinnerData()).thenReturn(data);
        Mockito.verify(mMainView).showErrorMsg();
    }

    @Test
    public void loadSpinnerDataWhenDataNull() throws Exception {
        mMainPresenter.loadSpinnerData();
        Mockito.verify(mMainModel).loadSpinnerData();
        Mockito.when(mMainModel.loadSpinnerData()).thenReturn(null);
        Mockito.verify(mMainView).showErrorMsg();
    }
}