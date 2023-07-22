package com.youngtechcr.www.domain;

import java.time.LocalDateTime;

public interface Timestamped {

    public LocalDateTime getCreatedAt();
    public void setCreatedAt(LocalDateTime createdAt);
    public LocalDateTime getUpdatedAt();

    public void setUpdatedAt(LocalDateTime updatedAt);

}
