package eric.clapton.infrastructure.util.callback;

public interface Action1<TArg> {
    void invoke(TArg arg);
}
