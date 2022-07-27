package co.yangdong.privacy.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import co.yangdong.privacy.R;


public class PrivacyWebViewDialog extends DialogFragment {
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.FullDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.privacy_web_view_layout, container, false);
        WebView webView = view.findViewById(R.id.privacy_web_view);
        webView.loadUrl(this.url);
        RelativeLayout relativeLayout = view.findViewById(R.id.navigation_bar_back_root);
        relativeLayout.setOnClickListener(view1 -> dismiss());

        return view;
    }
}
