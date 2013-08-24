package org.bitbrothers.example.jnistringencoding;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText inputField;
    private TextView vmField;
    private TextView nativeField;
    private TextView convertField;

    private final TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            try {
                vmField.setText(buildOutput(str, str.getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            nativeField.setText(buildOutput(str, NativeLayer.convertStringToByteArray(str)));
        }
    };

    private final View.OnClickListener onConvertClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String s = inputField.getText().toString();
            try {
                String result = NativeLayer.convertByteArrayToString(s.getBytes("UTF-8"));
                convertField.setText('"' + result + '"');
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("jnistrings");
        setContentView(R.layout.main);
        inputField = (EditText) findViewById(R.id.inputField);
        vmField = (TextView) findViewById(R.id.vmField);
        nativeField = (TextView) findViewById(R.id.nativeField);
        convertField = (TextView) findViewById(R.id.convertField);
        findViewById(R.id.convertButton).setOnClickListener(onConvertClickListener);
        inputField.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputField.setText("A" + "\uD834" + "\uDF39");
    }

    private String buildOutput(String str, byte[] array) {
        StringBuilder b = new StringBuilder();
        b.append("String Length: ").append(str.length()).append('\n');
        b.append("Array Length: ").append(array.length).append('\n');
        try {
            String arrayToString = new String(array, "UTF-8");
            b.append("String : \"").append(arrayToString).append("\"\n");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        b.append("Array: ").append(Arrays.toString(array));
        return b.toString();
    }
}
