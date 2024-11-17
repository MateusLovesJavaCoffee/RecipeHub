package br.com.mateusulrich.recipeservice;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")

@SpringBootTest
@ExtendWith(PostgresCleanUpExtension.class)
@Tag("integrationTest")
@Transactional
public @interface ServiceIntegrationTests {
}
