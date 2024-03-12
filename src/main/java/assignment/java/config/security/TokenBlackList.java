package assignment.java.config.security;

public interface TokenBlackList {
    void addToBlacklist(String token);
    boolean isBlacklisted(String token);
}