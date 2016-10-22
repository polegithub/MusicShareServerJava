package eric.clapton.infrastructure.entity.dto;

public enum SysMessageSeverity {
    CONFIRM("确认"),
    DEBUG("调试"),
    INFO("消息"),
    WARNING("警告"),
    ERROR("错误"),
    FATAL("崩溃"), ;

    private final String description;

    private SysMessageSeverity(String description) {
        this.description = description;
    }

    public final String getDescription() {
        return description;
    }

}
