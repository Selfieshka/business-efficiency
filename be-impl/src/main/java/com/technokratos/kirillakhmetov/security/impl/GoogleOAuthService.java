package com.technokratos.kirillakhmetov.security.impl;

import com.technokratos.kirillakhmetov.config.properties.GoogleOAuthProperties;
import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.enums.Role;
import com.technokratos.kirillakhmetov.repository.OwnerRepository;
import com.technokratos.kirillakhmetov.security.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOAuthService implements OAuthService {
    private final GoogleOAuthProperties googleOAuthProperties;
    private final RestTemplate restTemplate;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String getAuthUrl() {
        return UriComponentsBuilder.fromUriString(googleOAuthProperties.getAuthUri())
                .queryParam("client_id", googleOAuthProperties.getClientId())
                .queryParam("redirect_uri", googleOAuthProperties.getRedirectUri())
                .queryParam("response_type", googleOAuthProperties.getResponseType())
                .queryParam("scope", googleOAuthProperties.getScope())
                .queryParam("access_type", googleOAuthProperties.getAccessType())
                .queryParam("prompt", googleOAuthProperties.getPrompt())
                .build()
                .toUriString();
    }

    @Override
    public void processOAuthPostLogin(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", googleOAuthProperties.getClientId());
        map.add("client_secret", googleOAuthProperties.getClientSecret());
        map.add("code", code);
        map.add("grant_type", googleOAuthProperties.getGrantType());
        map.add("redirect_uri", googleOAuthProperties.getRedirectUri());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(googleOAuthProperties.getTokenUrl(), request, Map.class);
        String accessToken = (String) response.getBody().get("access_token");
        log.info("Received access token from Google");

        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<?> userInfoRequest = new HttpEntity<>(userInfoHeaders);

        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(
                googleOAuthProperties.getUserInfoUrl(),
                HttpMethod.GET,
                userInfoRequest,
                Map.class
        );

        log.info("Received user info from Google: {}", userInfoResponse.getBody());

        authenticate(userInfoResponse.getBody());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication after OAuth processing: {}", authentication);
        if (authentication != null) {
            log.info("User is authenticated: {}", authentication.isAuthenticated());
        } else {
            log.warn("Authentication is null after OAuth processing");
        }
    }

    private void authenticateUser(Owner owner) {
        UserDetailsImpl userDetails = new UserDetailsImpl(owner);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());

        ((UsernamePasswordAuthenticationToken) authentication).setDetails(userDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String generateRandomPassword() {
        return passwordEncoder.encode(UUID.randomUUID().toString());
    }

    private Owner authenticate(Map<String, Object> userInfo) {
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        Optional<Owner> ownerOptional = ownerRepository.findByEmail(email);
        Owner owner;

        if (ownerOptional.isPresent()) {
            owner = ownerOptional.get();
            owner.setFirstName(name);
        } else {
            owner = new Owner();
            owner.setEmail(email);
            owner.setFirstName(name);
            owner.setRole(Role.USER);
            owner.setPassword(generateRandomPassword());
        }

        owner = ownerRepository.save(owner);
        authenticateUser(owner);
        return owner;
    }
}