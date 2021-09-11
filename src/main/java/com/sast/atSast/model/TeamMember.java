
package com.sast.atSast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember {
    private Long teamId;
    private Long memberUid;
    private Byte enable;
    private Long leaderUid;
    private String instructor;
    private Long contestId;
    private String teamName;
    private Integer score;
    private String result;
    private String teamGroup;
    private String instructorId;
    private String subjectCategory;
    private String workType;

<<<<<<< HEAD
    public Message toMessage(){
        Message message=new Message();
        message.uid=this.memberUid;
        message.contestId=this.contestId;
        message.enable=1;
        message.leaderUid=this.leaderUid;
        message.teamId=this.teamId;
        message.teamName=this.teamName;
        message.contestName=null;
        message.leaderName=null;
        return message;
    }


=======
    @Transient
    private String memberUids;
>>>>>>> 4160b63d50a1f7f8dd30960c2034203265449ab9
}

