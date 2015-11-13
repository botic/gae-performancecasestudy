package io.botic.response;

public class BenchmarkResult {
    private double score;
    private String text;

    public BenchmarkResult() {
    }

    public BenchmarkResult(String text, double score) {
        this.text = text;
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
