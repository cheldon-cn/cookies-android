package com.cycle.util;

import com.cycle.App;
import com.cycle.R;

/**
 * @author cycle.member
 * @date 2018/12/23
 */
public class ConfigUtil {
    private ConfigUtil() {}

    public static boolean isDebug() {
        return "debug".equalsIgnoreCase(App.getInstance().getString(R.string.build_config_type));
    }
}
