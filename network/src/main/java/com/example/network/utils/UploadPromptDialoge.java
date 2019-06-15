package com.example.network.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.network.R;


public class UploadPromptDialoge extends ProgressDialog {

    private TextView textViewTiShi;

    public UploadPromptDialoge(Context context) {
        super(context);
    }

    public UploadPromptDialoge(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIndeterminate(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.upload_prompt_dialog);
        WindowManager.LayoutParams arr = getWindow().getAttributes();
        arr.height = WindowManager.LayoutParams.WRAP_CONTENT;
        arr.alpha = 0.8f;
        arr.width = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(arr);
        textViewTiShi = getWindow().findViewById(R.id.dialog_load_text_view);
    }

    public void setTishi(String tishi) {
        if (textViewTiShi != null) {
            textViewTiShi.setText(tishi);
        }
    }
}
