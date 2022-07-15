package br.com.letscode.interview.answer.resource.controller;

import br.com.letscode.interview.answer.resource.domain.RankingPosition;
import br.com.letscode.interview.answer.resource.service.RankingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RankingControllerTest {

    @Mock
    private RankingService rankingService;

    @InjectMocks
    private RankingController rankingController = new RankingController();

    @Test
    public void shouldNotThrowExceptionOnGetQuestion() {
        List<RankingPosition> rankingPositionList = new ArrayList<>();
        when(rankingService.getRanking()).thenReturn(rankingPositionList);
        assertEquals(rankingPositionList, rankingController.getRanking());
    }
}
