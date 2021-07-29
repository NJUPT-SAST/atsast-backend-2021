
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
    private Long contestId;
    private Long judgeUid;
    private Byte enable;
    private Long id;
    private Long teamId;

    @Transient
    private List<Long> teamIds;
}
