package com.youngtechcr.www.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class TimestampedUtils {

    private static final ZoneId CRZoneId = ZoneId.of(ZoneIdOption.COSTA_RICA.getId());

    public static void setTimestampsToNow(Timestamped timestampedToBeCreated) {
        timestampedToBeCreated.setCreatedAt(LocalDateTime.now(CRZoneId));
        timestampedToBeCreated.setUpdatedAt(LocalDateTime.now(CRZoneId));
    }

    /*
    * "storedCreatedAtTimestamp" parameter is usually the current createdAt property (stored in DB)
    * of the TimeStamped object who is being updated
    * */
    // CHANGE THIS THING!!!!!! second arg MIGHT be unnecesary
    public static void updateTimeStamps(
            Timestamped timestampedToBeUpdated,
            LocalDateTime storedCreatedAtTimestamp) {
        timestampedToBeUpdated.setUpdatedAt(LocalDateTime.now(CRZoneId));
        timestampedToBeUpdated.setCreatedAt(storedCreatedAtTimestamp);
        // this above makes sense, trust me  :v
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(CRZoneId);
    }

}
