package com.sysco.event.platform.knowledge.graph.core.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testTokenGeneration() throws Exception {
        mvc.perform(get("/token?username=TestUser"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").isString())
                        .andDo(result -> Assertions.assertTrue(result.getResponse().getContentAsString().startsWith("Bearer")));
    }

    @Test
    void testTokenGenerationWithoutUserName() throws Exception {
        mvc.perform(get("/token"))
                        .andExpect(status().isBadRequest());
    }
}
