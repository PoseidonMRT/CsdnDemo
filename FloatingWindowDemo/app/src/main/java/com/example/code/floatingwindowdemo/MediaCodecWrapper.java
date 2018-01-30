package com.dfwd.wlkt.screenbroadcasting;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dfwd.wlkt.util.LogUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 屏幕广播视频流数据处理
 */
class MediaCodecWrapper {
    private static final int TIME_OUT = 0;
    private static final byte[] NULL_BYTE_ARRAY = new byte[]{0x00, 0x00, 0x01, 0x20};
    private SurfaceHolder mSurfaceHolder;
    private MediaCodec mMediaCodec;
    private BlockingQueue<byte[]> mH264RawQueue;
    private volatile boolean mNeedExit = false;
    private Thread mThread;

    @SuppressWarnings("deprecation")
    static boolean supportHardwareDecoding() {
        for (int i = 0; i < MediaCodecList.getCodecCount(); i++) {
            MediaCodecInfo mediaCodecInfo = MediaCodecList.getCodecInfoAt(i);
            if (mediaCodecInfo.isEncoder()) {
                continue;
            }
            String[] types = mediaCodecInfo.getSupportedTypes();
            for (String typesItem : types) {
                if ("video/avc".equals(typesItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    void configureMediaCodec(final int width, final int height, SurfaceView surfaceview) {
        LogUtil.saveClassTestLog(" configureMediaCodec start");
        mSurfaceHolder = surfaceview.getHolder();
        mH264RawQueue = new LinkedBlockingDeque<>();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                LogUtil.saveClassTestLog(" surfaceCreated start");
                //不管Surface处于什么情形都会回调surfaceChanged,所以在SurfaceCreated中的init操作无意义
//                initMediaCodec(holder, width, height);
                LogUtil.saveClassTestLog(" surfaceCreated end");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                LogUtil.saveClassTestLog(" surfaceChanged start holder:" + holder +
                        " format:" + format + " width:" + width + " height:" + height);
                releaseMediaCodec();
                initMediaCodec(holder, width, height);
                LogUtil.saveClassTestLog(" surfaceChanged end");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                LogUtil.saveClassTestLog(" surfaceDestroyed start");
                releaseMediaCodec();
                LogUtil.saveClassTestLog(" surfaceDestroyed end");
            }
        });
        LogUtil.saveClassTestLog(" configureMediaCodec end");
    }

    private void initMediaCodec(SurfaceHolder holder, int width, int height) {
        try {
            mMediaCodec = MediaCodec.createDecoderByType("video/avc");
        } catch (IOException e) {
            LogUtil.saveClassTestLog(Log.getStackTraceString(e));
        }
        final MediaFormat mediaformat = MediaFormat.createVideoFormat("video/avc", width, height);
        mediaformat.setInteger(MediaFormat.KEY_FRAME_RATE, 15);
        mMediaCodec.configure(mediaformat, holder.getSurface(), null, 0);
        startDecodingThread();
    }

    private void releaseMediaCodec() {
        if (mMediaCodec == null){
            return;
        }

        LogUtil.saveClassTestLog(" release start");
        inputH264RawFrame(NULL_BYTE_ARRAY);
        mNeedExit = true;
        try {
            mThread.join();
            mMediaCodec.stop();
            mMediaCodec.release();
        } catch (Exception e) {
            LogUtil.saveClassTestLog(Log.getStackTraceString(e));
        } finally {
            LogUtil.saveClassTestLog(" release end");
        }
        //当SurfaceChange时，清空数据队列，重新接收数据[典型情况，按Home键黑屏]
        if (mH264RawQueue != null){
            mH264RawQueue.clear();
        }

    }

    private void startDecodingThread() {
          //由于SurfaceView的CallBack是异步回调，此时执行Queue.clear时，Queue里面已经存在需要解码的关键帧数据，导致应用奔溃
//        if (mH264RawQueue != null){
//            mH264RawQueue.clear();
//        }
        mNeedExit = false;
        mMediaCodec.start();
        mThread = new Thread(() -> {
            long frameCount = 0;
            while (!mNeedExit) {

                // 将数据放入底层（mMediaCodec）队列
                if (mH264RawQueue == null || mH264RawQueue.size() == 0){
                    //防止H264 Queue中数据为空，导致dequeueInputBuffer异常
                    LogUtil.saveClassTestLog("H264 Raw Queue is null or empty!");
                    continue;
                }
                int inputBufferIndex = mMediaCodec.dequeueInputBuffer(TIME_OUT);
                if (inputBufferIndex >= 0) {
                    ByteBuffer inputBuffer = mMediaCodec.getInputBuffer(inputBufferIndex);
                    if (null == inputBuffer) {
                        continue;
                    }

                    byte[] h264Frame = null;

                    try {
                        if (mH264RawQueue != null) {
                            h264Frame = mH264RawQueue.take();
                        }
                    } catch (InterruptedException e) {
                        LogUtil.saveClassTestLog(Log.getStackTraceString(e));
                    }

                    if (h264Frame == null) {
                        continue;
                    }

                    if (Arrays.equals(NULL_BYTE_ARRAY, h264Frame)) {
                        mH264RawQueue.clear();
                        LogUtil.saveClassTestLog(" 中断视频接收循环，结束线程");
                        break;
                    }

                    inputBuffer.clear();
                    inputBuffer.put(h264Frame, 0, h264Frame.length);
                    mMediaCodec.queueInputBuffer(inputBufferIndex, 0, h264Frame.length,
                            frameCount * 15, MediaCodec.BUFFER_FLAG_KEY_FRAME);

                    frameCount++;
                }

                // 将底层（mMediaCodec）上面的数据渲染至界面，应该独立。
                MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                int outputBufferIndex = mMediaCodec.dequeueOutputBuffer(bufferInfo, 0);
                while (outputBufferIndex >= 0) {
                    if (mSurfaceHolder.getSurface().isValid()) {
                        mMediaCodec.releaseOutputBuffer(outputBufferIndex, true);
                        outputBufferIndex = mMediaCodec.dequeueOutputBuffer(bufferInfo, 0);
                    }
                }
            }

        });
        mThread.start();
    }

    void inputH264RawFrame(byte[] frameBuffer) {
        if (mNeedExit) {
            return;
        }
        byte[] tempFrame = frameBuffer.clone();
        try {
            mH264RawQueue.put(tempFrame);
        } catch (InterruptedException e) {
            LogUtil.saveClassTestLog(Log.getStackTraceString(e));
        }
    }
}
