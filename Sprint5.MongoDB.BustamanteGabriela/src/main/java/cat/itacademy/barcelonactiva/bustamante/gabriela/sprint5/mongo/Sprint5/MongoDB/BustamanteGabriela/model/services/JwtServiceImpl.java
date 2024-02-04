package cat.itacademy.barcelonactiva.bustamante.gabriela.sprint5.mongo.Sprint5.MongoDB.BustamanteGabriela.model.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{
    private final SecretKey secretKey;

    // Configuración de algoritmo y longitud de la clave
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final String SECRET_KEY_STRING = "Tm8Ake0wrdbM9bvzCn+3WkZKZEqhcz7evlPWe+Z4do8=";

    //Constructor
    public JwtServiceImpl() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_STRING);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .claim("role", userDetails.getAuthorities())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    @Override
    public String getUserName(String token) {

        return getClaim(token, Claims::getSubject);
    }
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String usernameInToken = getUserName(token);
        return (usernameInToken.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {

        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {

        return getClaim(token, Claims::getExpiration);
    }

}
