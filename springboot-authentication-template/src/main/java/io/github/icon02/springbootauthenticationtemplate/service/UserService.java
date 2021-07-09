package io.github.icon02.springbootauthenticationtemplate.service;

import io.github.icon02.springbootauthenticationtemplate.model.User;
import io.github.icon02.springbootauthenticationtemplate.tempStorage.TemporaryUserStorage;
import io.github.icon02.springbootauthenticationtemplate.testData.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Service
public class UserService implements UserDetailsService {

    private final TemporaryUserStorage userStorage;

    @Autowired
    public UserService(TemporaryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User getById(Long id) {
        return userStorage.get(id);
    }

    public User getByEmail(String email) {
        return userStorage.get(email);
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public void createAccount(User user) {
        user.setId(null);
        userStorage.save(user);
    }

    /**
     * Gets the user with a specific !email!, since we are not using usernames in this template
     *
     * @param email replaces param username
     * @return a user-object
     * @throws UsernameNotFoundException if the user with
     */
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User with email \"" + email + "\" not found!");

        return user;
    }


    @PostConstruct
    private void initTestUser() {
        Collection<User> testUsers = UserData.getTestUsers();
        for (User u : testUsers) createAccount(u);
    }
}
