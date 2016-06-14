package com.dou361.update.business;

import com.dou361.update.callback.UpdateDownloadCB;
import com.dou361.update.util.HandlerUtil;

import java.io.File;

/**
 * @author Administrator
 */
public abstract class DownloadWorker implements Runnable {

    protected String url;
    protected UpdateDownloadCB downloadCB;
    protected File cacheFileName;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCacheFileName(File cacheFileName) {
        this.cacheFileName = cacheFileName;
    }

    public void setDownloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
    }

    @Override
    public void run() {
        cacheFileName.getParentFile().mkdirs();
        try {
            sendUpdateStart();
            download(url, cacheFileName);
            sendUpdateComplete(cacheFileName);
        } catch (HttpException he) {
            sendUpdateError(he.getCode(), he.getErrorMsg());
        } catch (Exception e) {
            sendUpdateError(-1, e.getMessage());
        }
    }

    protected abstract void download(String url, File target) throws Exception;

    private void sendUpdateStart() {
        if (downloadCB == null) return;

        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                downloadCB.onUpdateStart();
            }
        });
    }

    protected void sendUpdateProgress(final long current, final long total) {
        if (downloadCB == null) return;

        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                downloadCB.onUpdateProgress(current, total);
            }
        });
    }

    private void sendUpdateComplete(final File file) {
        if (downloadCB == null) return;

        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                downloadCB.onUpdateComplete(file);
            }
        });
    }

    private void sendUpdateError(final int code, final String errorMsg) {
        if (downloadCB == null) return;

        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                downloadCB.onUpdateError(code, errorMsg);
            }
        });
    }
}
