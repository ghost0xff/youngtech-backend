package com.youngtechcr.www.profile.picture;


import com.youngtechcr.www.domain.TimeStamped;
import com.youngtechcr.www.profile.Profile;
import com.youngtechcr.www.storage.FileMetaData;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_profile_picture")
public class ProfilePicture implements FileMetaData, TimeStamped {
    @Id
    @Column(name = "id_profile_picture")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profilePictureId;
    @Column(name = "server_file_name")
    private String serverFileName;
    @Column(name = "original_file_name")
    private String originalFileName;
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
    @OneToOne(mappedBy = "profilePicture")
    private Profile profile;

    public Integer getProfilePictureId() {
        return profilePictureId;
    }

    public void setProfilePictureId(Integer profilePictureId) {
        this.profilePictureId = profilePictureId;
    }



    @Override
    public String getServerFileName() {
        return serverFileName;
    }

    @Override
    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    @Override
    public String getOriginalFileName() {
        return originalFileName;
    }

    @Override
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @Override
    public String getRelativePath() {
        return relativePath;
    }

    @Override
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public long getSizeInBytes() {
        return sizeInBytes;
    }

    @Override
    public void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}


