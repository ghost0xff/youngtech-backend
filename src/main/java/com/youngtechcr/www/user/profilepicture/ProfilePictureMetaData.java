package com.youngtechcr.www.user.profilepicture;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.youngtechcr.www.domain.TimeStamped;
import com.youngtechcr.www.storage.FileMetaData;
import com.youngtechcr.www.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_profile_picture")
public class ProfilePictureMetaData implements FileMetaData, TimeStamped {
    @Id
    @Column(name = "id_profile_picture")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profilePictureId;
    @OneToOne
    @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user")
    private User user;
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

    public Integer getProfilePictureId() {
        return profilePictureId;
    }

    public void setProfilePictureId(Integer profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfilePictureMetaData that = (ProfilePictureMetaData) o;
        return sizeInBytes == that.sizeInBytes && Objects.equals(profilePictureId, that.profilePictureId) && Objects.equals(user, that.user) && Objects.equals(serverFileName, that.serverFileName) && Objects.equals(originalFileName, that.originalFileName) && Objects.equals(relativePath, that.relativePath) && Objects.equals(mimeType, that.mimeType) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profilePictureId, user, serverFileName, originalFileName, relativePath, mimeType, sizeInBytes, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "ProfilePictureMetaData{" +
                "profilePictureId=" + profilePictureId +
                ", user=" + user +
                ", serverFileName='" + serverFileName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", sizeInBytes=" + sizeInBytes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
