package com.youngtechcr.www.profile.picture;


import com.youngtechcr.www.domain.Timestamped;
import com.youngtechcr.www.profile.Profile;
import com.youngtechcr.www.storage.Storable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfilePicture that = (ProfilePicture) o;

        if (sizeInBytes != that.sizeInBytes) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(serverName, that.serverName)) return false;
        if (!Objects.equals(originalName, that.originalName)) return false;
        if (!Objects.equals(relativePath, that.relativePath)) return false;
        if (!Objects.equals(mimeType, that.mimeType)) return false;
        if (!Objects.equals(createdAt, that.createdAt)) return false;
        if (!Objects.equals(updatedAt, that.updatedAt)) return false;
        return Objects.equals(profile, that.profile);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
        result = 31 * result + (originalName != null ? originalName.hashCode() : 0);
        result = 31 * result + (relativePath != null ? relativePath.hashCode() : 0);
        result = 31 * result + (mimeType != null ? mimeType.hashCode() : 0);
        result = 31 * result + (int) (sizeInBytes ^ (sizeInBytes >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        return result;
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