package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.TeamMemberMapper;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    @Autowired
    private TeamMemberMapper teamMemberMapper;

    @Override
    public List<TeamMember> getTeams(Long contestId) {
        return teamMemberMapper.getTeams(contestId);
    }

    @Override
    public void updateTeam(Long teamId, Integer score, String result) {
        teamMemberMapper.updateTeam(teamId, score, result);
    }

    @Override
    public void insertTeam(TeamMember teamMember) {
        teamMemberMapper.insertTeam(teamMember);
    }

}
