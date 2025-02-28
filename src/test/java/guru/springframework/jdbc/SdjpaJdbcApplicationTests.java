package guru.springframework.jdbc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled // No H2 context configured
@SpringBootTest
class SdjpaJdbcApplicationTests {

	@Test
	void contextLoads(ApplicationContext appCxt) {
		assertThat(appCxt).isNotNull();
	}

}
