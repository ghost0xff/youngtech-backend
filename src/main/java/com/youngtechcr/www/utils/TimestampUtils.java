package com.youngtechcr.www.utils;

import com.youngtechcr.www.domain.interfaces.TimeStamped;

import java.time.LocalDateTime;

public final class TimestampUtils {

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
