package com.youngtechcr.www.domain;

import java.time.LocalDateTime;

public final class TimestampedUtils {


    public static void setTimestampsToNow(Timestamped timestampedToBeCreated) {
        timestampedToBeCreated.setCreatedAt(LocalDateTime.now());
        timestampedToBeCreated.setUpdatedAt(LocalDateTime.now());
    }

    /*
    * "storedCreatedAtTimestamp" parameter is usually the current createdAt property (stored in DB)
    * of the TimeStamped object who is being updated
    * */
    public static void updateTimeStamps(Timestamped timestampedToBeUpdated, LocalDateTime storedCreatedAtTimestamp) {
        timestampedToBeUpdated.setUpdatedAt(LocalDateTime.now());
        timestampedToBeUpdated.setCreatedAt(storedCreatedAtTimestamp);
    }
}
