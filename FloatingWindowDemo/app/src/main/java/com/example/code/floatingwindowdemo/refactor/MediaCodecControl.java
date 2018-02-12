package com.dfwd.wlkt.screenbroadcasting.refactor;

import android.media.MediaFormat;
import android.view.Surface;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/8
 */
public interface MediaCodecControl {
    /**
     * MediaCodec init method
     *
     * @param surface     MediaCodec output surface
     * @param mediaFormat Can be used to configure MediaCodec
     */
    void init(Surface surface, MediaFormat mediaFormat);

    /**
     * MediaCodec init method
     *
     * @param surface MediaCodec output surface
     * @param mime    The mime type of content
     * @param width   content width
     * @param height  content height
     */
    void init(Surface surface, String mime, int width, int height,int frameRate);

    void start();

    boolean inputH264RawFrame(byte[] rawFrame);

    void stop();

    void release();
}