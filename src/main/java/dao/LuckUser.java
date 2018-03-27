package dao;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:20 2018/3/27
 */
public interface LuckUser {
    //获取幸运用户
    public List<LuckUser> getLuckUser(Timestamp startTime, Timestamp endTime);
    //插入幸运用户
    public int insertLuckUser(String user_list);
    //查询幸运用户
    public String getLuckUserList();
}
