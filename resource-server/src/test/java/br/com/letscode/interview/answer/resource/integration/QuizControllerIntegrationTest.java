package br.com.letscode.interview.answer.resource.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldAuthenticateAndStartAndFinishAQuiz() throws Exception{
        String accessToken = TestUtil.obtainAccessToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quiz/start")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quiz/finish")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldAuthenticateAndTryToFinishAQuizReturningAnError() throws Exception{
        String accessToken = TestUtil.obtainAccessToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quiz/finish")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void shouldTryToStartTwoQuizzesReturningAnError() throws Exception{
        String accessToken = TestUtil.obtainAccessToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quiz/start")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quiz/start")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quiz/finish")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
