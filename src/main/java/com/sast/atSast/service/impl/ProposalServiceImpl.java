package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.ProposalMapper;
import com.sast.atSast.model.Proposal;
import com.sast.atSast.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: ATSAST
 * @description: 上传报销材料业务实现层
 * @author: cxy621
 * @create: 2021-07-26 19:44
 **/
@Service
public class ProposalServiceImpl implements ProposalService {

    @Autowired
    private ProposalMapper proposalMapper;

    @Override
    public void addProposalFile(Proposal proposal) {
        proposalMapper.addProposalFile(proposal);
    }

}
