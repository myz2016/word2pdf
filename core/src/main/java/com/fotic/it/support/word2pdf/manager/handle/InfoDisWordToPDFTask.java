/** Copyright (c) 2018 XXX. All Rights Reserved. */

package com.fotic.it.support.word2pdf.manager.handle;

import com.istyle.document.convert.SPDocumentConvert;

/** 
 * Title : InfoDisWordToPDFTask.java 
 * 信息披露word转PDF
 *
 * @author jiaxiaofei
 * @date 2018-8-28 下午1:59:38
 * @version 1.0
 * @since JDK 1.6
 */
public class InfoDisWordToPDFTask implements Runnable {
	public boolean mInited = false;
	public String mSrcFileName;
	public String mDstFileName;

	public InfoDisWordToPDFTask(String srcFileName, String dstFileName) {
		this.mSrcFileName = srcFileName;
		this.mDstFileName = dstFileName;
	}

	@Override
	public void run() {
		if (!(this.mInited)) {
			SPDocumentConvert.nativeInitialize(0);
			this.mInited = true;
		}
		SPDocumentConvert.nativeWordToPDF(this.mSrcFileName, this.mDstFileName);
	}
}

