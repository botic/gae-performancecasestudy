package io.botic.response;

public class TimedOperationsResult {
    private long startNanos;

    private String text;
    private long msDuration;
    private long operationCount;

    public TimedOperationsResult() {
        this.startNanos = System.nanoTime();
    }

    public TimedOperationsResult(String text, long msDuration, long operationCount) {
        super();
        this.text = text;
        this.msDuration = msDuration;
        this.operationCount = operationCount;
    }

    public TimedOperationsResult stop() {
        this.msDuration = Math.round(System.nanoTime() - this.startNanos) / 1000000;
        return this;
    }

    public TimedOperationsResult stop(long count) {
        this.operationCount = count;
        return this.stop();
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

    public void setMsDuration(long msDuration) {
        this.msDuration = msDuration;
    }

    public long getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(long operationCount) {
        this.operationCount = operationCount;
    }
}
