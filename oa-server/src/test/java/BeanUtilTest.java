import com.lee.oa.Application;
import com.lee.oa.mapper.UserMapper;
import com.lee.oa.pojo.User;
import com.lee.oa.util.BeanUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @ClassName BeanUtilTest
 * @Description
 * @Author lihongliang
 * @Date 2025/11/7 10:30
 * @Version 1.0
 */
@SpringBootTest(classes = Application.class)
@SpringJUnitConfig
public class BeanUtilTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void CopyProperties() {
        User user1 = userMapper.selectById(1);

        User user2 = new User();
        user2.setTelephone("654321");
        user2.setName("张三");
        BeanUtil.copyPropertiesWithNonNull(user2, user1);

        System.out.println("user1: " + user1);

    }
}