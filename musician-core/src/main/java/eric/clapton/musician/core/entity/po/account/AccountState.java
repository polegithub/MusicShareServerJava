package eric.clapton.musician.core.entity.po.account;

public enum AccountState {
    NORMAL("正常"), TO_BE_REVIEWED("资质待审核"), LOCKED("已锁定"), REMOVED("已删除");

    private final String description;

    AccountState(String description) {
        this.description = description;
    }

    public final String getDescription() {
        return description;
    }
}
