package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileStd {
    private long stageId;
    private long fileId;
    private String filePath;
    private String fileDescription;
    private String fileLimit;
    private long contestId;
}
