package com.skcc.cloudz.zcp.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import com.skcc.cloudz.zcp.common.exception.InvalidRequestException;

public class CommonUtil {

    private CommonUtil() {
    }

    private static class CommonUtilHolder {
        static final CommonUtil single = new CommonUtil();
    }

    public static CommonUtil getInstance() {
        return CommonUtilHolder.single;
    }

    public String encodeURIComponent(String s) {
        String result = null;

        try {
            result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

    public String getProfile(Environment environment) {
        if (environment == null) {
            throw new InvalidRequestException("잘못된 요청 입니다.");
        }

        String[] activeProfiles = environment.getActiveProfiles();

        String profile = StringUtils.EMPTY;
        for (String activeProfile : activeProfiles) {
            profile = activeProfile;
            break;
        }

        return profile;
    }
}
