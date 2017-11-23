package com.study.mcvideoplayer;

import android.animation.TimeAnimator;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

  private TextureView mTextureView;
  private MediaCodec mMediaCodec;
  private TimeAnimator mTimeAnimator = new TimeAnimator();
  private MediaExtractor mMediaExtractor = new MediaExtractor();

  private Queue<Integer> mAvailableInputBuffers;

  private Queue<Integer> mAvailableOutputBuffers;

  private MediaCodec.BufferInfo[] mOutputBufferInfo;

  private ByteBuffer[] mInputBuffers;
  private ByteBuffer[] mOutputBuffers;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    initData();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.action_menu, menu);
    return true;
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mTimeAnimator != null && mTimeAnimator.isRunning()) {
      mTimeAnimator.end();
    }


  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_play) {
      startPlayback();
      item.setEnabled(false);
    }
    return true;
  }


  public void initView() {
    mTextureView = (TextureView) findViewById(R.id.PlaybackView);

  }

  public void initData() {

  }

  public boolean writeSample(final ByteBuffer input,
      final MediaCodec.CryptoInfo crypto,
      final long presentationTimeUs,
      final int flags) throws MediaCodec.CryptoException, WriteException {
    boolean result = false;
    int size = input.remaining();

    // check if we have dequed input buffers available from the codec
    if (size > 0 && !mAvailableInputBuffers.isEmpty()) {
      int index = mAvailableInputBuffers.remove();
      ByteBuffer buffer = mInputBuffers[index];

      // we can't write our sample to a lesser capacity input buffer.
      if (size > buffer.capacity()) {
        throw new WriteException(String.format(
            "Insufficient capacity in MediaCodec buffer: "
                + "tried to write %d, buffer capacity is %d.",
            input.remaining(),
            buffer.capacity()));
      }

      buffer.clear();
      buffer.put(input);

      // Submit the buffer to the codec for decoding. The presentationTimeUs
      // indicates the position (play time) for the current sample.
      if (crypto == null) {
        mMediaCodec.queueInputBuffer(index, 0, size, presentationTimeUs, flags);
      } else {
        mMediaCodec.queueSecureInputBuffer(index, 0, crypto, presentationTimeUs, flags);
      }
      result = true;
    }
    return result;
  }

  static MediaCodec.CryptoInfo cryptoInfo = new MediaCodec.CryptoInfo();

  /**
   * Write a media sample to the decoder.
   *
   * A "sample" here refers to a single atomic access unit in the media stream. The definition
   * of "access unit" is dependent on the type of encoding used, but it typically refers to
   * a single frame of video or a few seconds of audio. {@link android.media.MediaExtractor}
   * extracts data from a stream one sample at a time.
   *
   * @param extractor Instance of {@link android.media.MediaExtractor} wrapping the media.
   * @param presentationTimeUs The time, relative to the beginning of the media stream, at which
   * this buffer should be rendered.
   * @param flags Flags to pass to the decoder. See {@link MediaCodec#queueInputBuffer(int, int,
   * int, long, int)}
   */
  public boolean writeSample(final MediaExtractor extractor,
      final boolean isSecure,
      final long presentationTimeUs,
      int flags) {
    boolean result = false;
    boolean isEos = false;

    if (!mAvailableInputBuffers.isEmpty()) {
      int index = mAvailableInputBuffers.remove();
      ByteBuffer buffer = mInputBuffers[index];

      // reads the sample from the file using extractor into the buffer
      int size = extractor.readSampleData(buffer, 0);
      if (size <= 0) {
        flags |= MediaCodec.BUFFER_FLAG_END_OF_STREAM;
      }

      // Submit the buffer to the codec for decoding. The presentationTimeUs
      // indicates the position (play time) for the current sample.
      if (!isSecure) {
        mMediaCodec.queueInputBuffer(index, 0, size, presentationTimeUs, flags);
      } else {
        extractor.getSampleCryptoInfo(cryptoInfo);
        mMediaCodec.queueSecureInputBuffer(index, 0, cryptoInfo, presentationTimeUs, flags);
      }

      result = true;
    }
    return result;
  }

  /**
   * Performs a peek() operation in the queue to extract media info for the buffer ready to be
   * released i.e. the head element of the queue.
   *
   * @param out_bufferInfo An output var to hold the buffer info.
   * @return True, if the peek was successful.
   */
  public boolean peekSample(MediaCodec.BufferInfo out_bufferInfo) {
    // dequeue available buffers and synchronize our data structures with the codec.
    update();
    boolean result = false;
    if (!mAvailableOutputBuffers.isEmpty()) {
      int index = mAvailableOutputBuffers.peek();
      MediaCodec.BufferInfo info = mOutputBufferInfo[index];
      // metadata of the sample
      out_bufferInfo.set(
          info.offset,
          info.size,
          info.presentationTimeUs,
          info.flags);
      result = true;
    }
    return result;
  }

  public void popSample(boolean render) {
    // dequeue available buffers and synchronize our data structures with the codec.
    update();
    if (!mAvailableOutputBuffers.isEmpty()) {
      int index = mAvailableOutputBuffers.remove();

      // releases the buffer back to the codec
      mMediaCodec.releaseOutputBuffer(index, render);
    }
  }


  /**
   * Synchronize this object's state with the internal state of the wrapped
   * MediaCodec.
   */
  private void update() {
    int index;

    if (mMediaCodec == null){
      return;
    }
    // Get valid input buffers from the codec to fill later in the same order they were
    // made available by the codec.
    while ((index = mMediaCodec.dequeueInputBuffer(0)) != MediaCodec.INFO_TRY_AGAIN_LATER) {
      mAvailableInputBuffers.add(index);
    }

    // Likewise with output buffers. If the output buffers have changed, start using the
    // new set of output buffers. If the output format has changed, notify listeners.
    MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
    while ((index = mMediaCodec.dequeueOutputBuffer(info, 0)) != MediaCodec.INFO_TRY_AGAIN_LATER) {
      switch (index) {
        case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
          mOutputBuffers = mMediaCodec.getOutputBuffers();
          mOutputBufferInfo = new MediaCodec.BufferInfo[mOutputBuffers.length];
          mAvailableOutputBuffers.clear();
          break;
        case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:

          break;
        default:
          // Making sure the index is valid before adding to output buffers. We've already
          // handled INFO_TRY_AGAIN_LATER, INFO_OUTPUT_FORMAT_CHANGED &
          // INFO_OUTPUT_BUFFERS_CHANGED i.e all the other possible return codes but
          // asserting index value anyways for future-proofing the code.
          if (index >= 0) {
            mOutputBufferInfo[index] = info;
            mAvailableOutputBuffers.add(index);
          } else {
            throw new IllegalStateException("Unknown status from dequeueOutputBuffer");
          }
          break;
      }

    }
    // END_INCLUDE(update_codec_state)

  }

  public void startPlayback() {
    // Construct a URI that points to the video resource that we want to play
    Uri videoUri = Uri.parse("android.resource://"
        + getPackageName() + "/"
        + R.raw.vid_bigbuckbunny);

    try {

      mMediaExtractor.setDataSource(this, videoUri, null);
      int nTracks = mMediaExtractor.getTrackCount();

      for (int i = 0; i < nTracks; ++i) {
        mMediaExtractor.unselectTrack(i);
      }

      for (int i = 0; i < nTracks; ++i) {

        MediaFormat trackFormat = mMediaExtractor.getTrackFormat(i);
        final String mimeType = trackFormat.getString(MediaFormat.KEY_MIME);

        // Check to see if this is actually a video mime type. If it is, then create
        // a codec that can decode this mime type.
        if (mimeType.contains("video/")) {
          mMediaCodec = MediaCodec.createDecoderByType(mimeType);
          mMediaCodec
              .configure(trackFormat, new Surface(mTextureView.getSurfaceTexture()), null, 0);
          mMediaCodec.start();
          mInputBuffers = mMediaCodec.getInputBuffers();
          mOutputBuffers = mMediaCodec.getOutputBuffers();
          mOutputBufferInfo = new MediaCodec.BufferInfo[mOutputBuffers.length];
          mAvailableInputBuffers = new ArrayDeque<Integer>(mOutputBuffers.length);
          mAvailableOutputBuffers = new ArrayDeque<Integer>(mInputBuffers.length);
        }

        if (mMediaCodec != null) {
          mMediaExtractor.selectTrack(i);
        }

        mTimeAnimator.setTimeListener(new TimeAnimator.TimeListener() {
          @Override
          public void onTimeUpdate(final TimeAnimator animation,
              final long totalTime,
              final long deltaTime) {

            boolean isEos = ((mMediaExtractor.getSampleFlags() & MediaCodec.BUFFER_FLAG_END_OF_STREAM) == MediaCodec.BUFFER_FLAG_END_OF_STREAM);

            if (!isEos) {

              boolean result = writeSample(mMediaExtractor, false,
                  mMediaExtractor.getSampleTime(), mMediaExtractor.getSampleFlags());

              if (result) {
                // Advancing the extractor is a blocking operation and it MUST be
                // executed outside the main thread in real applications.
                mMediaExtractor.advance();
              }
            }
            // END_INCLUDE(write_sample)

            // Examine the sample at the head of the queue to see if its ready to be
            // rendered and is not zero sized End-of-Stream record.
            MediaCodec.BufferInfo out_bufferInfo = new MediaCodec.BufferInfo();
            peekSample(out_bufferInfo);

            // BEGIN_INCLUDE(render_sample)
            if (out_bufferInfo.size <= 0 && isEos) {
              mTimeAnimator.end();
              mMediaCodec.stop();
              mMediaCodec.release();
              mMediaCodec = null;
              mMediaExtractor.release();
            } else if (out_bufferInfo.presentationTimeUs / 1000 < totalTime) {
              // Pop the sample off the queue and send it to {@link Surface}
              popSample(true);
            }
            // END_INCLUDE(render_sample)

          }
        });
        mTimeAnimator.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private class WriteException extends Throwable {

    private WriteException(final String detailMessage) {
      super(detailMessage);
    }
  }
}
