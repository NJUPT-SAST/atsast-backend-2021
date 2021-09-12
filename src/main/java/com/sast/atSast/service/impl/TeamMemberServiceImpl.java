package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.TeamMemberMapper;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.pojo.LeaderInfo;
import com.sast.atSast.pojo.SummaryTeam;
import com.sast.atSast.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<List<SummaryTeam>> getSummaryTeams(Long contestId) {

        List<TeamMember> Teams = teamMemberMapper.getTeams(contestId);
        Map<Long, List<SummaryTeam>> map = new HashMap<>();
        List<List<SummaryTeam>> listList = new LinkedList<>();

        for (TeamMember teamMember : Teams) {
            if (!map.containsKey(teamMember.getTeamId())) {
                SummaryTeam summaryTeam = new SummaryTeam();
                summaryTeam.setTeamName(teamMember.getTeamName());
                summaryTeam.setMemberUid(summaryTeam.getMemberUid());
                summaryTeam.setInstructor(summaryTeam.getInstructor());
                summaryTeam.setResult(summaryTeam.getResult());
                List<SummaryTeam> summaryTeams = new LinkedList<>();
                summaryTeams.add(summaryTeam);
                map.put(teamMember.getTeamId(), summaryTeams);
            } else {
                SummaryTeam summaryTeam = new SummaryTeam();
                summaryTeam.setTeamName(teamMember.getTeamName());
                summaryTeam.setMemberUid(summaryTeam.getMemberUid());
                summaryTeam.setInstructor(summaryTeam.getInstructor());
                summaryTeam.setResult(summaryTeam.getResult());
                map.get(teamMember.getTeamId()).add(summaryTeam);
            }
        }

        for (List<SummaryTeam> item : map.values()) {
            listList.add(item);
        }

        return listList;
    }

    @Override
    public List<TeamMember> getTeamsByLeader(Long contestId) {
        return teamMemberMapper.getTeamsByLeader(contestId);
    }

    @Override
    public List<LeaderInfo> getLeaderInfo(Long contestId) {
        return teamMemberMapper.getLeaderInfo(contestId);
    }
    //    @Override
//    public List<JugdeResultTeam> getJudgeResult(List<TeamMember> teams, String catagory) {
//        return null;
//    }
}
