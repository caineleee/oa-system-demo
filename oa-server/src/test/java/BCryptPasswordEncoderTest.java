import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName BCryptPasswordEncoderTest
 * @Description
 * @Author lihongliang
 * @Date 2025/9/27 18:01
 * @Version 1.0
 */

public class BCryptPasswordEncoderTest {

    @Test
    public void test() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String str = "finance";
        String str2 = passwordEncoder.encode( str);
        System.out.println(str);
        System.out.println(str2);
        Assertions.assertTrue(passwordEncoder.matches(str, str2));
    }
}
