package io.github.palexdev.raw4j.oauth;

public class OAuthClient {
/*    private String userAgent;
    private OkHttpClient client;
    private OAuthInfo authInfo;
    private OAuthData authData;

    private final Gson gson = new Gson();

    public OAuthClient(String userAgent) {
        this.userAgent = userAgent;
    }

    public OAuthClient init(String username, String password, String clientID, String secret) {
        authInfo = new OAuthInfo(username, password, clientID, secret);
        return this;
    }

    public OAuthInfo authenticate() throws IOException {
        if (authInfo == null) {
            throw new OAuthException("Auth client must be initialized first!");
        }

        authData = retrieveAccessToken();
        authInfo.setTimeAcquired(Instant.now().getEpochSecond());
        return authInfo;
    }

    private OAuthData retrieveAccessToken() throws IOException {
        client = buildAuthClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("client_id", authInfo.getClientID())
                .add("response_type", "token")
                .add("state", StringUtils.randomString(10))
                .add("redirect_uri", "http://127.0.0.1/callback")
                .build();

        String response = post(Urls.OAUTH_AUTH_URL.toString(), requestBody);
        System.out.println(response);
        return gson.fromJson(response, OAuthData.class);
    }

    private String post(String url, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private OkHttpClient buildAuthClient() {
        Interceptor interceptor = chain -> chain.proceed(
                chain.request()
                        .newBuilder()
                        .header("User-Agent", userAgent)
                        .build()
        );
        Authenticator authenticator = (route, response) -> {
            String credential = Credentials.basic(authInfo.getUsername(), authInfo.getPassword());
            return response.request().newBuilder().header("Authorization", credential).build();
        };

        return new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .authenticator(authenticator)
                .build();
    }

    public OAuthData getAuthData() {
        return authData;
    }*/
}
