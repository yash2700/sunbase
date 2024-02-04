package com.sunbase.sunbase.Exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorInfo {
    private int responseCode;
    private String responseMessage;
    private String error;
}
