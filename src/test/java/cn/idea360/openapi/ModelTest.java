package cn.idea360.openapi;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.ParseOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author cuishiying
 */
@Slf4j
class ModelTest {

    @Test
    void build() {
        String fileName = "openapi.json";
        try (InputStream inputStream = ModelTest.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                log.error("文件未找到: {}", fileName);
                return;
            }

            String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            log.info("文件内容:\n {}", content);

            OpenAPIParser parser = new OpenAPIParser();
            ParseOptions options = new ParseOptions();
            options.setResolve(true);
            OpenAPI openAPI = parser.readContents(content, null, options).getOpenAPI();
            log.info("openapi:\n {}", openAPI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
