package dev.decadev.demo.util;

import dev.decadev.demo.security.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    public String getAuthenticatedUserEmail() {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return userDetails.getUsername();
    }
}
