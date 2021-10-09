package com.cycle.data.task;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.cycle.data.OnResponseListener;
import com.cycle.data.entity.UserProfile;
import com.cycle.util.ConstantUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cycle.member
 * @date 2019-07-21
 */
public class GetBlockedUserListTask extends BaseTask<List<UserProfile>> {
    public GetBlockedUserListTask(OnResponseListener<List<UserProfile>> listener) {
        super(listener);
    }

    @Override
    public void run() {

        List<UserProfile> result = new ArrayList<>();

        Document doc;

        try {
            doc = get(ConstantUtil.BLOCKED_USER_URL);
        } catch (IOException e) {
            e.printStackTrace();
            failedOnUI(e.getMessage());
            return;
        }

        Elements elements = doc.select("div.member-lists .ui-content .member");

        if (elements.isEmpty()) {
            successOnUI(result);
            return;
        }

        for (Element element : elements) {
            UserProfile profile = new UserProfile();
            profile.setAvatar(element.select("img.avatar").attr("src"));
            profile.setUsername(element.select("span.username a").text());
            profile.setUrl(element.select("span.username a").first().absUrl("href"));

            result.add(profile);
        }

        successOnUI(result);

    }
}
