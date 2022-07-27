package co.yangdong.privacy.utils;

import android.app.Application;

import androidx.annotation.NonNull;

import com.yl.lib.sentry.hook.PrivacyResultCallBack;
import com.yl.lib.sentry.hook.PrivacySentry;
import com.yl.lib.sentry.hook.PrivacySentryBuilder;
import com.yl.lib.sentry.hook.util.PrivacyLog;

public class PrivacySentryUtils {
    public static void init(Application ctx) {
        PrivacySentryBuilder builder = new PrivacySentryBuilder()
                .syncDebug(true)
                // 自定义文件结果的输出名
                .configResultFileName("buyer_privacy")
                // 配置写入文件日志
                .enableFileResult(true)
                // 文件输出后的回调
                .configResultCallBack(new PrivacyResultCallBack() {
                    @Override
                    public void onResultCallBack(@NonNull String s) {
                        PrivacyLog.Log.i("result file patch is " + s);
                    }
                });
        // 添加默认结果输出，包含log输出和文件输出
        PrivacySentry.Privacy.INSTANCE.init(ctx, builder);
    }
}
