package eric.clapton.infrastructure.util.callback;

public interface Func5<TArg1, TArg2, TArg3, TArg4, TArg5, TResult> {
    TResult invoke(TArg1 arg1, TArg2 arg2, TArg3 arg3, TArg4 arg4, TArg5 arg5);
}
