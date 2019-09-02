package com.management.topdf.demo.controller;

import com.management.topdf.demo.utils.PdfUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author xiqiuwei
 * @Date Created in 19:46 2019/9/2
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping("pdf")
public class PdfDemoController {

    @GetMapping ("exportPdf")
    public void exportPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PdfUtil.ExportPdf(request,response);
    }

}
