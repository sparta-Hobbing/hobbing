package com.hobbing.proxygateway.domain;

public class JwtHeader {
    public final static String KEY_ACCESS_TOKEN = "Authorization";
    public final static String KEY_USER_ID = "X-User-Id";
    public final static String KEY_USER_ROLE = "X-User-Role";
    public final static String VALUE_BEARER_PREFIX = "Bearer ";
    public final static String KEY_INTERNAL_KEY = "X-Internal-Key";
}
