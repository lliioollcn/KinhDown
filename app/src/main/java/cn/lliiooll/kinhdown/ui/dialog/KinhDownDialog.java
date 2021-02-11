package cn.lliiooll.kinhdown.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.lliiooll.kinhdown.R;

/**
 * @author lliiooll
 */
public class KinhDownDialog extends Dialog {
    public KinhDownDialog(@NonNull Context context) {
        super(context);
    }

    public KinhDownDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public KinhDownDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final KinhDownDialog dialog = new KinhDownDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.fragment_dialog, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            TextView title = layout.findViewById(R.id.dialog_title);
            TextView content = layout.findViewById(R.id.dialog_msg);
            ScrollView scroll = layout.findViewById(R.id.dialog_scroll);
            content.setMovementMethod(ScrollingMovementMethod.getInstance());
            Button success = layout.findViewById(R.id.dialog_success);
            Button cancel = layout.findViewById(R.id.dialog_cancel);
            if (this.title != null) {
                title.setText(this.title);
            }
            if (this.message != null) {
                content.setText(this.message);
            }
            if (this.negativeButtonText != null) {
                success.setText(this.negativeButtonText);
            }
            if (this.positiveButtonText != null) {
                cancel.setText(this.positiveButtonText);
            }
            if (this.negativeButtonClickListener != null) {
                success.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Builder.this.negativeButtonClickListener.onClick(dialog, 0);
                    }
                });
            }
            if (this.positiveButtonClickListener != null) {
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Builder.this.positiveButtonClickListener.onClick(dialog, 0);
                    }
                });
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
