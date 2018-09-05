package com.pie.core.net.request;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.pie.core.app.Pie;
import com.pie.core.net.callback.IUpdateBack;
import com.pie.core.util.system.SDCardUtils;

import java.io.File;

/**
 * @author:zjh
 * @date:2018/9/5
 * @Description：版本更新
 */
public class UpdateRequest {

    private DownloadManager mDownloadManager;
    private String mUrl;
    private String mTitle;
    private String mDescription;
    private String mApkName;
    private Context mContext;

    //下载的ID
    private long mDownloadId;

    public UpdateRequest(String url){
        this.mUrl = url;
        this.mApkName = String.valueOf(System.currentTimeMillis());
        mContext = Pie.getApplicationContext();
    }

    public UpdateRequest withNotifiTitle(String title){
        this.mTitle = title;
        return this;
    }

    public UpdateRequest withNotifiDescription(String description){
        this.mDescription = description;
        return this;
    }

    public UpdateRequest withDownLoadFileName(String fileName){
        this.mApkName = fileName;
        return this;
    }


    public void request(String name,IUpdateBack updateBack){
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mUrl));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        if (!TextUtils.isEmpty(mTitle)){
            request.setTitle(mTitle);
        }
        if (!TextUtils.isEmpty(mDescription)){
            request.setDescription(mDescription);
        }
        request.setVisibleInDownloadsUi(true);
        //设置下载的路径
        String path = SDCardUtils.getDiskCacheDir(mContext,"").getAbsolutePath();
        request.setDestinationInExternalPublicDir(path, mApkName);

        //获取DownloadManager
        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        mDownloadId = mDownloadManager.enqueue(request);

        //注册广播接收者，监听下载状态
        mContext.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };


    //检查下载状态
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(mDownloadId);
        Cursor c = mDownloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    Log.e("jason", "正在暂停");
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    Log.e("jason", "正在延迟");
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    Log.e("jason", "正在下载...");
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                    installAPK();
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        c.close();
    }


    //下载到本地后执行安装
    private void installAPK() {
        //获取下载文件的Uri
        Uri downloadFileUri = mDownloadManager.getUriForDownloadedFile(mDownloadId);
        if (downloadFileUri != null) {
            Intent intent= new Intent(Intent.ACTION_VIEW);
            File apkFile = queryDownloadedApk(mContext, mDownloadId);
            //版本在7.0以上是不能直接通过uri访问的
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(mContext, "com.sowe.guardstudents.provider.update.apk", apkFile);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            }else{
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    //通过downLoadId查询下载的apk，解决6.0以后安装的问题
    public static File queryDownloadedApk(Context context, long downloadId) {
        File targetApkFile = null;
        DownloadManager downloader = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        long downloadId = PreferencesUtils.getLong(context, DownloadActivity.DOWNLOAD_ID);
        if (downloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloader.query(query);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;
    }



}
