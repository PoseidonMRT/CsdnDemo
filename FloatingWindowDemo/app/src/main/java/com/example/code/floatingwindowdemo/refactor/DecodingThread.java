package com.dfwd.wlkt.screenbroadcasting.refactor;

import android.media.MediaCodec;
import android.util.Log;

import java.nio.ByteBuffer;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/8
 */
public class DecodingThread {

    private MediaCodec mMediaCodec;
    private int frameStampIndex = 0;
    private int TIME_OUT = 0;
    private int mFrameRate = 15;

    public DecodingThread(MediaCodec mediaCodec, int frameRate) {
        super();
        mMediaCodec = mediaCodec;
        mFrameRate = frameRate;
    }

    public boolean decodeH264RawFrame(final byte[] h264RawFrame) {
        if (!inputH264RawFrameIntoQueue(h264RawFrame)) {
            return false;
        }
        if (!outputYUV420FrameFromQueue()) {
            return false;
        }
        return true;
    }

    public boolean inputH264RawFrameIntoQueue(final byte[] h264RawFrame) {
        int inputBufferIndex = mMediaCodec.dequeueInputBuffer(TIME_OUT);
        boolean execResult = (inputBufferIndex > 0);
        if (execResult) {
            ByteBuffer inputBuffer = mMediaCodec.getInputBuffer(inputBufferIndex);
            inputBuffer.clear();
            inputBuffer.put(h264RawFrame, 0, h264RawFrame.length);
            mMediaCodec.queueInputBuffer(inputBufferIndex, 0, h264RawFrame.length,
                    frameStampIndex * mFrameRate, MediaCodec.BUFFER_FLAG_KEY_FRAME);
            frameStampIndex++;
        }
        return execResult;
    }

    public boolean outputYUV420FrameFromQueue() {
        boolean execResult = true;
        while (true) {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int outputBufferIndex = mMediaCodec.dequeueOutputBuffer(bufferInfo, 0);
            if (outputBufferIndex <= 0) {
                break;
            }
            if (bufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
                //execResult = false;
                Log.e("DecodingThread", "receive a eos frame!");
                break;
            }
            mMediaCodec.releaseOutputBuffer(outputBufferIndex, true);
        }
        return execResult;
    }
}
