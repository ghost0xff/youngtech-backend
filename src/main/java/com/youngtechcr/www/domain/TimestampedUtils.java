package com.youngtechcr.www.domain;

import java.time.LocalDateTime;

public final class TimestampedUtils {


    public static void setTimestampsToNow(TimeStamped timeStampedToBeCreated) {
        timeStampedToBeCreated.setCreatedAt(LocalDateTime.now());
        timeStampedToBeCreated.setUpdatedAt(LocalDateTime.now());
    }

    /*
    * "storedCreatedAtTimestamp" parameter is usually the createdAt property (stored in DB)
    * of the TimeStamped object who is being updated
    * */
    public static void updateTimeStamps(TimeStamped timeStampedToBeUpdated, LocalDateTime storedCreatedAtTimestamp) {
        timeStampedToBeUpdated.setUpdatedAt(LocalDateTime.now());
        timeStampedToBeUpdated.setCreatedAt(storedCreatedAtTimestamp);
    }
}
