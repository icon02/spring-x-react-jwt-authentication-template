package io.github.icon02.springbootauthenticationtemplate.tempStorage;

import java.util.Collection;

public interface TemporaryStorage<O, I> {
    Collection<O> getAll();
    O get(I id);
    I save(O obj);
    O remove(I id);

    interface Identifiable<I> {
        I getId();
    }
}
