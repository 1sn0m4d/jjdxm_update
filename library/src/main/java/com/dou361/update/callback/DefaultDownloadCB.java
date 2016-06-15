package com.dou361.update.callback;

import android.app.Activity;
import android.app.Dialog;

import com.dou361.update.UpdateBuilder;
import com.dou361.update.UpdateHelper;
import com.dou361.update.creator.DialogUI;
import com.dou361.update.model.Update;
import com.dou361.update.util.InstallUtil;
import com.dou361.update.util.SafeDialogOper;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * @author Administrator
 */
public class DefaultDownloadCB implements UpdateDownloadCB {

    private WeakReference<Activity> actRef = null;
    private UpdateBuilder builder;
    private UpdateDownloadCB downloadCB;
    private Update update;
    private UpdateDownloadCB innerCB;

    public DefaultDownloadCB(Activity activity) {
        actRef = new WeakReference<>(activity);
    }

    public void setBuilder(UpdateBuilder builder) {
        this.builder = builder;
        downloadCB = builder.getDownloadCB();
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public void setDownloadCB(UpdateDownloadCB downloadCB) {
        this.downloadCB = downloadCB;
    }

    @Override
    public void onUpdateStart() {
        if (downloadCB != null) {
            downloadCB.onUpdateStart();
        }

        if (getInnerCB() != null) {
            innerCB.onUpdateStart();
        }
    }

    public UpdateDownloadCB getInnerCB() {
        if (innerCB == null && builder.getStrategy().isShowDownloadDialog()) {
            innerCB = builder.getDownloadDialogCreator().create(update, actRef.get());
        }
        return innerCB;
    }

    @Override
    public void onUpdateComplete(File file) {
        if (downloadCB != null) {
            downloadCB.onUpdateComplete(file);
        }

        if (getInnerCB() != null) {
            innerCB.onUpdateComplete(file);
        }

        if (builder.getStrategy().isShowInstallDialog()) {
            DialogUI creator = builder.getDialogUI();
            creator.setCheckCB(builder.getCheckCB());
            Dialog dialog = creator.create(1, update, file.getAbsolutePath(), actRef.get());
            SafeDialogOper.safeShowDialog(dialog);
        } else if (builder.getStrategy().isAutoInstall()) {
            InstallUtil.installApk(UpdateHelper.getInstance().getContext(), file.getAbsolutePath());
        }
    }

    @Override
    public void onUpdateProgress(long current, long total) {
        if (downloadCB != null) {
            downloadCB.onUpdateProgress(current, total);
        }

        if (getInnerCB() != null) {
            innerCB.onUpdateProgress(current, total);
        }
    }

    @Override
    public void onUpdateError(int code, String errorMsg) {
        if (downloadCB != null) {
            downloadCB.onUpdateError(code, errorMsg);
        }

        if (getInnerCB() != null) {
            innerCB.onUpdateError(code, errorMsg);
        }
    }
}
