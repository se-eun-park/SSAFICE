package com.jetty.ssafficebe.file.entity;

import com.jetty.ssafficebe.common.jpa.BooleanToYNConverter;
import com.jetty.ssafficebe.user.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "attachment_file")
@Getter
@Setter
public class AttachmentFile extends BaseEntity {
    @Id
    @UuidGenerator
    private String fileId;
    private String fileType;
    private String fileName;
    private Long fileSize;
    private Long refId;
    private String hash; // 내용을 기반으로 한 해시값 -> 내용이 변경되거나 그런걸 알 수 있다고 하더라?
    private String mimeType; // 파일 확장자 타입 같음.
    private String isDeletedYn = "N";
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "isDeletedYn", updatable = false, insertable = false)
    private boolean isDeleted;
    private int orderIdx; // 참조 게시글 내 순서 같음. -> 유저가 업로드 한 순서대로 저장하는 듯

}
