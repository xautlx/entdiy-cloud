package com.entdiy.common.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("ut")
@Transactional
@Rollback
public class SpringBootMVCTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @SneakyThrows
    protected String getContentString(ResultActions resultActions) {
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        MvcResult mvcResult = resultActions
                //打印结果
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        if (response.startsWith("{\"success\":false")) {
            throw new RuntimeException(response);
        }
        return response;
    }

    @SneakyThrows
    protected String toJson(Object target) {
        return objectMapper.writeValueAsString(target);
    }

    @SneakyThrows
    protected <T> T fromJson(String json, Class<T> clazz) {
        String result = StringUtils.substringBetween(json, "\"result\":", ",\"timestamp\"");
        return objectMapper.readValue(result, clazz);
    }
}
