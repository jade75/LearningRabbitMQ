package ie.dkit.d00216118.demomessage.mapper;


import ie.dkit.d00216118.demomessage.model.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

//    User selectByUserNamePassword(String userName,String password);
User selectByUserNamePassword(@Param("userName") String userName, @Param("password") String password);
}