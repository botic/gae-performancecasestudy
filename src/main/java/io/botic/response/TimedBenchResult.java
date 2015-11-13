package io.botic.response;


public class TimedBenchResult {
    private long startNanos;

    private String text;
    private long msDuration;
    private double msPerWrite;

    public TimedBenchResult() {
        this.startNanos = System.nanoTime();
    }

    public TimedBenchResult(String text) {
        this.text = text;
        this.startNanos = System.nanoTime();
    }

    public void start() {
        this.startNanos = System.nanoTime();
    }

    public TimedBenchResult stop() {
        this.msDuration = Math.round(System.nanoTime() - this.startNanos) / 1000000;
        this.msPerWrite = -1;

        return this;
    }

    public TimedBenchResult stop(long writes) {
        this.stop();
        if (writes > 0) {
            this.msPerWrite = this.msDuration / writes;
        } else {
            this.msPerWrite = -1;
        }

        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getMsDuration() {
        return msDuration;
    }

    public void setMsDuration(long duration) {
        this.msDuration = duration;
    }

    public double getMsPerWrite() {
        return msPerWrite;
    }

    public void setMsPerWrite(long msPerWrite) {
        this.msPerWrite = msPerWrite;
    }
}
