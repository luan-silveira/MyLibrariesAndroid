package br.com.luansilveira.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTMLTextView extends AppCompatTextView {

    public HTMLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HTMLTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, new int[]{
                R.attr.src,
        });

        int src = array.getResourceId(0, -1);
        if (src != -1) {
            InputStream inputStream = getResources().openRawResource(src);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder html = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    html.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!html.toString().isEmpty()) setText(Html.fromHtml(html.toString()));
        } else {
            setText(Html.fromHtml(getText().toString()));
        }

        array.recycle();
    }
}
