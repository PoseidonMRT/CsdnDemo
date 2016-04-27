package com.tt.clickableimagespan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textview);
        ClickableImageSpan imageSpan = new ClickableImageSpan(this,R.mipmap.ic_launcher) {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "TextView Click", Toast.LENGTH_SHORT).show();
            }
        };
        SpannableString text = new SpannableString(textView.getText()+"");
        text.setSpan(imageSpan,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(text);
        textView.setMovementMethod(ClickableMovementMethod.getInstance());
    }
}
