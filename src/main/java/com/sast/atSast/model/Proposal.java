package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class Proposal {
    private String proposalUrl;
    private Long contestId;
    private Byte enable;
}
