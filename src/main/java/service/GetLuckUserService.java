package service;

import dao.LuckUser;

import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 17:22 2018/3/26
 */
public interface GetLuckUserService {
    //获取幸运用户
    public String getLuckUser ();
    //插入幸运用户
    public int  insertLuckUser(String user_list);
    //查询幸运用户
    public String getLuckUserList();

}
