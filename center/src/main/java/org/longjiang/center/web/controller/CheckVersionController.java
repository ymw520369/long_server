package org.longjiang.center.web.controller;

import org.longjiang.center.config.Version;
import org.longjiang.center.version.VersionManager;
import org.longjiang.core.data.ClientVersion;
import org.longjiang.core.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.longjiang.center.web.controller.CheckVersionController.VersionResult.UpdateType.*;

/**
 * Created on 2017/6/2.
 *
 * @author Alan
 * @since 1.0
 */
@RequestMapping("version")
@RestController
public class CheckVersionController {

    @Autowired
    private VersionManager versionManager;

    /**
     * 检查客户端版本情况
     *
     * @param versionRequest
     * @return
     */
    @RequestMapping(value = "check", method = RequestMethod.POST)
    public JsonResult checkVerison(@RequestBody ClientVersion versionRequest) {
        VersionResult versionResult = new VersionResult();
        Version version = versionManager.serverVersions.get(versionRequest.platform);
        //检查是否审核客户端
        if (version.approvalMajorVersion != 0 && version.approvalMajorVersion == versionRequest.majorVersion) {
            if (version.approvalMicroVersion > versionRequest.microVersion) {
                //审核服需要更新资源
                versionResult.updateType = RESOURCE.ordinal();
                versionResult.url = version.approvalResourceUpdateUrl;
            } else {
                versionResult.updateType = NONE.ordinal();
            }
        } else {//检查正常线上客户端
            if (version.majorVersion > versionRequest.majorVersion || version.subVersion > versionRequest.subVersion) {
                versionResult.updateType = APP.ordinal();
                versionResult.url = version.appUpdateUrl;
                if (version.subVersion > versionRequest.subVersion) {
                    versionResult.forced = version.forced;
                }
            } else if (version.microVersion > versionRequest.microVersion) {
                versionResult.updateType = RESOURCE.ordinal();
                versionResult.url = version.resourceUpdateUrl;
            } else {
                versionResult.updateType = NONE.ordinal();
            }
        }
        return JsonResult.SUCCESS().setData(versionResult);
    }

    /**
     * 版本验证返回信息
     */
    static class VersionResult {
        enum UpdateType {NONE, APP, RESOURCE}

        public int updateType = RESOURCE.ordinal();
        public boolean forced = true;
        public String url = "";

        public VersionResult() {
        }

        public VersionResult(UpdateType updateType, boolean forced, String updateUrl) {
            this.updateType = updateType.ordinal();
            this.url = updateUrl;
            this.forced = forced;
        }
    }
}
