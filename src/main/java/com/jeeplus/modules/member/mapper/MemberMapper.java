/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.member.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.member.entity.Member;

/**
 * 会员信息MAPPER接口
 * @author zyh
 * @version 2018-03-16
 */
@MyBatisMapper
public interface MemberMapper extends BaseMapper<Member> {
	
}