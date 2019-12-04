package com.brieffeed.back.security;

public class SecurityContains {

    public static final String[] SIGN_UP_URLS = {"/api", "/api/posts", "/api/posts/**", "/api/blogs/**", "/api/users", "/api/tags", "/api/categories/**", "/api/news", "/api/users/**"};
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 1800_000;
}