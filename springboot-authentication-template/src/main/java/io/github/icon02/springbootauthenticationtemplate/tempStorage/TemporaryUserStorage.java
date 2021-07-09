package io.github.icon02.springbootauthenticationtemplate.tempStorage;

import io.github.icon02.springbootauthenticationtemplate.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TemporaryUserStorage implements TemporaryStorage<User, Long> {

    private Long cId = 0L;
    private Map<Long, User> idStorage = new HashMap<>();
    private Map<String, User> emailStorage = new HashMap<>();

    @Override
    public Collection<User> getAll() {
        return idStorage.values();
    }

    @Override
    public User get(Long id) {
        return idStorage.get(id);
    }

    public User get(String email) {
        return emailStorage.get(email);
    }

    @Override
    public Long save(User obj) {
        Long id;
        if(obj.getId() == null) {
            id = cId++;
            obj.setId(id);
        } else {
            id = obj.getId();
        }

        idStorage.put(id, obj);
        emailStorage.put(obj.getEmail(), obj);

        return id;
    }

    @Override
    public User remove(Long id) {
        User obj =  idStorage.remove(id);
        if(obj != null) {
            emailStorage.remove(obj.getEmail());
        }

        return obj;
    }



}
