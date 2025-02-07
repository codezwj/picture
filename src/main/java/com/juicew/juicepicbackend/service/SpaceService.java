package com.juicew.juicepicbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juicew.juicepicbackend.model.VO.SpaceVO;
import com.juicew.juicepicbackend.model.dto.space.SpaceAddRequest;
import com.juicew.juicepicbackend.model.dto.space.SpaceQueryRequest;
import com.juicew.juicepicbackend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.juicew.juicepicbackend.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author nxbhx
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2024-12-31 00:36:41
*/
public interface SpaceService extends IService<Space> {

    /**
     * 创建空间
     * @param spaceAddRequest
     * @param userLogin
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User userLogin);

    /**
     * 校验空间
     * @param space
     * @param add 是否为创建时校验，创建时校验更严格
     */
    void validSpace(Space space, boolean add);

    /**
     * 获取空间包装类，单条
     * @param space
     * @param request
     * @return
     */
    SpaceVO getSpaceVO(Space space,HttpServletRequest request);

    /**
     * 获取空间包装类，分页
     * @param spacePage
     * @param request
     * @return
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 获取查询对象
     * @param spaceQueryRequest
     * @return
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);


    /**
     * 根据空间级别填充空间对象
     * @param space
     */
    void fillSpaceBySpaceLevel(Space space);

    /**
     * 校验空间权限
     * @param loginUser
     * @param space
     */
    void checkSpaceAuth(User loginUser, Space space);

}
