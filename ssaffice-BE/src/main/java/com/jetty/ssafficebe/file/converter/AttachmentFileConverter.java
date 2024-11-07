package com.jetty.ssafficebe.file.converter;

import com.jetty.ssafficebe.file.entity.AttachmentFile;
import com.jetty.ssafficebe.file.payload.AttachmentFileSummaryWithStream;
import com.jetty.ssafficebe.file.payload.UploadFileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttachmentFileConverter {

    UploadFileResponse toResponse(AttachmentFile attachmentFile);

    AttachmentFileSummaryWithStream toAttachmentFileSummaryWithStream(AttachmentFile attachmentFile);
}
