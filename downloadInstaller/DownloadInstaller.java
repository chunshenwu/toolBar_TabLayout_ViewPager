package tw.com.fet.ecs.downloadInstaller;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.hiiir.toolkit.debug.HrLog;
import com.hiiir.toolkit.threadpool.ThreadPoolUtil;

import java.io.File;

import tw.com.fet.ecs.util.DBUtil;

public class DownloadInstaller {

    private static final String TAG = DownloadInstaller.class.getSimpleName();

    public enum Type {
        VOIP,
        EMMA,
        ;

        public String getEnqueueIdPreferenceName() {
            HrLog.d(TAG, "getEnqueueIdPreferenceName:");
            final String name = name() + "_enqueueId";
            HrLog.v(TAG, "getEnqueueIdPreferenceName: name is " + name);
            return name;
        }

        public String getAutoInstallPreferenceName() {
            HrLog.d(TAG, "getAutoInstallPreferenceName:");
            final String name = name() + "_autoInstall";
            HrLog.v(TAG, "getEnqueueIdPreferenceName: name is " + name);
            return name;
        }
    }

    private final static String INSTALL_TYPE = "application/vnd.android.package-archive";

    private final Context mContext;
    private final String mApkUrl;
    private final Type mType;

    private final DownloadManager mDownloadManager;
    private final SharedPreferences mPreferences;

    private boolean mAutoInstall = true;

    DownloadInstaller(final Context context, final String apkUrl, final Type type) {
        mContext = context;
        mApkUrl = apkUrl;
        mType = type;

        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    void setAutoInstall(final boolean autoInstall) {
        mAutoInstall = autoInstall;
    }

    private String getFileName() {
        return mType.name() + ".apk";
    }

    private String getPath() {
        HrLog.d(TAG, "start:");
        final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + getFileName();
        HrLog.v(TAG, path);
        return path;
    }

    public void start() {
        HrLog.i(TAG, "start:");
        removeFileIfExist();
        removeDownloadTaskIfExist();
        download();
    }

    static void install(Context context, Uri fileUri) {
        HrLog.d(TAG, "install:");
        Intent triggerInstall = new Intent(Intent.ACTION_VIEW);
        triggerInstall.setDataAndType(fileUri, INSTALL_TYPE);
        triggerInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(triggerInstall);
    }

    static void clearPreference(final SharedPreferences preferences, final Type type) {
        HrLog.d(TAG, "clearPreference:");
        preferences.edit()
            .remove(type.getEnqueueIdPreferenceName())
            .remove(type.getAutoInstallPreferenceName())
            .apply();
    }

    private void removeFileIfExist() {
        HrLog.d(TAG, "removeFileIfExist:");
        String filePath = getPath();
        File file = new File(filePath);
        if (file != null && file.exists()) {
            file.delete();
            HrLog.v(TAG, "start: Delete exist file");
        }
    }
    private void removeDownloadTaskIfExist() {
        HrLog.d(TAG, "removeDownloadTaskIfExist:");
        long previousDownloadId = mPreferences.getLong(mType.getEnqueueIdPreferenceName(), -1);
        if (previousDownloadId != -1) {
            HrLog.v(TAG, "removeDownloadTaskIfExist: remove");
            mDownloadManager.remove(previousDownloadId);
        }
    }

    private long download() {
        HrLog.d(TAG, "download:");
        Uri uri = Uri.parse(mApkUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, getFileName());
        long enqueueId = mDownloadManager.enqueue(request);
        mPreferences.edit()
            .putLong(mType.getEnqueueIdPreferenceName(), enqueueId)
            .putBoolean(mType.getAutoInstallPreferenceName(), mAutoInstall)
            .apply();
        return enqueueId;
    }


    public static void checkFlagAndInstall(final Context context, final Intent intent) {
        HrLog.i(TAG, "checkFlagAndInstall:");
        Runnable runnable = new InstallApkRunnable(context, intent);
        ThreadPoolUtil.execute(runnable);
    }

    public static void removeDownloadTask(Context context, Intent intent) {
        HrLog.i(TAG, "removeDownloadTask:");
        final long[] downloadIdArray = intent.getLongArrayExtra(
            DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);

        if (downloadIdArray != null && downloadIdArray.length > 0) {
            final long referenceDownloadId = downloadIdArray[0];
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            long enqueueId = -1;
            String log = null;
            for (Type type : Type.values()) {
                enqueueId = preferences.getLong(type.getEnqueueIdPreferenceName(), -1);
                log = type.name() + " enqueueId is " + enqueueId + "referenceDownloadId " + referenceDownloadId;
                if (referenceDownloadId == enqueueId) {
                    DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    dm.remove(referenceDownloadId);
                    DownloadInstaller.clearPreference(preferences, type);
                    HrLog.v(TAG, "removeDownloadTask: " + log);
                } else {
                    HrLog.v(TAG, "removeDownloadTask: Skip! Id not match. " + log);
                }
            }
        }
    }


    public static class Builder {
        private DownloadInstaller mDownloadInstaller = null;

        public Builder(final Context context, final String apkUrl, final Type type) {
            if (context == null || apkUrl == null) {
                throw new IllegalArgumentException();
            }
            mDownloadInstaller = new DownloadInstaller(context, apkUrl, type);
        }

        public Builder setAutoInstall(final boolean autoInstall) {
            HrLog.i(TAG, "setAutoInstall:");
            mDownloadInstaller.setAutoInstall(autoInstall);
            return this;
        }

        public DownloadInstaller build() {
            HrLog.i(TAG, "build:");
            return mDownloadInstaller;
        }
    }
}

class InstallApkRunnable implements Runnable {
    private static final String TAG = InstallApkRunnable.class.getSimpleName();
    private final Context mContext;
    private final Intent mIntent;

    public InstallApkRunnable(final Context context, final Intent intent) {
        mContext = context;
        mIntent = intent;
    }

    @Override
    public void run() {
        final long referenceDownloadId = mIntent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        long enqueueId;
        boolean autoInstall;
        String log;
        for (DownloadInstaller.Type type : DownloadInstaller.Type.values()) {
            enqueueId = preferences.getLong(type.getEnqueueIdPreferenceName(), -1);
            autoInstall = preferences.getBoolean(type.getAutoInstallPreferenceName(), false);
            log = type.name() + " enqueueId is " + enqueueId + " autoInstall is " + autoInstall + ", referenceDownloadId " + referenceDownloadId;
            if (autoInstall && (referenceDownloadId == enqueueId)) {
                DownloadManager downloadMgr = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                int downloadStatus = getDownloadStatus(downloadMgr, referenceDownloadId);
                if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) {
                    HrLog.v(TAG, "checkFlagAndInstall: " + log);
                    DownloadInstaller.install(mContext, downloadMgr.getUriForDownloadedFile(referenceDownloadId));
                    DownloadInstaller.clearPreference(preferences, type);
                } else {
                    HrLog.w(TAG, "checkFlagAndInstall: Download fail. " + log);
                }
            } else {
                HrLog.v(TAG, "checkFlagAndInstall: Skip! " + log);
            }
        }
    }

    private static int getDownloadStatus(DownloadManager downloadMgr, long referenceId) {
        HrLog.d(TAG, "getDownloadStatus:");
        int downStatus = -1;
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(referenceId);
        Cursor cursor = downloadMgr.query(query);
        if (DBUtil.notEmptyCursorAndMoveToFirst(cursor)) {
            int statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            downStatus = cursor.getInt(statusIndex);
        } else {
            HrLog.w(TAG, "Empty row");
        }
        DBUtil.closeCursor(cursor);
        return downStatus;
    }
}
