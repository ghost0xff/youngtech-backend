package com.youngtechcr.www.security.eidte.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.FailedExternalHttpConnectionException;
import com.youngtechcr.www.exceptions.custom.InvalidEidteTokenException;
import com.youngtechcr.www.security.eidte.CommonClaimNames;
import com.youngtechcr.www.security.eidte.EidteVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.*;

public class EidteGoogleVerifier implements EidteVerifier {

    private static final Logger logger = LoggerFactory.getLogger(EidteGoogleVerifier.class);

    @Override
    public Optional<OidcIdToken> verify(String tokenValue) {
        ApacheHttpTransport httpTransport = new ApacheHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier googleVerifier =
                new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory).build();
        GoogleIdToken googleIdToken = null;
        try {
            googleIdToken = googleVerifier.verify(tokenValue);
        } catch (GeneralSecurityException e) {
            logger.debug(HttpErrorMessages.INVALID_EIDTE_TOKEN);
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_TOKEN);
        } catch (IOException e) {
            logger.debug(HttpErrorMessages.FAILED_CONNECTION_EXTERNAL_SERVER);
            throw new FailedExternalHttpConnectionException(OAuth2ErrorCodes.SERVER_ERROR);
        }
        if (googleIdToken == null) {
            logger.debug("Provided expired or unvalid id_token, rejecting EIDTE exchange");
            OAuth2Error error = new OAuth2Error(
                OAuth2ErrorCodes.INVALID_TOKEN,
                HttpErrorMessages.INVALID_EIDTE_TOKEN,
                null
            );
            throw new InvalidEidteTokenException(error);
        }
        // ---------------------- GoogleIdToken Destructuring ------------------------------
       GoogleIdToken.Payload payload = googleIdToken.getPayload();
       JsonWebToken.Header header = googleIdToken.getHeader();

       String accessTokenHash = payload.getAccessTokenHash();
       List<String> audiences = payload.getAudienceAsList();
       String subject = payload.getSubject();
       String issuer = payload.getIssuer();
       String nonce = payload.getNonce();
       String authzParty = payload.getAuthorizedParty();
       List<String> authnMethods = payload.getMethodsReferences();
       String authnContextClass = payload.getClassReference();
       boolean emailVerified = payload.getEmailVerified();
       String email = payload.getEmail();
       String profile = (String) payload.get(CommonClaimNames.PROFILE);
       String picture = (String) payload.get(CommonClaimNames.PICTURE);
       String givenName = (String) payload.get(CommonClaimNames.GIVEN_NAME);
       String familyName = (String) payload.get(CommonClaimNames.FAMILY_NAME);
       Instant issuedAtTime = Instant.ofEpochSecond(payload.getIssuedAtTimeSeconds());
       Instant expirationTime = Instant.ofEpochSecond(payload.getExpirationTimeSeconds());
//       Instant authTime = Instant.ofEpochSecond(payload.getAuthorizationTimeSeconds()); // not an OIDC claim
//       Instant notBeforeTime = Instant.ofEpochSecond(payload.getNotBeforeTimeSeconds()); // not an OIDC claim
       String jwtId = payload.getJwtId();
       String type = payload.getType() ;

        // ---------------------- Oidc Safe Contruction ------------------------------
        // Rewrite this in Rust, so I don't have to deal with NullPointerExceptions, DAMM NULL
        OidcIdToken oidcIdToken = OidcIdToken
                .withTokenValue(tokenValue)
                .accessTokenHash(accessTokenHash)
                .audience(audiences)
                .subject(subject)
                .issuer(issuer)
                .nonce(nonce)
                .issuedAt(issuedAtTime)
                .expiresAt(expirationTime)
                .authorizedParty(authzParty)
//                .authorizationCodeHash( ... ) // no c_hash in googles id_token?  // not anid OIDC claim
                .authenticationMethods(authnMethods)
                .authenticationContextClass(authnContextClass)
//                .authTime(authTime) // not an OIDC claim
//                .claim(CommonClaimNames.NOT_BEFORE_TIME, notBeforeTime) // not anid OIDC claim
                .claim(CommonClaimNames.EMAIL_VERIFIED, emailVerified)
                .claim(CommonClaimNames.EMAIL, email)
                .claim(CommonClaimNames.PROFILE, profile)
                .claim(CommonClaimNames.PICTURE, picture)
                .claim(CommonClaimNames.GIVEN_NAME, givenName)
                .claim(CommonClaimNames.FAMILY_NAME, familyName)
                .build();
        return Optional.of(oidcIdToken);
    }


}
