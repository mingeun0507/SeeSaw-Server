package com._8attery.seesaw.domain.project;

import com._8attery.seesaw.domain.battery.Battery;
import com._8attery.seesaw.domain.battery_variation.BatteryVariation;
import com._8attery.seesaw.domain.project_emotion.ProjectEmotion;
import com._8attery.seesaw.domain.project_record.ProjectRecord;
import com._8attery.seesaw.domain.project_remembrance.ProjectRemembrance;
import com._8attery.seesaw.domain.user.User;
import com._8attery.seesaw.domain.value.Value;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SS_PROJECT")
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "value_id")
    private Value value;

    @Column(name = "project_name", nullable = false)
    private String projectName; // 프로젝트 이름

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt; // 시작 날짜

    @Column(name = "ended_at")
    private LocalDateTime endedAt; // 종료 날짜

    @Enumerated(EnumType.STRING)
    @Column(name = "intensity")
    private Intensity intensity; // 강도

    @Column(name = "goal")
    private String goal; // 목표

    @ColumnDefault("false")
    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished; // 프로젝트 완료 여부

    @OneToOne(mappedBy = "project", fetch = LAZY, orphanRemoval = true)
    private ProjectEmotion projectEmotion;

    @OneToOne(mappedBy = "project", fetch = LAZY, orphanRemoval = true)
    private ProjectRecord projectRecord;

    @OneToMany(mappedBy = "project", orphanRemoval = true)
    private List<ProjectRemembrance> projectRemembrances = new ArrayList<>();

    @Builder
    public Project(String projectName, LocalDateTime startedAt, LocalDateTime endedAt, Intensity intensity, String goal, Boolean isFinished) {
        this.projectName = projectName;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.intensity = intensity;
        this.goal = goal;
        this.isFinished = isFinished;
    }
}
