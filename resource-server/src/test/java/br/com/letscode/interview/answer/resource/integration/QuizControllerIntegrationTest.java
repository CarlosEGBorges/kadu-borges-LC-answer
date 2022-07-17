package br.com.letscode.interview.answer.resource.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldAuthenticateAndStartAndFinishAQuiz() throws Exception{
        String accessToken = TestUtil.obtainAccessToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/start")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/finish")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldAuthenticateAndTryToFinishAQuizReturningAnError() throws Exception{
        String accessToken = TestUtil.obtainAccessToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/finish")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void shouldTryToStartTwoQuizzesReturningAnError() throws Exception{
        String accessToken = TestUtil.obtainAccessToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/start")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/start")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        // finish the Quiz opened
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/finish")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldStartAQuizAndGetAQuestion() throws Exception{
        String accessToken = TestUtil.obtainAccessToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/start")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quizzes/questions")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/finish")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldStartAQuizAndGetAQuestionAndRespondToIt() throws Exception{
        String accessToken = TestUtil.obtainAccessToken();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/start")
                .header("Authorization", "Bearer " + accessToken)
                .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        String mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/questions")
                .header("Authorization", "Bearer " + accessToken)
                .accept("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        Map<String, Object> cardOneImdb = (Map<String, Object>) jsonParser.parseMap(mvcResult).get("cardOne");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/quizzes/finish")
                .header("Authorization", "Bearer " + accessToken)
                .content("{imdbId:" + cardOneImdb.get("imdbId") + "}")
                .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
