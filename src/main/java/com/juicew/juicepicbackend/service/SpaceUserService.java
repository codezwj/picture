package com.juicew.juicepicbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juicew.juicepicbackend.model.VO.SpaceUserVO;
import com.juicew.juicepicbackend.model.VO.SpaceVO;
import com.juicew.juicepicbackend.model.dto.space.SpaceAddRequest;
import com.juicew.juicepicbackend.model.dto.space.SpaceQueryRequest;
import com.juicew.juicepicbackend.model.dto.spaceuser.SpaceUserAddRequest;
import com.juicew.juicepicbackend.model.dto.spaceuser.SpaceUserEditRequest;
import com.juicew.juicepicbackend.model.dto.spaceuser.SpaceUserQueryRequest;
import com.juicew.juicepicbackend.model.entity.Space;
import com.juicew.juicepicbackend.model.entity.SpaceUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.juicew.juicepicbackend.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author nxbhx
* @description 针对表【space_user(空间用户关联)】的数据库操作Service
* @createDate 2025-02-03 02:47:26
*/
public interface SpaceUserService extends IService<SpaceUser> {

    /**
     * 创建空间成员
     * @param spaceUserAddRequest
     */
    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    /**
     * 校验空间成员
     * @param spaceUser
     * @param add 是否为创建时校验，创建时校验更严格
     */
    void validSpaceUser(SpaceUser spaceUser, boolean add);

    /**
     * 获取空间成员包装类，单条
     * @param spaceUser
     * @param request
     * @return
     */
    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    /**
     * 获取空间成员包装类，列表
     * @param spaceUserList
     * @return
     */
    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);

    /**
     * 获取查询对象
     * @param spaceUserQueryRequest
     * @return
     */
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);


}
