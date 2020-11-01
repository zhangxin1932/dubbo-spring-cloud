package com.zy;

import com.alibaba.fastjson.JSON;
import com.zy.vo.TbUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import javax.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestUserController {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginAndLogout() throws Exception {
        TbUser user = new TbUser();
        user.setUsername("tom");
        user.setPassword("123456");

        ResultActions actions = this.mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .content(JSON.toJSONString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        String sessionId = actions.andReturn().getResponse().getCookie("authId").getValue();

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/user/logout")
                .cookie(new Cookie("authId", sessionId)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
