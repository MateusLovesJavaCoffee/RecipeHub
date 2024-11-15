package br.com.mateusulrich.recipeservice;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@ComponentScan(
        basePackages = "br.com.mateusulrich.recipeservice",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = {".*Repository", ".*Service"})
        }
)
@DataJpaTest
@ExtendWith(PostgresCleanUpExtension.class)
@Tag("integrationTest")
public @interface RepositoryTests {
}
