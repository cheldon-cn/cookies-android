package com.cycle.data.task;

import android.text.TextUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.cycle.App;
import com.cycle.data.AuthInfoManager;
import com.cycle.data.OnResponseListener;
import com.cycle.util.ConstantUtil;

import java.io.IOException;

/**
 *
 * @author cycle.member
 * @date 2017/9/16
 */

public class AuthCheckTask extends BaseTask<String> {
    public AuthCheckTask(OnResponseListener<String> listener) {
        super(listener);
    }

    @Override
    public void run() {
        try {
            if (!TextUtils.isEmpty(AuthInfoManager.getInstance().getUsername()) &&
                    !TextUtils.isEmpty(AuthInfoManager.getInstance().getAvatar())) {
                successOnUI("succeed");
                return;
            }

            Document doc = get(ConstantUtil.BASE_URL);

            boolean succeed = checkAuth(doc);

            if (succeed) {
                successOnUI("succeed");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        AuthInfoManager.getInstance().setUsername(null);
        AuthInfoManager.getInstance().setAvatar(null);
        failedOnUI("auth failed");
    }

    @Override
    protected void successOnUI(String data) {
        super.successOnUI(data);

        if (!mIsCanceled) {
            Document doc;
            try {
                doc = get(ConstantUtil.VERIFY_TELEPHONE_URL);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Elements elements = doc.select("button#getSmsCode");

            final boolean telephoneVerified = elements.isEmpty();

            mHandler.post(() -> {
                App.getInstance().mGlobal.telephoneVerified.setValue(telephoneVerified);
            });
        }
    }
}
