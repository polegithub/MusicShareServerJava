package eric.clapton.infrastructure.util.callback;

public interface Func3<TArg1, TArg2, TArg3, TResult> {
    TResult invoke(TArg1 arg1, TArg2 arg2, TArg3 arg3);
}
