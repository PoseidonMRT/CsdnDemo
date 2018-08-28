package com.example.code.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

/**
 * Created by chintan on 23-04-2017.
 */

public class CustomEditText extends EditText {

  //The image we are going to use for the Clear button

  private Drawable imgCloseButton /*= getResources().getDrawable(android.R.drawable.btn_plus, null)*/;
  private Context context;
  private AttributeSet attributeSet;

  public CustomEditText(Context context) {
    super(context);
    this.context = context;
    initCustomEditText();
  }

  public CustomEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    this.attributeSet = attrs;
    initCustomEditText();
  }

  public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    this.attributeSet = attrs;
    initCustomEditText();
  }

  private void initCustomEditText() {
    imgCloseButton = ContextCompat.getDrawable(context, android.R.drawable.ic_delete);
    // Set bounds of the Clear button so it will look ok
    imgCloseButton.setBounds(0, 0, 60, 60/*imgCloseButton.getIntrinsicWidth(), imgCloseButton.getIntrinsicHeight()*/);

    // There may be initial text in the field, so we may need to display the  button
    handleClearButton();


    //if the Close image is displayed and the user remove his finger from the button, clear it. Otherwise do nothing
    this.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {

        CustomEditText et = CustomEditText.this;

        if (et.getCompoundDrawables()[2] == null)
          return false;

        if (event.getAction() != MotionEvent.ACTION_UP)
          return false;

        if (event.getX() > et.getWidth() - et.getPaddingRight() - imgCloseButton.getIntrinsicWidth()) {
          et.setText("");
          CustomEditText.this.handleClearButton();
        }
        return false;
      }
    });

    //if text changes, take care of the button
    this.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

        CustomEditText.this.handleClearButton();
        if (before < count) {

        }
      }

      @Override
      public void afterTextChanged(Editable arg0) {
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }
    });


  }

  protected void setCustomImage(final Drawable customDrawable, final int width, final int height) {
    imgCloseButton = customDrawable;
    imgCloseButton.setBounds(0, 0, width, height);
  }


  private void handleClearButton() {
    if (this.getText().toString().equals("")) {
      // add the clear button
      this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
    } else {
      //remove clear button
      this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], imgCloseButton, this.getCompoundDrawables()[3]);
    }
  }

  @Override
  public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
    return new CustomEditTextKeyEvent(super.onCreateInputConnection(outAttrs), true);
  }

  /*Following class is for sending the backspace event in case user want it, if we do not send it backspace event is is not recognise*/

  private class CustomEditTextKeyEvent extends InputConnectionWrapper {

    public CustomEditTextKeyEvent(InputConnection target, boolean mutable) {
      super(target, mutable);
    }

    @Override
    public boolean sendKeyEvent(KeyEvent event) {
      if (event.getAction() == KeyEvent.ACTION_DOWN
          && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {

//                CustomEditText.this.setNewText();
        // Un-comment if you wish to cancel the backspace:
        // return false;
      }
      return super.sendKeyEvent(event);
    }

    @Override
    public boolean deleteSurroundingText(int beforeLength, int afterLength) {

      if (beforeLength == 1 && afterLength == 0) {

        return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
      }
      return super.deleteSurroundingText(beforeLength, afterLength);
    }
  }
}