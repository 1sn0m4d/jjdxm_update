package com.dou361.update;

import android.app.Activity;
import android.text.TextUtils;

import com.dou361.update.business.DownloadWorker;
import com.dou361.update.business.UpdateWorker;
import com.dou361.update.callback.UpdateCheckCB;
import com.dou361.update.callback.UpdateDownloadCB;
import com.dou361.update.creator.ApkFileCreator;
import com.dou361.update.strategy.UpdateStrategy;
import com.dou361.update.creator.DialogCreator;
import com.dou361.update.creator.DownloadCreator;
import com.dou361.update.creator.InstallCreator;
import com.dou361.update.model.UpdateParser;

/**
 * @author Administrator
 */
public class UpdateBuilder {

    private UpdateWorker checkWorker;
    private DownloadWorker downloadWorker;
    private UpdateCheckCB checkCB;
    private UpdateDownloadCB downloadCB;
    private String url;
    private UpdateStrategy strategy;
    private DialogCreator updateDialogCreator;
    private InstallCreator installDialogCreator;
    private DownloadCreator downloadDialogCreator;
    private UpdateParser jsonParser;
    private ApkFileCreator fileCreator;

    public static UpdateBuilder create() {
        return new UpdateBuilder();
    }

    public UpdateBuilder url(String url) {
        this.url = url;
        return this;
    }

    public UpdateBuilder checkWorker(UpdateWorker checkWorker) {
        this.checkWorker = checkWorker;
        return this;
    }

    public UpdateBuilder downloadWorker(DownloadWorker downloadWorker) {
        this.downloadWorker = downloadWorker;
        return this;
    }

    public UpdateBuilder downloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
        return this;
    }

    public UpdateBuilder checkCB (UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
        return this;
    }

    public UpdateBuilder jsonParser (UpdateParser jsonParser) {
        this.jsonParser = jsonParser;
        return this;
    }

    public UpdateBuilder fileCreator (ApkFileCreator fileCreator) {
        this.fileCreator = fileCreator;
        return this;
    }

    public UpdateBuilder downloadDialogCreator (DownloadCreator downloadDialogCreator) {
        this.downloadDialogCreator = downloadDialogCreator;
        return this;
    }

    public UpdateBuilder installDialogCreator (InstallCreator installDialogCreator) {
        this.installDialogCreator = installDialogCreator;
        return this;
    }

    public UpdateBuilder updateDialogCreator(DialogCreator updateDialogCreator) {
        this.updateDialogCreator = updateDialogCreator;
        return this;
    }

    public UpdateBuilder strategy(UpdateStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public void check(Activity activity) {
        Updater.getInstance().checkUpdate(activity, this);
    }

    public UpdateStrategy getStrategy() {
        if (strategy == null) {
            strategy = UpdateConfig.getConfig().getStrategy();
        }
        return strategy;
    }

    public String getUrl() {
        if (TextUtils.isEmpty(url)) {
            url = UpdateConfig.getConfig().getUrl();
        }
        return url;
    }

    public DialogCreator getUpdateDialogCreator() {
        if (updateDialogCreator == null) {
            updateDialogCreator = UpdateConfig.getConfig().getUpdateDialogCreator();
        }
        return updateDialogCreator;
    }

    public InstallCreator getInstallDialogCreator() {
        if (installDialogCreator == null) {
            installDialogCreator = UpdateConfig.getConfig().getInstallDialogCreator();
        }
        return installDialogCreator;
    }

    public DownloadCreator getDownloadDialogCreator() {
        if (downloadDialogCreator == null) {
            downloadDialogCreator = UpdateConfig.getConfig().getDownloadDialogCreator();
        }
        return downloadDialogCreator;
    }

    public UpdateParser getJsonParser() {
        if (jsonParser == null) {
            jsonParser = UpdateConfig.getConfig().getJsonParser();
        }
        return jsonParser;
    }

    public UpdateWorker getCheckWorker() {
        if (checkWorker == null) {
            checkWorker = UpdateConfig.getConfig().getCheckWorker();
        }
        return checkWorker;
    }

    public DownloadWorker getDownloadWorker() {
        if (downloadWorker == null) {
            downloadWorker = UpdateConfig.getConfig().getDownloadWorker();
        }
        return downloadWorker;
    }

    public ApkFileCreator getFileCreator() {
        if (fileCreator == null) {
            fileCreator = UpdateConfig.getConfig().getFileCreator();
        }
        return fileCreator;
    }

    public UpdateCheckCB getCheckCB() {
        if (checkCB == null) {
            checkCB = UpdateConfig.getConfig().getCheckCB();
        }
        return checkCB;
    }

    public UpdateDownloadCB getDownloadCB() {
        if (downloadCB == null) {
            downloadCB = UpdateConfig.getConfig().getDownloadCB();
        }
        return downloadCB;
    }
}
