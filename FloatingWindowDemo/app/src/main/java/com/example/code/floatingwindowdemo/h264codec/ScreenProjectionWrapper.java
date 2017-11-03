package com.example.code.floatingwindowdemo.h264codec;

import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.projection.MediaProjection;
import android.os.Bundle;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScreenProjectionWrapper extends Thread {
    private static final String TAG = "ScreenPusher";
    private static final String MIME_TYPE = "video/avc"; // H.264 Advanced Video Coding
    private static final int FRAME_RATE = 15; // 30 fps
    private static final int IFRAME_INTERVAL = 250; // 10 seconds between I-frames
    private static final int TIMEOUT_US = 10000;
    private int mWidth;
    private int mHeight;
    private int mBitRate;
    private int mDpi;
    private MediaProjection mMediaProjection;
    private MediaCodec mEncoder;
    private EncoderStatus encoderStatus = EncoderStatus.kUnknown;
    private Surface mSurface;
    private AtomicBoolean mQuit = new AtomicBoolean(false);
    private MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();
    private VirtualDisplay mVirtualDisplay;
    private byte[] frameSPSFPS = null;
    private ScreenProjectionListerner screenProjectionListerner;
    public EncoderStatus getEncoderStatus() {
        return encoderStatus;
    }
    public void setEncoderStatus(EncoderStatus encoderStatus) {
        this.encoderStatus = encoderStatus;
    }
    public ScreenProjectionWrapper(int width, int height, int bitrate, int dpi, MediaProjection mp) {
        super(TAG);
        mWidth = width;
        mHeight = height;
        mBitRate = bitrate;
        mDpi = dpi;
        mMediaProjection = mp;
    }
    public ScreenProjectionWrapper(MediaProjection mp) {
        // 480p 2Mbps
        this(640, 480, 2000000, 1, mp);
    }
    public void setScreenProjectionListerner(ScreenProjectionListerner screenProjectionListerner) {
        this.screenProjectionListerner = screenProjectionListerner;
    }

    public final void quit() {
        mQuit.set(true);
    }

    @Override
    public void run() {
        try {
            try {
                prepareEncoder();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mVirtualDisplay = mMediaProjection.createVirtualDisplay(TAG + "-display",
                    mWidth, mHeight, mDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
                    mSurface, null, null);
            recordVirtualDisplay();

        } finally {
            release();
        }
    }

    private void recordVirtualDisplay() {
        while (!mQuit.get()) {
            int index = mEncoder.dequeueOutputBuffer(mBufferInfo, TIMEOUT_US);
            if (index == MediaCodec.INFO_TRY_AGAIN_LATER) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (index >= 0) {
                encodeToVideoTrack(index);
                mEncoder.releaseOutputBuffer(index, false);
            }
        }
        encoderStatus = EncoderStatus.kStopped;
    }

    private void encodeToVideoTrack(int index) {
        ByteBuffer encodedData = mEncoder.getOutputBuffer(index);
        if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
            encodedData.position(mBufferInfo.offset);
            encodedData.limit(mBufferInfo.offset + mBufferInfo.size);
            frameSPSFPS = new byte[mBufferInfo.size];
            encodedData.get(frameSPSFPS);
            mBufferInfo.size = 0;
        }
        if (mBufferInfo.size == 0) {
            encodedData = null;
        }
        if (encodedData != null) {
            encodedData.position(mBufferInfo.offset);
            encodedData.limit(mBufferInfo.offset + mBufferInfo.size);
            byte[] outData = new byte[mBufferInfo.size];
            encodedData.get(outData);
            //if it's the key frame , you must add sps & pps infront of your frame.
            byte[] h264RawFrame = null;
            if (mBufferInfo.flags == MediaCodec.BUFFER_FLAG_KEY_FRAME) {
                h264RawFrame = new byte[frameSPSFPS.length + outData.length];
                System.arraycopy(frameSPSFPS, 0, h264RawFrame, 0, frameSPSFPS.length);
                System.arraycopy(outData, 0, h264RawFrame, frameSPSFPS.length, outData.length);
            } else {
                h264RawFrame = outData;
            }
            this.screenProjectionListerner.onEncodingH264RawCompeleted(h264RawFrame);
        }
    }

    private void prepareEncoder() throws IOException {
        MediaFormat format = MediaFormat.createVideoFormat(MIME_TYPE, mWidth, mHeight);
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, mBitRate);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, IFRAME_INTERVAL);
        mEncoder = MediaCodec.createEncoderByType(MIME_TYPE);
        mEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mSurface = mEncoder.createInputSurface();
        encoderStatus = EncoderStatus.kStarted;
        mEncoder.start();
    }

    private void release() {
        if (mEncoder != null) {
            mEncoder.stop();
            mEncoder.release();
            mEncoder = null;
        }
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
        }
    }

    public void RuquestIFrame() {
        if (mEncoder != null) {
//            mEncoder.flush();
            Bundle params = new Bundle();
            params.putInt(MediaCodec.PARAMETER_KEY_REQUEST_SYNC_FRAME, 0);
            mEncoder.setParameters(params);
        }
    }
}
