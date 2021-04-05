package org.melsif.secretkeeper.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.melsif.secretkeeper.web.GlobalExceptionHandler;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;
import java.util.Collections;

public abstract class AbstractControllerTest {

    @BeforeEach
    public void setUp() {
        var messageSource = new StaticMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(getTestedController())
            .setHandlerExceptionResolvers(restErrorHandler(messageSource))
            .setMessageConverters(jacksonConverter())
            .build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    protected abstract Object getTestedController();

    private ExceptionHandlerExceptionResolver restErrorHandler(MessageSource messageSource) {
        var exceptionResolver = new ExceptionHandlerExceptionResolver() {
            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod,
                                                                              Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(GlobalExceptionHandler.class).resolveMethod(exception);
                if (method != null) {
                    return new ServletInvocableHandlerMethod(new GlobalExceptionHandler(messageSource), method);
                }
                return super.getExceptionHandlerMethod(handlerMethod, exception);
            }
        };
        exceptionResolver.setMessageConverters(Collections.singletonList(jacksonConverter()));
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

    private MappingJackson2HttpMessageConverter jacksonConverter() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
