package spring_boot.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import spring_boot.demo.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modify) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModify})")
    void insertUser(User user);

}
