package com.wallacy.projetoestagio.security;

import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomOAuth2SuccessHandlerTest {

    @InjectMocks
    private CustomOAuth2SuccessHandler successHandler;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private Authentication authentication;

    @Mock
    private OAuth2User oAuth2User;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserAndRedirectWithToken() throws IOException {
        // Arrange
        String email = "googleuser@example.com";
        String name = "Google User";
        String tokenValue = "fake-jwt-token";

        User user = new User();
        user.setEmail(email);
        user.setName(name);

        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute("email")).thenReturn(email);
        when(oAuth2User.getAttribute("name")).thenReturn(name);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn(tokenValue);
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        verify(userRepository).save(any(User.class));
        assertEquals("http://localhost:3000/oauth2/callback?token=" + tokenValue, response.getRedirectedUrl());
    }

    @Test
    void shouldFindExistingUserAndRedirect() throws IOException {
        // Arrange
        String email = "existing@example.com";
        String name = "Existing User";
        String tokenValue = "existing-jwt-token";

        User user = new User();
        user.setEmail(email);
        user.setName(name);

        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute("email")).thenReturn(email);
        when(oAuth2User.getAttribute("name")).thenReturn(name);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn(tokenValue);
        when(jwtEncoder.encode(any())).thenReturn(jwt);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        verify(userRepository, never()).save(any());
        assertEquals("http://localhost:3000/oauth2/callback?token=" + tokenValue, response.getRedirectedUrl());
    }
}
