package com.sast.atSast.mapper;

import com.sast.atSast.model.Proposal;
import org.springframework.stereotype.Repository;

/**
 * @program: ATSAST
 * @description: 上传报销材料的mapper层
 * @author: cxy621
 * @create: 2021-07-26 19:42
 **/
@Repository
public interface ProposalMapper {

    void addProposalFile(Proposal proposal);

    String getProposalById(Long contestId);

}
