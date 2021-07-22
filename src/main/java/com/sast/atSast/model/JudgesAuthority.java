              package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgesAuthority {
    private long contestId;
    private long judgeUid;
    private Byte enable;
    private long id;
    private long teamId;

    @Transient
    private List<Long> teamIds;
}
