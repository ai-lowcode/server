package cn.com.axel.oauth.mapper;

import cn.com.axel.oauth.entity.OAuthClient;
import org.apache.ibatis.annotations.Select;

/**
 * @author: axel
 * @date: 2020/2/16 16:05
 */
public interface ClientMapper {
    @Select("select * from sso_client_details where client_id = #{clientId} and del_flag = 0")
    OAuthClient getClientById(String clientId);
}
