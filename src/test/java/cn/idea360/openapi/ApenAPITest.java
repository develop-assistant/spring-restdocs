package cn.idea360.openapi;

import cn.idea360.openapi.controller.UserController;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.MethodJavadoc;
import com.github.therapi.runtimejavadoc.ParamJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author cuishiying
 */
@Slf4j
class ApenAPITest {

    @Test
    void init() {
        log.info("基于JavaDoc输出OpenAPI文档");
    }

    @SneakyThrows
    @DisplayName("基于JavaDoc和Spring注解生成OpenAPI文档")
    @Test
    void openapi() {
        Class<UserController> clazz = UserController.class;
        // 获取类注释
        ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(clazz);
        log.info("Class: {}", classJavadoc.getComment());
        if (clazz.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
            String[] path = requestMapping.value();
            log.info("BaseURL: {}", Arrays.toString(path));
        }

        for (Method method : clazz.getDeclaredMethods()) {
            // 获取方法的Javadoc注释
            MethodJavadoc methodJavadoc = RuntimeJavadoc.getJavadoc(method);

            // 打印方法名称
            log.info("Method: {}", method.getName());

            // 打印方法的Javadoc注释
            log.info("Description: {}", methodJavadoc.getComment());

            // 使用Spring的ParameterNameDiscoverer获取参数名
            ParameterNameDiscoverer parameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

            // 获取方法的参数
            Parameter[] parameters = method.getParameters();

            // 打印参数名和参数对象
            if (parameterNames != null && parameters.length == parameterNames.length) {
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    String parameterName = parameterNames[i];
                    log.info("Parameter name: {}", parameterName);
                    log.info("Parameter type: {}", parameter.getType().getName());
                    // 打印参数的Javadoc注释
                    Map<String, ParamJavadoc> paramJavadocMap = methodJavadoc.getParams().stream().collect(Collectors.toMap(ParamJavadoc::getName, Function.identity()));
                    log.info("Parameter description: {}", (Objects.isNull(paramJavadocMap.get(parameterName)) ? "No description" : paramJavadocMap.get(parameterName).getComment()));
                }
            } else {
                log.info("No parameter names found or parameters length mismatch.");
            }
        }
    }

}
