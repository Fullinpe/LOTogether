package com.example.lotogether;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class my_Dialog extends Dialog {

    private my_Dialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public my_Dialog(Context context) {
        super(context);
    }

    public static class Builder {

        private View mLayout;

        private EditText key_dia;
        private TextView mMessage;
        private Button mButton;

        private View.OnClickListener mButtonClickListener;

        private my_Dialog mDialog;


        @SuppressLint("InflateParams")
        Builder(Context context) {
//            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            mDialog = new my_Dialog(context, R.style.Theme_AppCompat_Dialog);
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //加载布局文件
            assert inflater != null;
            mLayout = inflater.inflate(R.layout.mdialog, (ViewGroup) mDialog.getCurrentFocus(), true);
            //添加布局文件到 Dialog
            mDialog.setContentView(mLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));


            key_dia = mLayout.findViewById(R.id.key_dia);
            mButton = mLayout.findViewById(R.id.dialog_button);


        }

        /**
         * 通过 ID 设置 Dialog 图标
         *public Builder setIcon(int resId) 55             mIcon.setImageResource(resId);
         return this;
         }


        /**
         * 设置 Message
         */
        Builder setMessage(@NonNull String message) {
            mMessage.setText(message);
            return this;
        }


        @SuppressLint("SetTextI18n")
        Builder key() {
            key_dia.setText("Hello");
            return this;
        }

        /**
         * 设置按钮文字和监听
         */
        Builder setButton(@NonNull String text, View.OnClickListener listener) {
            mButton.setText(text);
            mButtonClickListener = listener;
            return this;
        }

        my_Dialog create() {
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                    mButtonClickListener.onClick(view);
                }
            });
            key_dia.requestFocus();

            mDialog.setContentView(mLayout);
            mDialog.setCancelable(true);                //用户可以点击后退键关闭 Dialog
            mDialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
            return mDialog;
        }
        my_Dialog show() {
            my_Dialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
