//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.istyle.document.convert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SPDocumentConvert {
    private ExecutorService executorService;
    public static SPDocumentConvert.OnConvertResultListener mOnResultListener;

    static {
        System.loadLibrary("istyledocconvert");
    }

    private SPDocumentConvert() {
        this.executorService = null;
    }

    public static final SPDocumentConvert getInstance() {
        return SPDocumentConvert.SingletonHolder.INSTANCE;
    }

    public void createTaskPool(int nCount) {
        if (this.executorService == null) {
            this.executorService = Executors.newFixedThreadPool(nCount);
        }

    }

    public void destroyTaskPool() {
        if (this.executorService != null) {
            this.executorService.shutdown();
        }

    }

    public void addTask(Runnable task) {
        if (this.executorService != null) {
            this.executorService.submit(task);
        }

    }

    public static native int nativeInitialize(int var0);

    public static native int nativeFinalize(int var0);

    public static native int nativeWordToPDF(String var0, String var1);

    public interface OnConvertResultListener {
        void onResult(String var1, String var2, int var3);
        void onResult(SPWordToPDFTask toPDFTask);
    }

    private static class SingletonHolder {
        private static final SPDocumentConvert INSTANCE = new SPDocumentConvert();
        private SingletonHolder() {}
    }
}
