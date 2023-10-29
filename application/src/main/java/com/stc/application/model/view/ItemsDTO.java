package com.stc.application.model.view;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ItemsDTO {

    private String userEmail;

    private String name;

    private String permissionGroupId;

    private MultipartFile file;
}
