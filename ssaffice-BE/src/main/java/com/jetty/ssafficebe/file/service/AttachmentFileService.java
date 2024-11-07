package com.jetty.ssafficebe.file.service;

import com.jetty.ssafficebe.file.payload.AttachmentFileSummaryWithStream;
import com.jetty.ssafficebe.file.payload.UploadFileResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentFileService {

    UploadFileResponse uploadFile(MultipartFile file, String fileType, String refId) throws IOException;

    AttachmentFileSummaryWithStream getAttachmentFileWithInputStream(String fileId, String fileType) throws IOException;

    void updateFileRefId(String refId, String fileType, Collection<String> fileIds);
}
