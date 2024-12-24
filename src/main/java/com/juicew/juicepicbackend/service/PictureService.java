package com.juicew.juicepicbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juicew.juicepicbackend.model.VO.PictureVO;
import com.juicew.juicepicbackend.model.dto.picture.PictureQueryRequest;
import com.juicew.juicepicbackend.model.dto.picture.PictureUploadRequest;
import com.juicew.juicepicbackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.juicew.juicepicbackend.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author nxbhx
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2024-12-23 23:19:51
*/
public interface PictureService extends IService<Picture> {

    /**
     * 校验图片
     * @param picture
     */
    void validPicture(Picture picture);

    /**
     * 上传图片
     *
     * @param multipartFile
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);


    /**
     * 获取图片包装类（单条）
     * @param picture
     * @param request
     * @return
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 获取图片包装类（分页）
     * @param picturePage
     * @param request
     * @return
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 获取查询对象
     * @param pictureQueryRequest
     * @return
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);
}
