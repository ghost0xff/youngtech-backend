package com.youngtechcr.www.profile.picture;


import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.profile.Profile;
import com.youngtechcr.www.storage.Storable;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_profile_picture")
public class ProfilePicture implements Storable, Timestamped {
    @Id
    @Column(name = "id_profile_picture")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "server_name")
    private String serverName;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "relative_path")
    private String relativePath;
    @Column(name = "mime_type")
    private String mimeType;
    @Column(name = "size_in_bytes")
    private long sizeInBytes;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @OneToOne(mappedBy = "profilePicture", fetch = FetchType.LAZY)
    private Profile profile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverFileName) {
        this.serverName = serverFileName;
    }

    @Override
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalFileName) {
        this.originalName = originalFileName;
    }

    @Override
    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public long getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ProfilePicture{" +
                "profilePictureId=" + id +
                ", serverName='" + serverName + '\'' +
                ", originalName='" + originalName + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", sizeInBytes=" + sizeInBytes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}