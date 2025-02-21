package com.hr_software_project.hr_management.constant;

public class Constants {
        public static final String DEFAULT_DATA_TIME_ZONE = "GMT";//when converting from date -> timestamp
        public static final String ROLE_ADMIN = "ADMIN";
        public static final String ROLE_USER = "USER";
        public static final String ROLE_MANAGER = "MANAGER";
        public static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";
        public static final String STATUS_ACTIVE = "ACTIVE";
        public static final String STATUS_INACTIVE = "INACTIVE";
        public static final String STATUS_DELETED = "DELETED";

        public static final String OPT_OUT_EMPTY_CLEAR = "Opt-Out list for the group: %s is empty.";
        public static final String OPT_OUT_SITE_NO_CHANGES = "No effect to the current Opt-Out list for group: %s.";

        public static final String OPT_OUT_SITE_DIFFERENCE_PROMPT =
                "Opt-Out list affected by change in alert preference parameter update. Opt-Out information for: %s would be removed.";

}

