package com.brieffeed.back;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostRestTest {

  @Autowired
  private MockMvc mockMvc;

  // Тестирование аутентификации с правильными учетными данными
  @Test
  public void testAuthenticationPositive() throws Exception {
    this.mockMvc.perform(
        post("/api/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "    \"username\": \"user\",\n"
                + "    \"password\": \"user_password\"\n"
                + "}"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  // Тестирование аутентификации с неверными учетными данными
  @Test
  public void testAuthenticationNegative() throws Exception {
    this.mockMvc
        .perform(
            post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"
                    + "    \"username\": \"admin\",\n"
                    + "    \"password\": \"wrongpwd\"\n"
                    + "}"))
        .andDo(print()).andExpect(status().is4xxClientError());
  }

}
