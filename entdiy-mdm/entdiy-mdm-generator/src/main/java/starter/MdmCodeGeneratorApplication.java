package starter;

import com.entdiy.mdm.entity.User;
import com.entdiy.mybatisplus.generator.MyBatisPlusCodeGenerator;
import com.entdiy.mybatisplus.generator.MyBatisPlusGeneratorProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackageClasses = {User.class})
@EnableConfigurationProperties(MyBatisPlusGeneratorProperties.class)
public class MdmCodeGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MdmCodeGeneratorApplication.class, args);
    }

    @Bean
    public MyBatisPlusCodeGenerator myBatisPlusCodeGenerator() {
        return new MyBatisPlusCodeGenerator(MdmCodeGeneratorApplication.class);
    }
}
