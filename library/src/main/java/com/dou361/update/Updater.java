package com.dou361.update;

import android.app.Activity;

import com.dou361.update.business.DownloadWorker;
import com.dou361.update.business.IUpdateExecutor;
import com.dou361.update.business.UpdateExecutor;
import com.dou361.update.business.UpdateWorker;
import com.dou361.update.callback.DefaultCheckCB;
import com.dou361.update.callback.DefaultDownloadCB;
import com.dou361.update.model.Update;

/**
 *
 * @author lzh
 */
public class Updater {
    private static Updater updater;
    private IUpdateExecutor executor;

    private Updater() {
        executor = UpdateExecutor.getInstance();
    }
    public static Updater getInstance() {
        if (updater == null) {
            updater = new Updater();
        }
        return updater;
    }

    /**
     * check out whether or not there is a new version on internet
     * @param activity The activity who need to show update dialog
     * @param builder update builder that contained all config.
     */
    public void checkUpdate(Activity activity,UpdateBuilder builder) {

        UpdateConfig.getConfig().context(activity);
        // define a default callback to receive callback from update task
        DefaultCheckCB checkCB = new DefaultCheckCB(activity);
        checkCB.setBuilder(builder);

        UpdateWorker checkWorker = builder.getCheckWorker();
        checkWorker.setUrl(builder.getUrl());
        checkWorker.setParser(builder.getJsonParser());
        checkWorker.setCheckCB(checkCB);

        executor.check(builder.getCheckWorker());
    }

    /**
     * Request to download apk.
     * @param activity The activity who need to show download and install dialog;
     * @param update update instance,should not be null;
     * @param builder update builder that contained all config;
     */
    public void downUpdate(Activity activity,Update update,UpdateBuilder builder) {
        UpdateConfig.getConfig().context(activity);
        // define a default download callback to receive callback from download task
        DefaultDownloadCB downloadCB = new DefaultDownloadCB(activity);
        downloadCB.setBuilder(builder);
        downloadCB.setUpdate(update);
        downloadCB.setDownloadCB(builder.getDownloadCB());

        DownloadWorker downloadWorker = builder.getDownloadWorker();
        downloadWorker.setUrl(update.getUpdateUrl());
        downloadWorker.setDownloadCB(downloadCB);
        downloadWorker.setCacheFileName(builder.getFileCreator().create(update.getVersionName()));

        executor.download(downloadWorker);
    }

}
