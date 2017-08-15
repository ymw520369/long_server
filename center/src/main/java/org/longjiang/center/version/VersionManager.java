package org.longjiang.center.version;

import com.alibaba.fastjson.JSON;
import org.alan.mars.monitor.FileMonitor;
import org.alan.utils.FileHelper;
import org.longjiang.center.config.Version;
import org.longjiang.core.data.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017/6/5.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class VersionManager implements CommandLineRunner {
    public static String versionConfig = "config/center.json";
    @Autowired
    private FileMonitor monitor;

    public Map<Platform, Version> serverVersions = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        monitor.monitor(versionConfig, file -> {
            String json = FileHelper.readFile(versionConfig);
            List<Version> versions = JSON.parseArray(json, Version.class);
            versions.forEach(version -> serverVersions.put(version.platform, version));
        }, true);
    }
}
