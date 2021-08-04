package com.sast.atSast.service;

import com.sast.atSast.model.Proposal;

/**
 * @program: ATSAST
 * @description: 上传报销材料业务层
 * @author: cxy621
 * @create: 2021-07-26 19:44
 **/
public interface ProposalService {

    void addProposalFile(Proposal proposal);

}
