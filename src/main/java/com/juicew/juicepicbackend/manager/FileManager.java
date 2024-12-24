package com.juicew.juicepicbackend.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.juicew.juicepicbackend.common.ResultUtils;
import com.juicew.juicepicbackend.config.CosClientConfig;
import com.juicew.juicepicbackend.exception.BusinessException;
import com.juicew.juicepicbackend.exception.ErrorCode;
import com.juicew.juicepicbackend.exception.ThrowUtils;
import com.juicew.juicepicbackend.model.dto.file.UploadPictureResult;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FileManager {
    //通用文件上传方法

    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private CosManager cosManager;

    /**
     * 上传图片
     *
     * @param multipartFile
     * @param uploadPathPrefix
     * @return
     */
    public UploadPictureResult uploadPicture(MultipartFile multipartFile, String uploadPathPrefix){
        //校验图片
        validPicture(multipartFile);

        //图片上传地址
        String uuid = RandomUtil.randomString(16);
        String originalFilename = multipartFile.getOriginalFilename();
        //自己拼接文件上传路径，而不是使用原始名称，可以增强安全性,这里使用时间加uuid拼接原文件后缀
        String uploadFilename = String.format("%s_%S.%s", DateUtil.formatDate(new Date()),uuid,
                FileUtil.getSuffix(originalFilename));
        String uploadPath = String.format("/%s/%s",uploadPathPrefix,uploadFilename);
        //解析结果并返回
        //CosManager接收参数为File类，所以先用File创建一个临时文件，然后把multipartFile传给file，再通过Cos上传

        File file = null;
        try {
            file = File.createTempFile(uploadPathPrefix,null);
            multipartFile.transferTo(file);
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            //获取图片信息对象
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();

            int pictureWidth = imageInfo.getWidth();
            int pictureHeight = imageInfo.getHeight();
            //四舍五入保留一位
            double pictureScale = NumberUtil.round(pictureWidth * 1.0 / pictureHeight, 2).doubleValue();

            //封装返回结果
            UploadPictureResult uploadPictureResult = new UploadPictureResult();
            uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
            uploadPictureResult.setPicName(FileUtil.mainName(originalFilename));
            uploadPictureResult.setPicSize(FileUtil.size(file));
            uploadPictureResult.setPicWidth(pictureWidth);
            uploadPictureResult.setPicHeight(pictureHeight);
            uploadPictureResult.setPicScale(pictureScale);
            uploadPictureResult.setPicFormat(imageInfo.getFormat());

            //返回可访问的地址
            return uploadPictureResult;
        } catch (Exception e) {
            log.error("图片上传到对象存储失败",e);
            throw new BusinessException
                    (ErrorCode.SYSTEM_ERROR, "上传失败");
        }
        finally {
            //临时文件清理
            deleteTempFile(file);
        }

        //临时文件清理

    }



    /**
     * 校验图片
     *
     * @param multipartFile
     */
    public void validPicture(MultipartFile multipartFile){
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR,"文件不能为空");
        //1.校验文件大小
        long fileSize = multipartFile.getSize();
        final long ONE_M = 1024 * 1024;
        ThrowUtils.throwIf(fileSize > 2 * ONE_M,ErrorCode.PARAMS_ERROR,"文件不能超过2M");
        //2.校验文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        //允许的后缀
        final List<String> ALLOW_FORMAT_LIST = Arrays.asList("jpg","jpeg","png","webp");
        ThrowUtils.throwIf(!ALLOW_FORMAT_LIST.contains(fileSuffix),ErrorCode.PARAMS_ERROR,"文件类型错误");

    }


    /**
     * 清理临时文件
     * @param file
     */
    private static void deleteTempFile(File file) {
        if(file != null){
            boolean delete = file.delete();
            if(!delete){
                log.error("file delete fail, filepath = {}",file.getAbsolutePath());
            }
        }
    }



}



