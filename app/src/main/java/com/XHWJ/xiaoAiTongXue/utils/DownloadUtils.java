package com.XHWJ.xiaoAiTongXue.utils;

import android.text.TextUtils;

import com.XHWJ.xiaoAiTongXue.callback.OnDownloadListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;

public class DownloadUtils {
    private static DownloadUtils downloadUtil;

    public static DownloadUtils getInstance() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtils();
        }
        return downloadUtil;
    }

    /**
     * @param url      文件下载地址
     * @param fileName 文件名称
     * @param listener 下载的回调
     */
    public void downloadFile(String url, String fileName, final OnDownloadListener listener) {
        File localFileDir = FileUtils.createFileDir();
        if (!TextUtils.isEmpty(url)) {
            OkGo.<File>get(url)
                    .execute(new FileCallback(localFileDir.getPath(), fileName) {
                        @Override
                        public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                            File body = response.body();
                            listener.onDownloadSuccess(body);
                        }

                        @Override
                        public void onError(com.lzy.okgo.model.Response<File> response) {
                            super.onError(response);
                            listener.onDownloadFailed();
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }
                    });
        } else {
            listener.onDownloadFailed();
        }

    }

    /**
     * @param fileDir  文件下载的文件夹
     * @param url      文件下载地址
     * @param fileName 文件名称
     * @param listener 下载的回调
     */
    public void downloadFile(String fileDir, String url, String fileName, final OnDownloadListener listener) {
        File localFileDir = FileUtils.createFileDirOrFile(fileDir);
        if (!TextUtils.isEmpty(url)) {
            OkGo.<File>get(url)
                    .execute(new FileCallback(localFileDir.getPath(), fileName) {
                        @Override
                        public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                            File body = response.body();
                            listener.onDownloadSuccess(body);
                        }

                        @Override
                        public void onError(com.lzy.okgo.model.Response<File> response) {
                            super.onError(response);
                            listener.onDownloadFailed();
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }
                    });
        } else {
            listener.onDownloadFailed();
        }
    }
}
