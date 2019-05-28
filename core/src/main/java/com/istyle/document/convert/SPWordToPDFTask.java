package com.istyle.document.convert;

public class SPWordToPDFTask implements Runnable {
    private boolean mInited = false;
    private String mSrcFileName;
    private String mDstFileName;
    private int state;
    private String sysSource;
    public SPWordToPDFTask(String srcFileName, String dstFileName, int state, String sysSource) {
        this.mSrcFileName = srcFileName;
        this.mDstFileName = dstFileName;
        this.state = state;
        this.sysSource = sysSource;
    }

    public String getmSrcFileName() {
        return mSrcFileName;
    }

    public String getmDstFileName() {
        return mDstFileName;
    }

    public int getState() {
        return state;
    }

    public String getSysSource() {
        return sysSource;
    }

    @Override
    public void run() {
        if (!this.mInited) {
            System.out.println(mInited);
            SPDocumentConvert.nativeInitialize(0);
            this.mInited = true;
        }

        this.state = SPDocumentConvert.nativeWordToPDF(this.mSrcFileName, this.mDstFileName);
        if (SPDocumentConvert.mOnResultListener != null) {
            SPDocumentConvert.mOnResultListener.onResult(this);
        }
    }
}
