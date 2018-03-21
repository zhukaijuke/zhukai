package com.zhukai.common.cups;

import java.io.InputStream;
import java.net.URL;

import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;
import org.cups4j.PrintJob;
import org.cups4j.PrintJobAttributes;
import org.cups4j.PrintRequestResult;
import org.springframework.util.StringUtils;

public class CupsPrinterUtil {
	
	/***
     * 调取打印方法
     * 
     * @param parameters
     * @return
     * @throws Exception
     */
    public static int printTask(PrintParameters parameters) throws Exception {
        CupsPrinter cp = getPrint(parameters.getHttpUrl(), parameters.getHttpPort(), parameters.getPrinterName());
        String userName = StringUtils.isEmpty(parameters.getUserName()) ? "System" : parameters.getUserName();
        int jobid = printJob(cp, parameters.getFileName(), userName, parameters.getInputStream(), parameters.getPrintCount());
        return jobid;
    }

    /**
     * 获取打印任务状态
     * 
     * @param host ip地址
     * @param port 端口
     * @param jobId 任务ID
     * @return
     * @throws Exception
     */
    public static PrintJobAttributes getJobStatus(String host, int port, int jobId) throws Exception {
        CupsClient c = new CupsClient(host, port);
        PrintJobAttributes pa = c.getJobAttributes(jobId);
        return pa;
    }

    /**
     * 打印
     * 
     * @param cp 打印服务器
     * @param jobName 任务名称
     * @param userName 用户名
     * @param input 文件流
     * @return 打印任务ID
     * @throws Exception
     */
    public static int printJob(CupsPrinter cp, String jobName, String userName, InputStream input, int printCount) throws Exception {
        // 没有打印机直接反馈
        if (null == cp) {
            return -1;
        }
        PrintJob printJob = new PrintJob.Builder(input).jobName(jobName).userName(userName).copies(printCount).build();
        // 打印
        PrintRequestResult rs = cp.print(printJob);
        if (rs.getJobId() > 0) {
            return rs.getJobId();
        }
        return -1;
    }
	
	/**
     * 获取打印机
     * 
     * @param host ip地址
     * @param port 端口
     * @param printName 打印机名称
     * @return 打印机
     * @throws Exception
     */
	public static CupsPrinter getPrint(String host, int port, String printName) throws Exception {
		CupsClient c = new CupsClient(host, port);
		URL printUrl = new URL("http://" + host + ":" + port + "/printers/" + printName);
		CupsPrinter cp = c.getPrinter(printUrl);
		return cp;
	}
    
}
