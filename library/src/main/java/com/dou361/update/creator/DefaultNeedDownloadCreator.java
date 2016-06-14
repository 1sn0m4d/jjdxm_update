package com.dou361.update.creator;

import android.app.Activity;
import android.app.ProgressDialog;

import com.dou361.update.callback.UpdateDownloadCB;
import com.dou361.update.model.Update;
import com.dou361.update.util.SafeDialogOper;

import java.io.File;

/**
 * @author Administrator
 */
public class DefaultNeedDownloadCreator implements DownloadCreator {
    @Override
    public UpdateDownloadCB create(Update update,Activity activity) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setProgress(0);
        if (update.isForced()) {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
        SafeDialogOper.safeShowDialog(dialog);
        UpdateDownloadCB downloadCB = new UpdateDownloadCB() {
            @Override
            public void onUpdateStart() {
            }

            @Override
            public void onUpdateComplete(File file) {
                SafeDialogOper.safeDismissDialog(dialog);
            }

            @Override
            public void onUpdateProgress(long current, long total) {
                int percent = (int) (current * 1.0f / total * 100);
                dialog.setProgress(percent);
            }

            @Override
            public void onUpdateError(int code, String errorMsg) {
                SafeDialogOper.safeDismissDialog(dialog);
            }
        };
        return downloadCB;
    }
}
