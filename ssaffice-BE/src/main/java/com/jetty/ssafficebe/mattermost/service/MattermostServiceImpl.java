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
import org.springframework.web.client.HttpClientErrorException;

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
     * @return 사용자가 속해 있는 채널 List 입니다.
     * @throws IllegalArgumentException  사용자 ID가 null 일 경우 발생합니다.
     * @throws ResourceNotFoundException 사용자 ID에 해당하는 사용자를 찾을 수 없을 때 발생합니다.
     * @throws InvalidTokenException     유효하지 않은 토큰일 경우 발생합니다.
     * @throws RuntimeException          채널 정보를 가져오는 도중 다른 오류가 발생할 때 발생합니다.
     */
    @Override
    public List<MMChannelSummary> getChannelsByUserIdFromMM(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("사용자 ID가 null 입니다.");
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
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("채널 정보를 가져오는 도중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 주어진 채널 목록에서 '공지사항'을 포함하는 채널만 필터링하여 반환하는 메서드입니다.
     *
     * @param mmChannelSummaryList 필터링할 채널 목록입니다.
     * @return '공지사항'을 포함하는 채널 목록입니다.
     * @throws IllegalArgumentException 입력된 채널 목록이 null 일 경우 발생합니다.
     */
    @Override
    public List<MMChannelSummary> filteredNoticeChannels(List<MMChannelSummary> mmChannelSummaryList) {
        if (mmChannelSummaryList == null) {
            throw new IllegalArgumentException("필터링 할 채널 목록이 null 입니다.");
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
     * 주어진 채널 목록에서 이미 존재하는 채널을 제외하고 새로운 채널만 반환하는 메서드입니다.
     *
     * @param channelSummaries 중복 여부를 검사할 채널 목록입니다.
     * @return 중복되지 않은 새로운 채널 목록입니다.
     * @throws IllegalArgumentException 입력된 채널 목록이 null 일 경우 발생합니다.
     */
    @Override
    public List<Channel> getNonDuplicateChannels(List<MMChannelSummary> channelSummaries) {
        if (channelSummaries == null) {
            throw new IllegalArgumentException("중복 파악할 채널 목록이 null 입니다.");
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
            return new ApiResponse(true, HttpStatus.OK, "저장할 채널이 없습니다", null);
        }

        // 1. 채널 목록을 데이터베이스에 저장합니다.
        channelRepository.saveAll(channelList);

        // 2. 저장된 채널의 ID 목록을 반환합니다.
        List<String> savedChannelIds = channelList.stream().map(Channel::getChannelId).toList();

        return new ApiResponse(true, HttpStatus.OK, "채널이 성공적으로 저장되었습니다", savedChannelIds);
    }

    /**
     * 주어진 사용자 ID와 채널 목록을 이용해 userChannel Table 에 매핑되어있지 않은 채널 목록을 반환하는 메서드입니다.
     *
     * @param userId               사용자 ID 입니다.
     * @param mmChannelSummaryList 사용자가 속해있는 모든 채널 목록입니다.
     * @return userChannel 에 해당 userId 에 매핑되어 있지 않은 채널 목록입니다.
     * @throws IllegalArgumentException 사용자 ID 또는 채널 요약 목록이 null 일 경우 발생합니다.
     */
    @Override
    public List<MMChannelSummary> getNonDuplicateChannelsByUserId(Long userId,
                                                                  List<MMChannelSummary> mmChannelSummaryList) {
        if (userId == null) {
            throw new IllegalArgumentException("사용자 ID가 null 입니다.");
        }
        if (mmChannelSummaryList == null) {
            throw new IllegalArgumentException("채널 요약 목록이 null 입니다.");
        }

        // 1. userChannel table 에서 해당 userID에 매핑되어있는 채널 목록을 조회합니다.
        List<UserChannel> existingUserChannelList = userChannelRepository.findAllByUserId(userId);
        List<String> existingUserChannelIds = existingUserChannelList.stream().map(UserChannel::getChannelId).toList();

        // 2. 매핑되어있지 않은 채널 목록을 필터링하여 반환합니다.
        return mmChannelSummaryList.stream().filter(channel -> !existingUserChannelIds.contains(channel.getId()))
                                   .toList();
    }

    /**
     * 주어진 사용자 ID와 채널 목록을 이용해 UserChannel 테이블에 저장하는 메서드입니다.
     *
     * @param userId               사용자 ID 입니다.
     * @param mmChannelSummaryList 저장할 채널 목록입니다.
     * @return 저장 결과를 담고 있는 ApiResponse 객체입니다.
     * @throws ResourceNotFoundException 사용자 ID에 해당하는 사용자를 찾을 수 없을 때 발생합니다.
     * @throws IllegalArgumentException  사용자 ID 또는 채널 요약 목록이 null 일 경우 발생합니다.
     */
    @Override
    public ApiResponse saveChannelListToUserChannelByUserId(Long userId, List<MMChannelSummary> mmChannelSummaryList) {

        if (userId == null) {
            throw new IllegalArgumentException("사용자 ID가 null 입니다.");
        }
        if (mmChannelSummaryList == null || mmChannelSummaryList.isEmpty()) {
            throw new IllegalArgumentException("저장할 채널 요약 목록이 null 이거나 비어 있습니다.");
        }

        // 1. userID로 사용자 정보를 조회합니다.
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));

        // 2. userID와 채널 아이디를 저장할 UserChannel 엔티티 목록을 생성하고 저장합니다.
        List<UserChannel> userChannelsToSave = mmChannelSummaryList.stream().map(channel -> new UserChannel(userId,
                                                                                                            channel.getId()))
                                                                   .toList();
        userChannelRepository.saveAll(userChannelsToSave);

        // 3. 사용자 정보(RecentMMChannelSyncTIme 필드)를 갱신하고 업데이트합니다.
        user.setRecentMmChannelSyncTime(LocalDateTime.now());
        userRepository.save(user);
        return new ApiResponse(true, HttpStatus.OK, "Channel saved successfully into UserChannel",
                               userChannelsToSave.stream().map(UserChannel::getChannelId).toList());
    }


    /**
     * 두 사용자의 ID를 이용해 Mattermost 에서 직접 메시지 채널을 생성하는 메서드입니다.
     *
     * @param userId       채널을 생성할 사용자의 ID 입니다.
     * @param targetUserId 채널을 생성할 타겟 사용자의 ID 입니다.
     * @return 생성된 DM 채널의 ID 입니다.
     * @throws ResourceNotFoundException 사용자 ID에 해당하는 사용자를 찾을 수 없을 때 발생합니다.
     */
    @Override
    public String createDMChannel(Long userId, Long targetUserId) {

        // 1. 사용자 정보를 조회하여 Mattermost 토큰을 가져옵니다.
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        String token = user.getMattermostToken();

        // 2. 사용자와 타겟 사용자의 Mattermost ID를 조회합니다.
        String mmUserId = user.getMattermostUserId();
        String mmTargetUserId = userRepository.findById(targetUserId).orElseThrow(
                                                      () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", targetUserId))
                                              .getMattermostUserId();

        // 3. DM 채널 생성을 위한 페이로드 구성
        String[] payload = new String[]{mmUserId, mmTargetUserId};

        // 4. Mattermost API 를 호출하여 DM 채널을 생성합니다.
        ResponseEntity<MMChannelSummary> response = this.mattermostUtil.callMattermostApi("/channels/direct",
                                                                                          HttpMethod.POST, payload,
                                                                                          MMChannelSummary.class,
                                                                                          token);
        // 5. 생성된 채널의 ID를 반환합니다.
        return Objects.requireNonNull(response.getBody(), "응답 본문이 null입니다.").getId();
    }

    /**
     * 주어진 사용자 ID와 채널 ID를 사용하여 해당 채널에 직접 메시지를 전송하는 메서드입니다.
     *
     * @param userId 메시지를 전송할 사용자의 ID 입니다.
     * @param channelId 메시지를 전송할 채널의 ID 입니다.
     * @param scheduleId 메시지의 내용을 정의하는 스케줄의 ID 입니다.
     * @return 메시지 전송 결과를 담고 있는 ApiResponse 객체입니다.
     * @throws ResourceNotFoundException 사용자 ID 또는 스케줄 ID에 해당하는 데이터를 찾을 수 없을 때 발생합니다.
     * @throws InvalidTokenException 토큰이 유효하지 않을 때 발생합니다.
     * @throws RuntimeException 메시지를 보내는 도중 다른 오류가 발생할 때 발생합니다.
     */
    @Override
    public ApiResponse sendDirectMessage(Long userId, String channelId, Long scheduleId) {
        // 1. 사용자 정보를 조회하여 Mattermost 토큰을 가져옵니다.
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        String token = user.getMattermostToken();

        // 2. 스케줄 정보를 조회하여 메시지 내용을 구성합니다.
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.SCHEDULE_NOT_FOUND, "scheduleId", scheduleId));

        String message =
                "[REMIND]\n제목 : " + schedule.getTitle() + " \n" + "내용 : " + schedule.getMemo() + " \n" + "시작시간 : "
                        + schedule.getStartDateTime() + " \n" + "종료시간 : " + schedule.getEndDateTime();

        // 3. 메시지 전송을 위한 페이로드를 구성하여 MMAPI 를 호출하여 메시지를 보냅니다.
        Map<String, String> payload = Map.of("channel_id", channelId, "message", message);
        try {
            ResponseEntity<PostSummary> response = this.mattermostUtil.callMattermostApi("/posts", HttpMethod.POST,
                                                                                         payload, PostSummary.class,
                                                                                         token);

            boolean isSuccess = response.getStatusCode() == HttpStatus.CREATED;
            return new ApiResponse(isSuccess, HttpStatus.resolve(response.getStatusCode().value()), "",
                                   response.getBody());

        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
        } catch(HttpClientErrorException e) {
            throw new RuntimeException("메시지 전송 중 오류가 발생했습니다.", e);
        }
    }
}
