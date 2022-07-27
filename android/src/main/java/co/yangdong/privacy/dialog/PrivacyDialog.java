package co.yangdong.privacy.dialog;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import co.yangdong.privacy.listener.PrivacyDialogClickListener;
import co.yangdong.privacy.R;

public class PrivacyDialog extends DialogFragment {
    private String privacyTitle;
    private String privacyText;
    private String onePolicyURL;
    private String twoPolicyURL;
    private PrivacyDialogClickListener onAgreeClickListener;
    private PrivacyDialogClickListener onRejectClickListener;
    private PrivacyWebViewDialog privacyWebViewDialog;

    public void setPrivacyTitle(String privacyTitle) {
        this.privacyTitle = privacyTitle;
    }

    public void setPrivacyText(String privacyText) {
        this.privacyText = privacyText;
    }

    public void setOnePolicyURL(String onePolicyURL) {
        this.onePolicyURL = onePolicyURL;
    }

    public void setTwoPolicyURL(String twoPolicyURL) {
        this.twoPolicyURL = twoPolicyURL;
    }

    public void setOnAgreeClickListener(final PrivacyDialogClickListener listener) {
        this.onAgreeClickListener = listener;
    }

    public void setOnRejectClickListener(final PrivacyDialogClickListener listener) {
        this.onRejectClickListener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        privacyWebViewDialog = new PrivacyWebViewDialog();

        View view = inflater.inflate(R.layout.privacy_dialog, container, false);
        TextView titleView = view.findViewById(R.id.dialog_title);
        titleView.setText(privacyTitle);
        TextView textView = view.findViewById(R.id.dialog_content);
        textView.setText(privacyText);

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(privacyText);
        final int start = privacyText.indexOf("《");//第一个出现的位置
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                privacyWebViewDialog.setUrl(onePolicyURL);
                privacyWebViewDialog.show(getParentFragmentManager(), "PrivacyWebViewDialog");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(view.getContext(), R.color.privacyPrimaryColor));
                ds.setUnderlineText(false);
            }
        }, start, start + 6, 0);

        final int end = privacyText.lastIndexOf("《");//最后一个出现的位置

        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                privacyWebViewDialog.setUrl(twoPolicyURL);
                privacyWebViewDialog.show(getParentFragmentManager(), "PrivacyWebViewDialog");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(view.getContext(), R.color.privacyPrimaryColor));
                ds.setUnderlineText(false);
            }
        }, end, end + 6, 0);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(ssb, TextView.BufferType.SPANNABLE);

        AppCompatButton agreeBtn = view.findViewById(R.id.agree_btn);
        agreeBtn.setOnClickListener(vi -> onAgreeClickListener.onClick());

        AppCompatButton rejectBtn = view.findViewById(R.id.reject_btn);
        rejectBtn.setOnClickListener(vi -> onRejectClickListener.onClick());

        return view;
    }
}