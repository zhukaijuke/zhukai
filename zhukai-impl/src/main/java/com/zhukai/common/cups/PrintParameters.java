package com.zhukai.common.cups;

import java.io.InputStream;

public class PrintParameters {

	/** 打印的文件流 */
	private InputStream inputStream;
	/** 打印http地址 */
	private String httpUrl;
	/** 打印http 端口号 */
	private Integer httpPort;
	/** 打印机名称 **/
    private String printerName;
    /** 打印机份数 **/
    private int printCount = 1;
	/** 操作人 */
	private String userName;
	/** 生成PDF文件名称 (单据CODE) **/
    private String fileName;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public Integer getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(Integer httpPort) {
		this.httpPort = httpPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
