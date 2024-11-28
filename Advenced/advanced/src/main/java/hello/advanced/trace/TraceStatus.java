package hello.advanced.trace;

public class TraceStatus {

    private TraceId traceId;
    private Long startTimeNs;
    private String message;
    public TraceStatus(TraceId traceId, long startTimeNs, String message) {
        this.traceId = traceId;
        this.startTimeNs = startTimeNs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public long getStartTimeNs() {
        return startTimeNs;
    }

    public String getMessage() {
        return message;
    }
}
