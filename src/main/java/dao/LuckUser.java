package dao;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:20 2018/3/27
 */
public interface LuckUser {
    public List<LuckUser> getLuckUser(Timestamp startTime, Timestamp endTime);
}
