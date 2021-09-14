package com.example.familymap;

import com.example.familymap.net.ServerProxy;
import com.example.familymap.shared.requests.LoginRequest;
import com.example.familymap.shared.results.LoginResult;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ServerProxyTest {

    @Test
    public void ServerProxyLoginTest(){
        ServerProxy proxy = new ServerProxy();
        LoginRequest request = new LoginRequest();
        request.userName="username";
        request.password = "password";
        LoginResult result = proxy.login(request);
        assertNotNull(result);
    }
}
