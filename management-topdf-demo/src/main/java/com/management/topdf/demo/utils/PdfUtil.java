package com.management.topdf.demo.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author xiqiuwei
 * @Date Created in 21:02 2019/9/2
 * @Description
 * @Modified By:
 */
@SuppressWarnings("all")
public class PdfUtil {

    public static void ExportPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //告诉浏览器用什么软件可以打开此文件
        //设置响应格式等
        response.setContentType("application/pdf;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");

        // 设置导出的pdf
        // String title = URLEncoder.encode("霸气的奚大哥","UTF-8");
        String title = new String("霸气的奚大哥".getBytes("UTF-8"),"ISO-8859-1");
        //下载文件的默认名称
        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"",title+".pdf"));
        // 宋体中文嵌入式
        BaseFont chineseFront = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        // 设置纸张为A4大小
        Rectangle rectangle = new Rectangle(PageSize.A4);
        // 创建文档实例
        Document document = new Document(rectangle);
        Font normalFont = new Font(chineseFront, 11, Font.NORMAL); // 正常字体
        Font boldFont = new Font(chineseFront, 11, Font.BOLD); // 加粗字体
        Font firstTitleFont = new Font(chineseFront, 22, Font.BOLD); // 一级标题
        Font secondTitleFont = new Font(chineseFront, 15, Font.BOLD, CMYKColor.BLUE); // 二级标题
        Font underLineFont = new Font(chineseFront, 11, Font.UNDERLINE); // 带下划线
        //设置字体
        Font FontChinese24 = new Font(chineseFront, 24, Font.BOLD);
        Font FontChinese18 = new Font(chineseFront, 18, Font.BOLD);
        Font FontChinese16 = new Font(chineseFront, 16, Font.BOLD);
        Font FontChinese12 = new Font(chineseFront, 12, Font.NORMAL);
        Font FontChinese11Bold = new Font(chineseFront, 11, Font.BOLD);
        Font FontChinese11 = new Font(chineseFront, 11, Font.ITALIC);
        Font FontChinese11Normal = new Font(chineseFront, 11, Font.NORMAL);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.newPage();
        Paragraph firstParagraph = new Paragraph("github网址", firstTitleFont);
        // 设置行高
        firstParagraph.setLeading(0);
        // 标题居中
        firstParagraph.setAlignment(Element.ALIGN_CENTER);
        // 将当前标题加到文档中
        document.add(firstParagraph);
        // 标题与内容空开一行
        Paragraph blankRow = new Paragraph(18f, " ", FontChinese11);
        document.add(blankRow);
        // 将文档设置为两列
        PdfPTable pdfPTable1 = new PdfPTable(2);
        // 设置列宽
        int width[] = {80, 60};
        // 信息简介行
        pdfPTable1.setWidths(width);
        String name = "奚秋伟";
        String age = "27";
        PdfPCell pdfPCellName = new PdfPCell(new Paragraph("姓名：" + name, boldFont));
        PdfPCell pdfPCellAge = new PdfPCell(new Paragraph("年龄：" + age, boldFont));
        // 单元格的边框
        pdfPCellName.setBorder(0);
        pdfPCellAge.setBorder(0);
        pdfPTable1.addCell(pdfPCellName);
        pdfPTable1.addCell(pdfPCellAge);
        document.add(pdfPTable1);
        int width1[] = {140};
        // 描述行
        PdfPTable pdfPTable3 = new PdfPTable(1);
        pdfPTable3.setWidths(width1);
        String desc = "这是我个人学习的一个github仓库，供大家参考";
        PdfPCell pdfPCellDesc = new PdfPCell(new Paragraph("描述：" + desc, boldFont));
        pdfPCellDesc.setBorder(0);
        pdfPTable3.addCell(pdfPCellDesc);
        document.add(pdfPTable3);
        // 详细内容行
        PdfPTable pdfPTable2 = new PdfPTable(2);
        pdfPTable2.setWidths(width);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date();
        String formatTime = format.format(date);
        String url = "https://github.com/xiqiuwei/management.git";
        PdfPCell pdfPCellUrl = new PdfPCell(new Paragraph("当前的github仓库：" + url, boldFont));
        PdfPCell pdfPCellTime = new PdfPCell(new Paragraph("当前的北京时间：" + formatTime, boldFont));
        pdfPCellUrl.setBorder(0);
        pdfPCellTime.setBorder(0);
        pdfPTable2.addCell(pdfPCellUrl);
        pdfPTable2.addCell(pdfPCellTime);
        document.add(pdfPTable2);
        document.close();
    }
}
