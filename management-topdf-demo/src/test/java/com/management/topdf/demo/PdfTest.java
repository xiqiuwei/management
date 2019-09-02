package com.management.topdf.demo;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Author xiqiuwei
 * @Date Created in 19:48 2019/9/2
 * @Description
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagementToPDFDemoApplication.class)
public class PdfTest {

    @Test
    public void test1() throws IOException, DocumentException {
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
        // 设置导出的pdf
        String title = "霸气的奚大哥";
    }
}
