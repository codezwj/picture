package com.juicew.juicepicbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juicew.juicepicbackend.model.entity.Picture;
import com.juicew.juicepicbackend.service.PictureService;
import com.juicew.juicepicbackend.mapper.PictureMapper;
import org.springframework.stereotype.Service;

/**
* @author nxbhx
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2024-12-23 23:19:51
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{

}




