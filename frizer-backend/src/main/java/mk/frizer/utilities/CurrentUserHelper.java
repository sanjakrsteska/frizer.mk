package mk.frizer.utilities;

import mk.frizer.domain.BaseUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserHelper {
    public static Long getId() {
        BaseUser userDetails = get();
        return userDetails.getId();
    }

    public static BaseUser get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BaseUser userDetails = (BaseUser) authentication.getPrincipal();
        return userDetails;
    }
}
