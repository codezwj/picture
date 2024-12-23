package com.juicew.juicepicbackend.controller;

import com.juicew.juicepicbackend.annotation.AuthCheck;
import com.juicew.juicepicbackend.common.BaseResponse;
import com.juicew.juicepicbackend.common.ResultUtils;
import com.juicew.juicepicbackend.constant.UserConstant;
import com.juicew.juicepicbackend.exception.BusinessException;
import com.juicew.juicepicbackend.exception.ErrorCode;
import com.juicew.juicepicbackend.manager.CosManager;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.utils.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private CosManager cosManager;

    /**
     * 测试文件上传
     *
     * @param multipartFile
     * @return
     */

    @PostMapping("/test/upload")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> testUploadFile(@RequestPart("file")MultipartFile multipartFile){

        //获取文件名称
        String fileName = multipartFile.getOriginalFilename();
        //文件存储目标路径
        String filePath = String.format("/test/%s", fileName);

        //CosManager接收参数为File类，所以先用File创建一个临时文件，然后把multipartFile传给file，再通过Cos上传
        File file = null;
        try {
            file = File.createTempFile(filePath,null);
            multipartFile.transferTo(file);
            cosManager.putObject(filePath,file);
            //返回可访问的地址
            return ResultUtils.success(filePath);
        } catch (IOException e) {
            log.error("file upload error, filepath = " + filePath,e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        }
        finally {
            if(file != null){
                boolean delete = file.delete();
                if(!delete){
                    log.error("file delete fail, filepath = {}",filePath);
                }
            }
        }

    }



    /**
     * 测试文件下载
     *
     * @param filepath 文件路径
     * @param response 响应对象
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/test/download/")
    public void testDownloadFile(String filepath, HttpServletResponse response) throws IOException {
        COSObjectInputStream cosObjectInput = null;
        try {
            COSObject cosObject = cosManager.getObject(filepath);
            cosObjectInput = cosObject.getObjectContent();
            // 处理下载到的流
            byte[] bytes = IOUtils.toByteArray(cosObjectInput);
            // 设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filepath);
            // 写入响应
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("file download error, filepath = " + filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "下载失败");
        } finally {
            if (cosObjectInput != null) {
                cosObjectInput.close();
            }
        }
    }


}
