package cn.wolfcode.rbac.domain.vo;

import lombok.Data;

@Data
public class RecognitionRequest {
    String image;
    Long employeeId;
}
