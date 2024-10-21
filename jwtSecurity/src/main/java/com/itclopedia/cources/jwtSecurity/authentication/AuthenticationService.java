package com.itclopedia.cources.jwtSecurity.authentication;

import java.util.List;

public interface AuthenticationService {

    List<String> authenticate(final String username, final String password);

}
