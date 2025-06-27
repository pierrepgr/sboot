package com.reinosoft.banner;

import com.reinosoft.exception.BusinessException;
import com.reinosoft.utils.SLogger;
import com.reinosoft.web.SBoot;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Banner {
    private static final String BANNER_FILE = "banner.txt";
    private static final String DEFAULT_BANNER_FILE = "default-banner.txt";

    private Banner() {}

    public static String getBanner() {
        try {
            var url = SLogger.class.getClassLoader().getResource(BANNER_FILE);

            if (url == null) url = SLogger.class.getClassLoader().getResource(DEFAULT_BANNER_FILE);

            assert url != null : "Banner file not found: " + BANNER_FILE + " or " + DEFAULT_BANNER_FILE;

            var banner = new String(Files.readAllBytes(Paths.get(url.toURI())));

            banner = banner.replace("${sboot.version}", SBoot.getVersion());

            return banner;
        } catch (Exception e) {
            throw new BusinessException("Failed to load banner file: " + BANNER_FILE + " or " + DEFAULT_BANNER_FILE, e);
        }
    }
}
