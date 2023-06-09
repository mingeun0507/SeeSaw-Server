package com._8attery.seesaw.controller.project;

import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.dto.api.request.ProjectRequestDto;
import com._8attery.seesaw.dto.api.response.ProjectCardResponseDto;
import com._8attery.seesaw.dto.api.response.ProjectResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponse;
import com._8attery.seesaw.service.project.ProjectService;
import com._8attery.seesaw.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com._8attery.seesaw.exception.BaseResponseStatus.USERS_FAILED_POST_ID;

@Slf4j
//@Api(tags = {"4. Project"})
@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final UserService userService;

    private final ProjectService projectService;

    // 프로젝트 추가
    @PostMapping("/api/project")
    public BaseResponse<ProjectResponseDto> addProject(@RequestBody ProjectRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            ProjectResponseDto res = projectService.addUserProject(userId, req.getValueId(), req.getProjectName(), req.getStartedAt(), req.getEndedAt(), req.getIntensity(), req.getGoal());

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 프로젝트 수정
    @PutMapping("/api/project/{projectId}")
    public BaseResponse<ProjectResponseDto> updateProject(@PathVariable("projectId") Long projectId, @RequestBody ProjectRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            Long postUserId = projectService.retrieveUserId(projectId);
            if (userId != postUserId) {
                return new BaseResponse<>(USERS_FAILED_POST_ID);
            }

            ProjectResponseDto res = projectService.updateUserProject(projectId, userId, req.getValueId(), req.getProjectName(), req.getStartedAt(), req.getEndedAt(), req.getIntensity(), req.getGoal());

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 프로젝트 삭제
    @DeleteMapping("api/project/{projectId}")
    public BaseResponse<String> deleteProject(@PathVariable("projectId") Long projectId, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            Long postUserId = projectService.retrieveUserId(projectId);
            if (userId != postUserId) {
                return new BaseResponse<>(USERS_FAILED_POST_ID);
            }
            projectService.deleteUserProject(projectId);
            String result = "프로젝트 삭제를 성공했습니다.";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 진행 중인 프로젝트 조회
    @GetMapping("api/project/progress")
    public BaseResponse<List<ProjectCardResponseDto>> getProgressProject(@AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            List<ProjectCardResponseDto> res = projectService.getProgressProjectList(userId);

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 완료된 프로젝트 조회
    @GetMapping("api/project/complete")
    public BaseResponse<List<ProjectCardResponseDto>> getCompleteProject(@AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            List<ProjectCardResponseDto> res = projectService.getCompleteProjectList(userId);

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}
