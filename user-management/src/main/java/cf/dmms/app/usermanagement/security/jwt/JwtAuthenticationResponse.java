package cf.dmms.app.usermanagement.security.jwt;

public class JwtAuthenticationResponse {

    private static final String TOKEN_TYPE = "Bearer";

    private String accessToken;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static String getTokenType() {
        return TOKEN_TYPE;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
