package cn.wolfcode.rbac.domain.vo;

import lombok.Data;

@Data
public class ImageUploadRequest {
    private String base64Image;
    private Long employeeId;
}
