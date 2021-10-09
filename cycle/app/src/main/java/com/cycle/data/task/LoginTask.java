package com.cycle.data.task;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import com.cycle.App;
import com.cycle.data.NetworkTaskScheduler;
import com.cycle.data.OnResponseListener;
import com.cycle.util.ConstantUtil;
import com.cycle.util.PrefsUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author cycle.member
 * @date 2017/9/16
 */

public class LoginTask extends BaseTask<String> {

    private String mEmail;
    private String mPassword;

    public LoginTask(String email, String password, OnResponseListener<String> listener) {
        super(listener);
        mEmail = email;
        mPassword = password;
    }

    @Override
    public void run() {
        String xsrf = UUID.randomUUID().toString().replaceAll("-", "");

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put(ConstantUtil.KEY_COOKIE, "_xsrf=" + xsrf);

        Map<String, String> datas = new HashMap<>();
        datas.put("email", mEmail);
        datas.put("password", mPassword);
        datas.put(ConstantUtil.KEY_XSRF, xsrf);
        try {
            Connection.Response res = Jsoup.connect(ConstantUtil.LOGIN_URL).data(datas).headers(headers).method(Connection.Method.POST).execute();

            Map<String, String> cookies = res.cookies();
            if (cookies != null) {
                if (cookies.containsKey("user")) {
                    JSONObject jsonObject = new JSONObject();
                    for (String key : cookies.keySet()) {
                        jsonObject.put(key, cookies.get(key));
                    }
                    PrefsUtil.putString(App.getInstance(), ConstantUtil.KEY_COOKIE, jsonObject.toString());
                    PrefsUtil.putString(App.getInstance(), ConstantUtil.KEY_XSRF, xsrf);

                    NetworkTaskScheduler.getInstance().execute(new AuthCheckTask(new OnResponseListener<String>() {
                        @Override
                        public void onSucceed(String data) {
                            successOnUI("");
                        }

                        @Override
                        public void onFailed(String msg) {
                            failedOnUI(msg);
                        }
                    }));

                    return;
                }
            }
        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }

        failedOnUI("登录失败");
    }
}
