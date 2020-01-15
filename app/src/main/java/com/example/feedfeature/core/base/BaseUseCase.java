package com.example.feedfeature.core.base;

public abstract class BaseUseCase<Params, Callback> {
    public abstract void execute(Params params, Callback callback);
}
