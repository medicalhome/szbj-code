package com.founder.cdr.dao;

import java.util.List;

import com.founder.cdr.dto.UserAuthDto;
import com.founder.fasf.core.util.daohelper.annotation.Arguments;

public interface AccessControlDao
{
    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 ADD BEGIN
    // 访问控制实现
    @Arguments({ "userId" })
    public List<UserAuthDto> selectUserAuths(String userId);
    // $[BUG]0009663 ADD END

}
