package com.XHWJ.xiaoAiTongXue.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextUtils {
    /**
     * 检测EditText输入是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) ||
                (codePoint == 0xA) || (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }


    /**
     * 设置editText文本的最大输入字数
     *
     * @param editextMax
     * @param mun        最大字数
     */
    public static void setEditextMax(final EditText editextMax, final int mun) {
        editextMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > mun) { //判断EditText中输入的字符数是不是已经大于6
                    editextMax.setText(s.toString().substring(0, mun)); //设置EditText只显示前面6位字符
                    editextMax.setSelection(mun);//让光标移至末端
                    ToastUtils.showShort(null, "最多可输入" + mun + "个字符！");
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 设置editText文本的最大输入字数
     *
     * @param editextMax
     * @param mun        最大字数
     * @param tipsString 提示语
     */
    public static void setEditextMax(final EditText editextMax, final int mun, final String tipsString) {
        editextMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > mun) { //判断EditText中输入的字符数是不是已经大于6
                    editextMax.setText(s.toString().substring(0, mun)); //设置EditText只显示前面6位字符
                    editextMax.setSelection(mun);//让光标移至末端
                    ToastUtils.showShort(null,tipsString);
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
