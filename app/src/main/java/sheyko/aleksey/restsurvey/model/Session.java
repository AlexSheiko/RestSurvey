package sheyko.aleksey.restsurvey.model;

import java.util.Date;
import java.util.UUID;

public class Session {
    private String mCustomerId;
    private Date mStartTime;

    public Session() {
        mCustomerId = UUID.randomUUID().toString();
        mStartTime = new Date();
    }
}
