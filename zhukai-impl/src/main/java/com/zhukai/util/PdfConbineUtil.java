package main.java.com.zhukai.util;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 合并jasper生成的PDF的byte[]工具类
 */
public class PdfConbineUtil {

    private Collection byteColl = new ArrayList();

    public static PdfConbineUtil getInstance() {
        return new PdfConbineUtil();
    }

    /**
     * 合并
     */
    public byte[] conbine() throws IOException, DocumentException {
        ByteArrayOutputStream conbined = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter pdfwriter = PdfWriter.getInstance(document, conbined);
        document.open();
        PdfContentByte pcb = pdfwriter.getDirectContent();
        PdfReader reader = null;
        PdfImportedPage page = null;
        Iterator ite = byteColl.iterator();
        while (ite.hasNext()) {
            // 取得模板的内容
            reader = new PdfReader((byte[]) ite.next());
            for (int i = 0; i < reader.getNumberOfPages(); i++) {
                document.newPage();
                page = pdfwriter.getImportedPage(reader, i + 1);
                // 添加模板
                pcb.addTemplate(page, 0, 0);
            }
        }
        document.close();
        return conbined.toByteArray();
    }

    /**
     * 变换方向
     */
    public byte[] conbineSide() throws IOException, DocumentException {
        ByteArrayOutputStream conbined = new ByteArrayOutputStream();
        // 横写设定
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter pdfwriter = PdfWriter.getInstance(document, conbined);
        document.open();
        PdfContentByte pcb = pdfwriter.getDirectContent();
        PdfReader reader = null;
        PdfImportedPage page = null;
        Iterator ite = byteColl.iterator();
        while (ite.hasNext()) {
            // 取得模板的内容
            reader = new PdfReader((byte[]) ite.next());
            for (int i = 0; i < reader.getNumberOfPages(); i++) {
                document.newPage();
                page = pdfwriter.getImportedPage(reader, i + 1);
                // 添加模板
                pcb.addTemplate(page, 0, 0);
            }
        }
        document.close();
        return conbined.toByteArray();
    }

    /**
     * 添加
     * @param byteArray
     */
    public PdfConbineUtil add(byte[] byteArray) {
        byteColl.add(byteArray);
        return this;
    }

    public PdfConbineUtil clear() {
        byteColl.clear();
        return this;
    }
}