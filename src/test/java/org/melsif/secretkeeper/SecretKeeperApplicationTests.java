package org.melsif.secretkeeper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SecretKeeperApplicationTests extends AbstractIntegrationTest {

    @Test
    void contextLoads() {
    }

}
