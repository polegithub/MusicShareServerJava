package eric.clapton.infrastructure.entity.dto;

import java.io.Serializable;

public class SysMessage implements Serializable {
    private static final long serialVersionUID = -2424374185628092211L;

    private final SysMessageSeverity severity;
    private final String content;

    public SysMessage(SysMessageSeverity severity, String content) {
        this.severity = severity;
        this.content = content;
    }

    public final SysMessageSeverity getSeverity() {
        return severity;
    }

    public final String getContent() {
        return content;
    }

    public static final SysMessage error(String message) {
        return new SysMessage(SysMessageSeverity.ERROR, message);
    }

    public static final SysMessage warning(String message) {
        return new SysMessage(SysMessageSeverity.WARNING, message);
    }

    public static final SysMessage info(String message) {
        return new SysMessage(SysMessageSeverity.INFO, message);
    }

    public static final SysMessage confirm(String message) {
        return new SysMessage(SysMessageSeverity.CONFIRM, message);
    }

    public static final SysMessage fatal(String message) {
        return new SysMessage(SysMessageSeverity.FATAL, message);
    }

    public static final SysMessage debug(String message) {
        return new SysMessage(SysMessageSeverity.DEBUG, message);
    }
}
