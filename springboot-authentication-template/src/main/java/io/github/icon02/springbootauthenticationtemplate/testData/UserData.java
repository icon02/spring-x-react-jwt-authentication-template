package io.github.icon02.springbootauthenticationtemplate.testData;

import io.github.icon02.springbootauthenticationtemplate.model.Authority;
import io.github.icon02.springbootauthenticationtemplate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class UserData {

    private static final Collection<Authority> adminAuthorities = new ArrayList<>();
    private static final Collection<Authority> standardAuthorities = new ArrayList<>();
    private static final Collection<Authority> guestAuthorities = new ArrayList<>();

    static {
        adminAuthorities.add(new Authority("admin"));
        adminAuthorities.add(new Authority("standard"));
        adminAuthorities.add(new Authority("guest"));

        standardAuthorities.add(new Authority("standard"));
        standardAuthorities.add(new Authority("guest"));

        guestAuthorities.add(new Authority("guest"));
    }

    public static Collection<User> getTestUsers() {
        Collection<User> users = new ArrayList<>();
        users.add(new User(null, "nicolayr@gmx.at", "1234", adminAuthorities, true, new HashMap<>()));
        users.add(new User(null, "iris.layr@gmx.at", "1234", standardAuthorities, true, new HashMap<>()));
        users.add(new User(null, "pauolo@gmx.at", "1234", standardAuthorities, true, new HashMap<>()));
        users.add(new User(null, "kerstin@gmx.at", "1234", standardAuthorities, true, new HashMap<>()));
        users.add(new User(null, "disabled@gmx.at", "1234", standardAuthorities, false, new HashMap<>()));
        users.add(new User(null, "guest@gmx.at", "1234", guestAuthorities, true, new HashMap<>()));
        return users;
    }
}
