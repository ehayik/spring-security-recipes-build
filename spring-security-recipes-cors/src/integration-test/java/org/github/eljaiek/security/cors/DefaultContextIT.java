package org.github.eljaiek.security.cors;

import org.github.eljaiek.security.core.SecurityConfigurerProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class DefaultContextIT {

    @Autowired
    private ApplicationContext context;

    @Test
    public void applicationContext() {
        assertThat(context.getBean(SecurityConfigurerProvider.class))
                .isNotNull();
    }
}
