package cn.lliiooll.kinhdown.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.lliiooll.kinhdown.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * @author lliiooll
 */
public class KinhDownLoader extends Dialog {
    private AVLoadingIndicatorView view;

    public KinhDownLoader(@NonNull Context context) {
        super(context);
    }

    public KinhDownLoader(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private Type type;
        private AVLoadingIndicatorView view;
        private String title;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setType(Type v) {
            this.type = v;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        public KinhDownLoader create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final KinhDownLoader dialog = new KinhDownLoader(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.fragment_loader, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            view = layout.findViewById(R.id.loader_view);
            view.setIndicator(type.name());
            view.setIndicatorColor(R.color.colorBlack);
            dialog.setContentView(layout);
            TextView title = layout.findViewById(R.id.loader_title);
            title.setText(this.title);
            dialog.setContentView(layout);
            return dialog;
        }
    }

    private void setView(AVLoadingIndicatorView view) {
        this.view = view;
    }

    public void open() {
        if (!isShowing()) {
            show();
        }
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void close() {
        if (isShowing()) {
            dismiss();
        }
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public enum Type {
        BallPulse, BallGridPulse, BallClipRotate, BallClipRotatePulse,
        SquareSpin, BallClipRotateMultiple, BallPulseRise, BallRotate,
        CubeTransition, BallZigZag, BallZigZagDeflect, BallTrianglePath,
        BallScale, LineScale, LineScaleParty, BallScaleMultiple,
        BallPulseSync, BallBeat, LineScalePulseOut, LineScalePulseOutRapid,
        BallScaleRipple, BallScaleRippleMultiple, BallSpinFadeLoader, LineSpinFadeLoader,
        TriangleSkewSpin, Pacman, BallGridBeat, SemiCircleSpin,
    }
}
