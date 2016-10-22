package eric.clapton.infrastructure.util.callback;

public interface Func2<TArg1, TArg2, TResult> {
    TResult invoke(TArg1 arg1, TArg2 arg2);
}
