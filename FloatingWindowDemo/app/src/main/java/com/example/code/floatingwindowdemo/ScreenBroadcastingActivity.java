package com.dfwd.wlkt.screenbroadcasting;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.dfwd.wlkt.R;
import com.dfwd.wlkt.WLKTApplication;
import com.dfwd.wlkt.bean.SeeUserAnswerProtocol;
import com.dfwd.wlkt.common.base.BaseActivity;
import com.dfwd.wlkt.common.schedulers.SchedulerProvider;
import com.dfwd.wlkt.connection.ConnectionServerPresenter;
import com.dfwd.wlkt.connection.ProtocolParseScreenBroadcasting;
import com.dfwd.wlkt.connection.interfaces.OnScreenBroadcastingListener;
import com.dfwd.wlkt.data.Contans;
import com.dfwd.wlkt.data.UserInfo;
import com.dfwd.wlkt.database.bean.FileManageDataBean;
import com.dfwd.wlkt.model.FileManageModel;
import com.dfwd.wlkt.screenbroadcasting.interfaces.IScreenBroadcastingPresenter;
import com.dfwd.wlkt.screenbroadcasting.interfaces.IScreenBroadcastingView;
import com.dfwd.wlkt.screenprojection.helper.ScreenShotHelper;
import com.dfwd.wlkt.screenprojection.listener.ScreenShotCallBack;
import com.dfwd.wlkt.socket.Buffer;
import com.dfwd.wlkt.socket.Protocol;
import com.dfwd.wlkt.util.ABFileManager;
import com.dfwd.wlkt.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.UUID;

/**
 * 屏幕广播
 *
 * @author Michael_hj
 * @date 2017/10/13
 */
public class ScreenBroadcastingActivity extends BaseActivity implements
        OnScreenBroadcastingListener, IScreenBroadcastingView {

    public static final int DEFAULT_WIDTH = -1;
    public static final int DEFAULT_HEIGHT = -1;
    private static final int PERMISSION_REQUEST_ACTIVITY_RESULT_CODE = 10;
    public static final String KEY_FRAME_TYPE = "frame_type";
    public static final String KEY_DATA = "data";
    private MediaCodecWrapper mMediaCodecWrapper;
    private FrameLayout mFrameLayout;
    private IScreenBroadcastingPresenter mScreenBroadcastingPresenter;
    private ImageView mCutButton;
    private boolean isStop = false;
    private MediaProjectionManager mMediaProjectionManager;
    private int mWidth = -1;
    private int mHeight = -1;
    private ScreenBroadcastingReceiver mScreenBroadcastingReceiver;
    private boolean mNeedChangeSurfaceView = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.saveClassTestLog("onCreate");
        super.setAddTitleBar(false);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams
                .FLAG_FULLSCREEN);
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager
                .LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_screen_broadcasting);

        mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);

        initView();
        initData();
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.lay_content);
        mCutButton = (ImageView) findViewById(R.id.btn_cut);
        mCutButton.setOnClickListener(v -> {
            mCutButton.setVisibility(View.GONE);
            isStop = true;
            pauseBroadcast();
            startScreenshot();
        });

    }

    private void initData() {
        LogUtil.saveClassTestLog("initData");
        mScreenBroadcastingPresenter = new ScreenBroadcastingPresenter(this, UserInfo
                .getUserId(WLKTApplication.getInstance()), ScreenBroadcastingUtil.getInstance(), SchedulerProvider
                .getInstance(), new ScreenSnapshotModel(), new FileManageModel());
        mScreenBroadcastingPresenter.initProtocol(getIntent());
        mScreenBroadcastingPresenter.recoverySnapshotRecordAndActiveInterface();//恢复本地数据
        registerScreenBroadcasting(this);
    }

    private void pauseBroadcast() {
        LogUtil.saveClassTestLog("pauseBroadcast");
        ConnectionServerPresenter.getConnectionServerControl().setScreenBroadcastingListener(null);
    }

    private void startScreenshot() {
        if (WLKTApplication.getInstance().getMediaProjection() == null) {
            requestMediaProjectionPermission();
        } else {

            UUID fileGuid = UUID.randomUUID();
            String fileName = fileGuid.toString() + ".jpg";
            String path = ABFileManager.getFileDownloadDir() + File.separator;

            new ScreenShotHelper(this).takeScreenShot(WLKTApplication.getInstance().getMediaProjection(), false, new ScreenShotCallBack() {
                @Override
                public void screenShotSuccess(byte[] imageData) {

                }

                @Override
                public <T> void screenShotFailure(T msg) {
                    mCutButton.setVisibility(View.VISIBLE);
                    resumeBroadcast();
                    isStop = false;
                    Toast.makeText(ScreenBroadcastingActivity.this, msg.toString(), Toast.LENGTH_SHORT).show();
                    LogUtil.saveClassTestLog(" 截图失败 error:" + msg.toString());
                }

                @Override
                public void screenShotSuccess(String imagePath) {
                    FileManageDataBean fileManageData = new FileManageDataBean(fileGuid,
                            fileName, imagePath, false);
                    LogUtil.saveClassTestLog(" 截图保存成功 fileManageData:" + fileManageData);
                    UUID pushGuid = UUID.randomUUID();
                    LogUtil.saveClassTestLog(" pushGuid:" + pushGuid);
                    mScreenBroadcastingPresenter.createSnapshotRecordAndActiveInterface(pushGuid, fileManageData);
                }
            }, true, path, fileName);
        }
    }

    public void requestMediaProjectionPermission() {
        Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, PERMISSION_REQUEST_ACTIVITY_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                LogUtil.e("Permission Request Activity Result OK");
                MediaProjection mMediaProjection = WLKTApplication.getInstance().getMediaProjectionManager().getMediaProjection(resultCode, data);
                WLKTApplication.getInstance().setMediaProjection(mMediaProjection);
                startScreenshot();
            } else {
                LogUtil.e("Permission Request Activity Result Canceled");
                mCutButton.setVisibility(View.VISIBLE);
                resumeBroadcast();
                isStop = false;
                Toast.makeText(ScreenBroadcastingActivity.this, getResources().getString(R.string.str_permission_needed), Toast.LENGTH_SHORT).show();
                LogUtil.saveClassTestLog(" 截图失败 error:" + getResources().getString(R.string.str_permission_needed));
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.saveClassTestLog(" onNewIntent");
    }

    private SurfaceView mSurfaceView;

    @Override
    public void initSurfaceView(int width, int height) {
        LogUtil.saveClassTestLog(" initSurfaceView width:" + width + " height:" + height);

        mMediaCodecWrapper = new MediaCodecWrapper();

        if (!MediaCodecWrapper.supportHardwareDecoding()) {
            LogUtil.saveClassTestLog("设备不支持硬件解码");
            return;
        }

        Point resultSize = new Point();

        Point devSize = new Point();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealSize(devSize);

        if (isNavigationBarShow()) {
            devSize.y -= getNavigationBarHeight();
            LogUtil.saveClassTestLog("Navigation Bar Show");
        }

        LogUtil.saveClassTestLog("Width : " + devSize.x + "  Height: " + devSize.y);
        Log.e("TZB", "Width : " + devSize.x + "  Height: " + devSize.y);


        //判断推流下来的视频方向
        float scaleWidth = (float) devSize.x / width;
        float scaleHeight = (float) devSize.y / height;
        float zoomFactor = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;

        resultSize.x = (int) (width * zoomFactor);
        resultSize.y = (int) (height * zoomFactor);

        LogUtil.saveClassTestLog(" initSurfaceView resultSize.x:" + resultSize.x + " resultSize.y:" + resultSize.y);

//        Point resultSize = new Point();
//
//        LogUtil.saveClassTestLog(" initSurfaceView devSize.x:" + devSize.x + " devSize.y:" + devSize.y);
//
//        int layoutWidth = devSize.x;
//        int layoutHeight = devSize.y;
//        int calculateWidth = width * layoutHeight / height;//以布局实际高度为参考计算宽度
//        int calculateHeight = layoutWidth * height / width;//以布局实际宽度为参考计算高度
//        LogUtil.saveClassTestLog(" initSurfaceView calculateWidth:" + calculateWidth +
//                " calculateHeight:" + calculateHeight);
//
//        if (calculateHeight > layoutHeight) {
//            resultSize.x = calculateWidth;
//            resultSize.y = layoutHeight;
//        } else {
//            resultSize.x = layoutWidth;
//            resultSize.y = calculateHeight;
//        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(resultSize.x, resultSize.y, Gravity.CENTER);
        mSurfaceView = null;
        mFrameLayout.removeAllViews();
        mSurfaceView = new SurfaceView(this);
        mFrameLayout.addView(mSurfaceView, layoutParams);

        this.mWidth = width;
        this.mHeight = height;
        mCutButton.setVisibility(View.VISIBLE);
        mMediaCodecWrapper.configureMediaCodec(width, height, mSurfaceView);
    }

    public boolean isNavigationBarShow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(this).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    public int getNavigationBarHeight() {
        if (!isNavigationBarShow()) {
            return 0;
        }
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    @Override
    public boolean isActivityFinishing() {
        return isFinishing();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void startScreenBroadcastingShotActivity(String classRecordGuid, String streamChannelGuid, String pushGuid,
                                                    int width, int height, FileManageDataBean fileManageData) {
        Intent intent = new Intent(ScreenBroadcastingActivity.this,
                ScreenBroadcastingShotActivity.class);
        intent.putExtra(ProtocolParseScreenBroadcasting.SCREEN_BROADCASTING_FILE_DATA, fileManageData);
        intent.putExtra(ProtocolParseScreenBroadcasting.SCREEN_BROADCASTING_RECORD_GUID, classRecordGuid);
        intent.putExtra(ProtocolParseScreenBroadcasting.SCREEN_BROADCASTING_STREAM_CHANNEL_GUID, streamChannelGuid);
        intent.putExtra(ProtocolParseScreenBroadcasting.SCREEN_BROADCASTING_PUSH_GUID, pushGuid);
        intent.putExtra(ProtocolParseScreenBroadcasting.SCREEN_BROADCASTING_WIDTH, width);
        intent.putExtra(ProtocolParseScreenBroadcasting.SCREEN_BROADCASTING_HEIGHT, height);
        startActivity(intent);
        finishScreenBroadcast();
    }

    private void finishScreenBroadcast() {
        isStop = true;
        pauseBroadcast();
        mMediaCodecWrapper = null;
        finish();
    }

    @Override
    protected void onPause() {
        pauseBroadcast();
        super.onPause();
    }

    @Override
    protected void onResume() {
        isInputIFrame = false;
        resumeBroadcast();
        super.onResume();
    }

    private void resumeBroadcast() {
        LogUtil.saveClassTestLog("resumeBroadcast");
        ConnectionServerPresenter.getConnectionServerControl().setScreenBroadcastingListener(this);
    }

    @Override
    protected void onDestroy() {
        mMediaCodecWrapper = null;
        unregisterScreenBroadcasting(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;//屏蔽返回键
    }

    private boolean isInputIFrame = false;

    @Override
    public void onScreenBroadcasting(Protocol protocol, SeeUserAnswerProtocol userAnswerProtocol) {
        if (isStop) {
            return;
        }
        if (mMediaCodecWrapper == null) {
            return;
        }

        int width = userAnswerProtocol.getData().getWidth();
        int height = userAnswerProtocol.getData().getHeight();


         //type == 1 表示是关键帧，
        int type = 0;
        try {
            JSONObject jsonObject = protocol.json().getJSONObject(KEY_DATA);
            if (jsonObject.has(KEY_FRAME_TYPE)) {
                type = jsonObject.getInt(KEY_FRAME_TYPE);
            } else {//兼容旧版本，新的Android搭配旧的Windows,
                type = 1;
            }
        } catch (JSONException e) {
            LogUtil.saveClassTestLog("ScreenBroadcastingActivity Can not find Json Key data: "+e.getMessage());
            e.printStackTrace();
            return;
        }

        if (mWidth == DEFAULT_WIDTH || mHeight == DEFAULT_HEIGHT) {
            return;
        }

        if (width != mWidth || height != mHeight || mNeedChangeSurfaceView) {
            isInputIFrame = false;
            mNeedChangeSurfaceView = false;
            initSurfaceView(width, height);
        }

        try {
            Buffer buffer = protocol.GetBuffer(userAnswerProtocol.getData().getDataBufferId());
            byte[] h264RawData = buffer.data();
            //保证MediaCodec拿到的第一帧是关键帧，丢弃非关键帧
            if (type == 1 && !isInputIFrame) {
                isInputIFrame = true;
            }

            if (isInputIFrame) {
                mMediaCodecWrapper.inputH264RawFrame(h264RawData);
            }
        } catch (Exception e) {
            LogUtil.saveClassTestLog(Log.getStackTraceString(e));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mNeedChangeSurfaceView = true;
        LogUtil.saveClassTestHaveTagLog(" onConfigurationChanged newConfig:" + newConfig);
    }

    /**
     * 注册推屏关闭广播
     */
    private void registerScreenBroadcasting(Context context) {
        LogUtil.saveClassTestHaveTagLog(" 注册屏幕广播关闭广播");
        mScreenBroadcastingReceiver = new ScreenBroadcastingReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ProtocolParseScreenBroadcasting.SCREEN_BROADCASTING_ACTION);
        intentFilter.addAction(Contans.CLASS_TEST_ACTION);
        context.registerReceiver(mScreenBroadcastingReceiver, intentFilter);
        LogUtil.saveClassTestHaveTagLog(" 注册屏幕广播关闭广播 ScreenBroadcastingReceiver.hashCode:" +
                mScreenBroadcastingReceiver);
    }

    /**
     * 解除推屏关闭广播
     */
    private void unregisterScreenBroadcasting(Context context) {
        LogUtil.saveClassTestHaveTagLog(" 解除屏幕广播关闭广播 ScreenBroadcastingReceiver.hashCode:" +
                (mScreenBroadcastingReceiver == null ? "null" : mScreenBroadcastingReceiver.hashCode()));
        if (mScreenBroadcastingReceiver == null) {
            return;
        }
        LogUtil.saveClassTestHaveTagLog(" 解除屏幕广播关闭广播");
        context.unregisterReceiver(mScreenBroadcastingReceiver);
        mScreenBroadcastingReceiver = null;
    }

    /**
     * 推屏界面关闭广播
     */
    public class ScreenBroadcastingReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mScreenBroadcastingPresenter != null && !isFinishing()) {
                mScreenBroadcastingPresenter.onReceive(context, intent);
            }
        }
    }
}
