package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.*;
import com.sast.atSast.model.Contest;
import com.sast.atSast.model.Stage;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.pojo.*;
import com.sast.atSast.service.ContestService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author cxy621
 * @date 2021/7/16 18:48:44
 * @description 创建比赛，将信息插入数据库
 */
@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestMapper contestMapper;

    @Autowired
    private StageMapper stageMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private ProposalMapper proposalMapper;

    @Override
    public void createContest(Contest contest) {
        contest.setStages(contest.getStageTemps().size());
        contestMapper.createContest(contest);
        for (Stage stage : contest.getStageTemps()) {
            stageMapper.createStage(stage);
        }
    }

    @Override
    public void updatepushLink(Long contestId, String pushLink) {
        contestMapper.updatepushLink(contestId, pushLink);
    }

    @Override
    public Object[] getJudgeList(Long contestId) {
        List<TeamMember> teamMembers = contestMapper.getTeamById(contestId);
        List<TeamMemberTemp> teamMemberTemps = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            String name = teamMember.getTeamName();
            long id = teamMember.getTeamId();
            TeamMemberTemp teamMemberTemp = new TeamMemberTemp(name, id);
            teamMemberTemps.add(teamMemberTemp);
        }
        return teamMemberTemps.toArray();
    }

    public List<Contest> getContestByCurr(Integer curr) {
        return contestMapper.getContestByCurr(curr);
    }

    public List<Contest> getContest() {
        return contestMapper.getContest();
    }


    @Override
    public Contest getContestById(Long contestId) {
        return contestMapper.getContestById(contestId);
    }

    @Override
    public void updateCurr(Long contestId, Integer curr) {
        contestMapper.updateCurr(contestId, curr);
    }


    @Override
    public void updateComment(Long contestId, String comment) {
        contestMapper.updateComment(contestId, comment);
    }

    @Override
    public void updateJudge(Long judging, Long contestId) {
        contestMapper.updateJudge(judging, contestId);
    }

    @Override
    public String getpushLinkById(Long contestId) {
        return contestMapper.getpushLinkById(contestId);
    }

    @Override
    public String getfileUrlById(Long contestId) {
        return contestMapper.getfileUrlById(contestId);
    }

    @Override
    public JudgeCreateContest judgeContestBegin(Long contestId) {
        Contest contest = contestMapper.getContestById(contestId);
        JudgeCreateContest judgeCreateContest = new JudgeCreateContest();
        judgeCreateContest.setContestName(contest.getContestName());
        judgeCreateContest.setDescription(contest.getDescription());
        judgeCreateContest.setContestOrganizer(contest.getContestOrganizer());
        judgeCreateContest.setContestHost(contest.getContestHost());
        judgeCreateContest.setContestHelper(contest.getContestHelper());
        judgeCreateContest.setIsTeam(contest.getIsTeam());
        judgeCreateContest.setTeamGroup(contest.getTeamGroup());
        judgeCreateContest.setJoinGrade(contest.getJoinGrade());
        judgeCreateContest.setIsInstructor(contest.getIsInstructor());
        judgeCreateContest.setWorkCategory(contest.getWorkCategory());
        judgeCreateContest.setSubjectCategory(contest.getSubjectCategory());
        judgeCreateContest.setFileUrl(contest.getFileUrl());
        judgeCreateContest.setBanners(Arrays.asList(contest.getBanners().split("#")));
        judgeCreateContest.setMinMember(contest.getMinMember());
        judgeCreateContest.setMaxMember(contest.getMaxMember());
        judgeCreateContest.setMinInstructor(contest.getMinInstructor());
        judgeCreateContest.setMaxInstructor(contest.getMaxInstructor());

        List<Stage> stages = stageMapper.getStagesById(contestId);
        List<StageShow> stageShows = new ArrayList<>();

        for (Stage stage : stages) {
            StageShow stageShow = new StageShow();
            stageShow.setStageName(stage.getStageName());
            stageShow.setStageType(stage.getStageType());
            stageShow.setStageBegin(stage.getStageBegin());
            stageShow.setStageEnd(stage.getStageEnd());
            stageShows.add(stageShow);
        }
        judgeCreateContest.setStages(stageShows);

        return judgeCreateContest;

    }

    @Override
    public JudgeContestEnd getContestFiles(Long contestId) {
        JudgeContestEnd judgeContestEnd = new JudgeContestEnd();
        judgeContestEnd.setPictureUrls(Arrays.asList(pictureMapper.getUrlsById(contestId).split("#")));
        judgeContestEnd.setVideoUrl(videoMapper.getUrlById(contestId));
        judgeContestEnd.setPushLinkUrls(Arrays.asList(contestMapper.getpushLinkById(contestId).split("#")));
        judgeContestEnd.setProposalUrl(proposalMapper.getProposalById(contestId));
        return judgeContestEnd;
    }

    @Override
    public List<ContestStage> getStageById(Long contestId) {
        return contestMapper.getStageById(contestId);
    }

}


