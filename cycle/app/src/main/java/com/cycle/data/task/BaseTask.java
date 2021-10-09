package com.cycle.data.task;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.cycle.App;
import com.cycle.data.AuthInfoManager;
import com.cycle.data.OnResponseListener;
import com.cycle.util.ConstantUtil;
import com.cycle.util.PrefsUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author cycle.member
 * @date 2017/9/16
 */

public abstract class BaseTask<T> implements Runnable {
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    protected OnResponseListener<T> mListener;

    protected boolean mIsCanceled = false;

    BaseTask(OnResponseListener<T> listener) {
        mListener = listener;
    }

    public void cancel() {
        mIsCanceled = true;
    }

    public boolean isCanceled() {
        return mIsCanceled;
    }

    protected void successOnUI(final T data) {
        if (!mIsCanceled && mListener != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onSucceed(data);
                }
            });
        }
    }

    protected void failedOnUI(final String msg) {
        if (!mIsCanceled && mListener != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onFailed(msg);
                }
            });
        }
    }

    protected Connection getConnection(String url) {
        Connection connection = Jsoup.connect(url);

        Map<String, String> cookies = getCookies();
        if (cookies.size() > 0) {
            connection.cookies(cookies);
        }

        return connection;
    }

    protected Document get(String url) throws IOException {
        Document doc = getConnection(url).get();
        checkNotification(doc);
        return fixLink(doc);
    }

    protected Map<String, String> getCookies() {
        Map<String, String> cookies = new HashMap<>();
        String cookieString = PrefsUtil.getString(App.getInstance(), ConstantUtil.KEY_COOKIE, "");
        if (!TextUtils.isEmpty(cookieString)) {

            try {
                JSONObject jsonObject = new JSONObject(cookieString);

                Iterator it = jsonObject.keys();
                while (it.hasNext()) {
                    String key = String.valueOf(it.next());
                    cookies.put(key, (String) jsonObject.get(key));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return cookies;
    }

    protected String getCookieValue(String key) {
        Map<String, String> cookies = getCookies();
        return cookies.get(key);
    }

    protected String getXsrf() {
        return PrefsUtil.getString(App.getInstance(), ConstantUtil.KEY_XSRF, "");
    }

    protected <T extends Element> T fixLink(T element) {
        Elements links = element.select("[href]");
        for (Element link : links) {
            String href = link.attr("href");
            if (!TextUtils.isEmpty(href)) {
                if (href.toLowerCase().startsWith("http")) {
                    continue;
                }
                link.attr("href", link.absUrl("href"));
            }
        }
        links = element.select("[src]");
        for (Element link : links) {
            String src = link.attr("src");
            if (!TextUtils.isEmpty(src)) {
                if (src.toLowerCase().startsWith("http")) {
                    continue;
                }
                link.attr("src", link.absUrl("src"));
            }
        }
        return element;
    }

    protected void checkNotification(Document doc) {
        Elements elements = doc.select("a.contextually-unread");
        final boolean hasNotifications = !elements.isEmpty();
        mHandler.post(() -> App.getInstance().mGlobal.hasNotifications.setValue(hasNotifications));
    }

    protected boolean checkAuth(Document doc) {
        Elements elements = doc.select("div.usercard");
        if (!elements.isEmpty()) {
            Element usercardElement = elements.first();

            AuthInfoManager.getInstance().setUsername(usercardElement.select("div.username").first().text());
            AuthInfoManager.getInstance().setAvatar(usercardElement.select("img.avatar").first().attr("src"));
            return true;
        }
        return false;
    }

    /**
     * 尝试补偿登录状态
     * @param doc -
     */
    protected void tryFixAuthStatus(Document doc) {
        if (doc == null) {
            return;
        }

        if (TextUtils.isEmpty(AuthInfoManager.getInstance().getUsername())) {
            checkAuth(doc);
        }
    }
}
