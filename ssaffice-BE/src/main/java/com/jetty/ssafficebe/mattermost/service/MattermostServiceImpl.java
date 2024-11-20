package com.jetty.ssafficebe.mattermost.service;

import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.channel.entity.UserChannel;
import com.jetty.ssafficebe.channel.respository.ChannelRepository;
import com.jetty.ssafficebe.channel.respository.UserChannelRepository;
import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidTokenException;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.mattermost.converter.MMChannelConverter;
import com.jetty.ssafficebe.mattermost.payload.DeleteSummary;
import com.jetty.ssafficebe.mattermost.payload.MMChannelSummary;
import com.jetty.ssafficebe.mattermost.payload.PostRequest;
import com.jetty.ssafficebe.mattermost.payload.PostSummary;
import com.jetty.ssafficebe.mattermost.payload.PostUpdateRequest;
import com.jetty.ssafficebe.mattermost.util.MattermostUtil;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.repository.ScheduleRepository;
import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MattermostServiceImpl implements MattermostService {

    private static final String END_POINT_FOR_POST = "posts";
    private final MattermostUtil mattermostUtil;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final UserChannelRepository userChannelRepository;
    private final ScheduleRepository scheduleRepository;
    private final MMChannelConverter mmChannelConverter;

    @Override
    public PostSummary getPost(String postId) {
        ResponseEntity<PostSummary> response = this.mattermostUtil.callApi(END_POINT_FOR_POST + "/" + postId, null,
                                                                           HttpMethod.GET, null, PostSummary.class);

        return response.getBody();
    }

    @Override
    public ApiResponse createPost(PostRequest request) {
        ResponseEntity<PostSummary> response = this.mattermostUtil.callApi(END_POINT_FOR_POST, null, HttpMethod.POST,
                                                                           request, PostSummary.class);

        boolean isSuccess = response.getStatusCode() == HttpStatus.CREATED;

        return new ApiResponse(isSuccess, HttpStatus.resolve(response.getStatusCode().value()), "", response.getBody());
    }

    @Override
    public ApiResponse putPost(PostUpdateRequest request) {
        ResponseEntity<PostSummary> response = this.mattermostUtil.callApi(END_POINT_FOR_POST + "/" + request.getId(),
                                                                           null, HttpMethod.PUT, request,
                                                                           PostSummary.class);

        boolean isSuccess = response.getStatusCode() == HttpStatus.OK;

        return new ApiResponse(isSuccess, HttpStatus.resolve(response.getStatusCode().value()), "", response.getBody());
    }

    @Override
    public ApiResponse deletePost(String postId) {
        ResponseEntity<DeleteSummary> response = this.mattermostUtil.callApi(END_POINT_FOR_POST + "/" + postId, null,
                                                                             HttpMethod.DELETE, null,
                                                                             DeleteSummary.class);

        boolean isSuccess = response.getStatusCode() == HttpStatus.OK;

        return new ApiResponse(isSuccess, HttpStatus.resolve(response.getStatusCode().value()), "", response.getBody());
    }

    /**
     * 주어진 사용자 ID를 사용하여 Mattermost 에서 사용자의 채널 목록을 가져오는 메서드입니다. Mattermost API 를 호출하여 사용자와 연관된 모든 채널 정보를 가져옵니다.
     *
     * @param userId 채널 목록을 가져올 사용자의 ID 입니다.
     * @return 사용자가 접근 가능한 채널 요약 정보를 포함한 List 객체입니다.
     * @throws IllegalArgumentException  사용자 ID가 null 일 경우 발생합니다.
     * @throws ResourceNotFoundException 사용자 ID에 해당하는 사용자를 찾을 수 없을 때 발생합니다.
     * @throws InvalidTokenException     유효하지 않은 토큰일 경우 발생합니다.
     */
    @Override
    public List<MMChannelSummary> getChannelsByUserIdFromMM(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("사용자 ID가 null입니다.");
        }

        // 1. userId로 DB의 user 를 조회하여 Mattermost ID와 토큰을 가져옵니다.
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        String mmUserId = user.getMattermostUserId();
        String token = user.getMattermostToken();

        // 2. mmToken 과 mmId로 채널 요약 정보를 배열로 가져옵니다.
        try {
            ResponseEntity<MMChannelSummary[]> response = this.mattermostUtil.callMattermostApi(
                    "/users/" + mmUserId + "/channels", HttpMethod.GET, null, MMChannelSummary[].class, token);

            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * 주어진 채널 목록에서 '공지사항'을 포함하는 채널만 필터링하여 반환하는 메서드입니다.
     *
     * @param mmChannelSummaryList 필터링할 채널 요약 정보 목록입니다.
     * @return '공지사항'을 포함하는 채널 요약 정보 목록입니다.
     * @throws IllegalArgumentException 입력된 채널 요약 정보 목록이 null 일 경우 발생합니다.
     */
    @Override
    public List<MMChannelSummary> filteredNoticeChannels(List<MMChannelSummary> mmChannelSummaryList) {
        if (mmChannelSummaryList == null) {
            throw new IllegalArgumentException("mmChannelSummaryList 가 null 입니다.");
        }

        List<MMChannelSummary> mmChannelSummaries = new ArrayList<>();
        for (MMChannelSummary channelSummary : mmChannelSummaryList) {
            if (channelSummary.getDisplayName().contains("공지사항")) {
                mmChannelSummaries.add(channelSummary);
            }
        }
        return mmChannelSummaries;
    }


    /**
     * 주어진 채널 요약 목록에서 이미 존재하는 채널을 제외하고 새로운 채널만 반환하는 메서드입니다.
     *
     * @param channelSummaries 중복 여부를 검사할 채널 요약 목록입니다.
     * @return 중복되지 않은 새로운 채널 목록입니다.
     * @throws IllegalArgumentException 입력된 채널 요약 목록이 null 일 경우 발생합니다.
     */
    @Override
    public List<Channel> getNonDuplicateChannels(List<MMChannelSummary> channelSummaries) {
        if (channelSummaries == null) {
            throw new IllegalArgumentException("채널 요약 목록이 null입니다.");
        }

        // 1. 채널 요약 목록에서 채널 ID 들을 추출합니다.
        List<String> channelIds = channelSummaries.stream().map(MMChannelSummary::getId).toList();
        // 2. 이미 존재하는 채널 ID 목록을 데이터베이스에서 조회합니다.
        List<String> existingChannelIds = channelRepository.findByChannelIdIn(channelIds).stream()
                                                           .map(Channel::getChannelId).toList();
        // 3. 기존에 존재하지 않는 새로운 채널만 추출하여 반환합니다.
        List<MMChannelSummary> newChannelSummaries = channelSummaries.stream()
                                                                    .filter(channelSummary -> !existingChannelIds.contains(
                                                                            channelSummary.getId())).toList();
        // 4. 새로운 채널 목록을 Channel 엔티티로 변환하여 반환합니다.
        return mmChannelConverter.toChannelList(newChannelSummaries);
    }

    /**
     * 채널 목록을 데이터베이스에 저장하는 메서드입니다.
     *
     * @param channelList 저장할 채널 목록입니다.
     * @return 요청의 처리 결과를 담고 있는 ApiResponse 객체로, 성공적으로 저장되었을 때의 응답 결과를 포함합니다.
     * @throws IllegalArgumentException 채널 목록이 null 이거나 비어 있을 경우 발생합니다.
     */
    @Override
    public ApiResponse saveAllChannelsByMMChannelList(List<Channel> channelList) {
        if (channelList == null || channelList.isEmpty()) {
            return new ApiResponse(true, HttpStatus.OK, "No channel to save", null);
        }

        // 1. 채널 목록을 데이터베이스에 저장합니다.
        channelRepository.saveAll(channelList);

        // 2. 저장된 채널의 ID 목록을 반환합니다.
        List<String> savedChannelIds = channelList.stream()
                                                  .map(Channel::getChannelId)
                                                  .toList();

        return new ApiResponse(true, HttpStatus.OK, "Channel saved successfully", savedChannelIds);
    }

    // MM 에서 가져온 채널리스트 중 UserChannel 테이블에 해당 userId로 찾았을 때 저장되어있지 않은 채널만 가져옴
    @Override
    public List<MMChannelSummary> getNonDuplicateChannelsByUserId(Long userId,
                                                                  List<MMChannelSummary> mmChannelSummaryList) {
        List<UserChannel> existingUserChannelList = userChannelRepository.findAllByUserId(userId);
        List<String> existingUserChannelIds = existingUserChannelList.stream().map(UserChannel::getChannelId).toList();

        return mmChannelSummaryList.stream().filter(channel -> !existingUserChannelIds.contains(channel.getId()))
                                   .toList();
    }

    // 가져온 채널리스트를 UserChannel 테이블에 저장
    @Override
    public ApiResponse saveChannelListToUserChannelByUserId(Long userId, List<MMChannelSummary> mmChannelSummaryList) {
        List<UserChannel> userChannelsToSave = mmChannelSummaryList.stream().map(channel -> new UserChannel(userId,
                                                                                                            channel.getId()))
                                                                   .toList();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        user.setRecentMmChannelSyncTime(LocalDateTime.now());
        userRepository.save(user);
        userChannelRepository.saveAll(userChannelsToSave);
        return new ApiResponse(true, HttpStatus.OK, "Channel saved successfuly into UserChannel",
                               userChannelsToSave.stream().map(UserChannel::getChannelId).toList());
    }

    // DM을 보내기 위해선 먼저 DM Channel을 만들어야함.
    @Override
    public String createDMChannel(Long userId, Long targetUserId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        String token = user.getMattermostToken();

        String mmUserId = user.getMattermostUserId();
        String mmTargetUserId = userRepository.findById(targetUserId).orElseThrow(
                                                      () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", targetUserId))
                                              .getMattermostUserId();

        String[] payload = new String[]{mmUserId, mmTargetUserId};

        ResponseEntity<MMChannelSummary> response = this.mattermostUtil.callMattermostApi("/channels/direct",
                                                                                          HttpMethod.POST, payload,
                                                                                          MMChannelSummary.class,
                                                                                          token);

        return Objects.requireNonNull(response.getBody()).getId();
    }

    @Override
    public ApiResponse sendDirectMessage(Long userId, String channelId, Long scheduleId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        String token = user.getMattermostToken();

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.SCHEDULE_NOT_FOUND, "scheduleId", scheduleId));

        String message =
                "[REMIND]\n제목 : " + schedule.getTitle() + " \n" + "내용 : " + schedule.getMemo() + " \n" + "시작시간 : "
                        + schedule.getStartDateTime() + " \n" + "종료시간 : " + schedule.getEndDateTime();

        Map<String, String> payload = Map.of("channel_id", channelId, "message", message);
        try {
            ResponseEntity<PostSummary> response = this.mattermostUtil.callMattermostApi("/posts", HttpMethod.POST,
                                                                                         payload, PostSummary.class,
                                                                                         token);

            boolean isSuccess = response.getStatusCode() == HttpStatus.CREATED;
            return new ApiResponse(isSuccess, HttpStatus.resolve(response.getStatusCode().value()), "",
                                   response.getBody());

        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }
}
