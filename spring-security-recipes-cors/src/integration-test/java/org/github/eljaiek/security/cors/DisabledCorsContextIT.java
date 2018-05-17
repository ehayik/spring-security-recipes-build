package org.github.eljaiek.security.cors;

import org.github.eljaiek.security.core.SecurityConfigurerProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@ActiveProfiles("cors-disabled")
@SpringBootTest(classes = TestApplication.class)
public class DisabledCorsContextIT {

    @Autowired
    private ApplicationContext context;

    @Test
    public void applicationContext() {
        assertThatThrownBy(() -> context.getBean(SecurityConfigurerProvider.class))
                .isExactlyInstanceOf(NoSuchBeanDefinitionException.class);
    }
}
