package br.com.letscode.interview.answer.resource.domain;

public class RankingPosition implements Comparable<RankingPosition>{
    String player;
    Double score;

    public RankingPosition(String player, Double score){
        this.score = score;
        this.player = player;
    }

    @Override
    public int compareTo(RankingPosition rankingPosition) {
        return rankingPosition.score.compareTo(this.score);
    }
}