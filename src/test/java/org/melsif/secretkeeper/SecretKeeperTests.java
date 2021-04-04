package org.melsif.secretkeeper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SecretKeeperTests extends AbstractIntegrationTest {

    @Test
    void contextLoads() {
    }

}
