package spring_boot.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import spring_boot.demo.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modify,other) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModify},#{other})")
    void insertUser(User user);
    @Select("select * from user where token = #{token}")
    User findUserByToken(@Param("token") String token);
}
