package eric.clapton.infrastructure.util.callback;

public interface Func1<TArg, TResult> {
    TResult invoke(TArg arg);
}
