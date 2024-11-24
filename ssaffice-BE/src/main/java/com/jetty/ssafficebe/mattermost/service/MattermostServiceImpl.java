package com.jetty.ssafficebe.mattermost.service;

import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.channel.entity.UserChannel;
import com.jetty.ssafficebe.channel.respository.ChannelRepository;
import com.jetty.ssafficebe.channel.respository.UserChannelRepository;
import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidTokenException;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidValueException;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.mattermost.payload.DeleteSummary;
import com.jetty.ssafficebe.mattermost.payload.MMChannelSummary;
import com.jetty.ssafficebe.mattermost.payload.MMLoginRequest;
import com.jetty.ssafficebe.mattermost.payload.PostRequest;
import com.jetty.ssafficebe.mattermost.payload.PostSummary;
import com.jetty.ssafficebe.mattermost.payload.PostUpdateRequest;
import com.jetty.ssafficebe.mattermost.payload.UserAutocompleteResponse;
import com.jetty.ssafficebe.mattermost.payload.UserAutocompleteSummary;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class MattermostServiceImpl implements MattermostService {

    private static final String END_POINT_FOR_POST = "posts";
    private final MattermostUtil mattermostUtil;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final UserChannelRepository userChannelRepository;
    private final ScheduleRepository scheduleRepository;

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

    @Override
    public List<UserAutocompleteSummary> getUserAutocomplete(String name) {
        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.add("name", name);
        ResponseEntity<UserAutocompleteResponse> response = this.mattermostUtil.callApi("users/autocomplete",
                                                                                        queryParameters, HttpMethod.GET,
                                                                                        null,
                                                                                        UserAutocompleteResponse.class);
        return (response.getBody() != null) ? response.getBody().getUsers() : null;
    }

    // // userId를 이용해 MM 에서 해당 user 가 속한 채널리스트를 가져옴
    @Override
    public List<MMChannelSummary> getChannelsByUserIdFromMM(Long userId) {
        // 1. userId로 user 조회하여 mattermostId와 Token 가져오기
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        String mmUserId = user.getMattermostUserId();
        String token = user.getMattermostToken();

        // 2. token 과 mmId로 ChannelSummary 를 배열로 가져오기
        try {
            ResponseEntity<MMChannelSummary[]> response = this.mattermostUtil.callMattermostApi(
                    "/users/" + mmUserId + "/channels", HttpMethod.GET, null, MMChannelSummary[].class, token);

            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }

    // 가져온 채널 리스트 중 Notice(공지사항) 채널만 필터링
    @Override
    public List<MMChannelSummary> filteredNoticeChannels(List<MMChannelSummary> mmChannelSummaryList) {
        List<MMChannelSummary> mmChannelSummaries = new ArrayList<>();
        for (MMChannelSummary channelSummary : mmChannelSummaryList) {
            if (channelSummary.getDisplayName().contains("공지사항")) {
                mmChannelSummaries.add(channelSummary);
            }
        }
        return mmChannelSummaries;
    }


    // 기존 채널 db에 저장되어있지 않은 채널만 가져옴
    @Override
    public List<Channel> getNonDuplicateChannels(List<MMChannelSummary> channelSummaries) {
        List<String> channelIds = channelSummaries.stream().map(MMChannelSummary::getId).toList();
        List<String> existingChannelIds = channelRepository.findByChannelIdIn(channelIds).stream()
                                                           .map(Channel::getChannelId).toList();
        return channelSummaries.stream()
                               .filter(MMChannelSummary -> !existingChannelIds.contains(MMChannelSummary.getId()))
                               .map(MMChannelSummary -> {
                                   Channel channel = new Channel();
                                   channel.setChannelId(MMChannelSummary.getId());
                                   channel.setMmTeamId(MMChannelSummary.getTeamId());
                                   channel.setChannelName(MMChannelSummary.getDisplayName());
                                   return channel;
                               }).toList();
    }

    // 가져온 채널리스트를 Channel 테이블에 저장
    @Override
    public ApiResponse saveAllChannelsByMMChannelList(List<Channel> channelList) {
        channelRepository.saveAll(channelList);
        return new ApiResponse(true, HttpStatus.OK, "Channel saved successfully",
                               channelList.stream().map(Channel::getChannelId).toList());
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
    public String createDMChannel(Long userId, Long targetUserId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        String token = user.getMattermostToken();

        String mmUserId = user.getMattermostUserId();
        String mmTargetUserId = userRepository.findById(targetUserId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", targetUserId)).getMattermostUserId();

        String[] payload = new String[]{mmUserId, mmTargetUserId};

        ResponseEntity<MMChannelSummary> response = this.mattermostUtil.callMattermostApi(
                "/channels/direct" , HttpMethod.POST, payload, MMChannelSummary.class, token);

        return Objects.requireNonNull(response.getBody()).getId();
    }

    @Override
    public ApiResponse sendDirectMessage(Long userId, String channelId, Long scheduleId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        String token = user.getMattermostToken();

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.SCHEDULE_NOT_FOUND, "scheduleId", scheduleId));

        String message = "[REMIND]\n제목 : " + schedule.getTitle() +" \n" + "내용 : " + schedule.getMemo() + " \n" + "시작시간 : " + schedule.getStartDateTime() + " \n" + "종료시간 : " + schedule.getEndDateTime();

        Map<String, String> payload = Map.of(
                "channel_id", channelId,
                "message", message
        );
        try {
            ResponseEntity<PostSummary> response = this.mattermostUtil.callMattermostApi(
                    "/posts", HttpMethod.POST, payload, PostSummary.class, token);

            boolean isSuccess = response.getStatusCode() == HttpStatus.CREATED;
            return new ApiResponse(isSuccess, HttpStatus.resolve(response.getStatusCode().value()), "", response.getBody());

        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(ErrorCode.TOKEN_NOT_FOUND);
        }
    }

    @Override
    public ApiResponse MMLogin(Long userId, MMLoginRequest mmLoginRequest){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        user.setMattermostToken(this.getMMToken(mmLoginRequest.getLoginId(), mmLoginRequest.getPassword()));
        userRepository.save(user);
        return new ApiResponse(true, HttpStatus.OK, "Login Success", user.getEmail());
    }

    public String getMMToken(String mmId, String mmPassword) {
        MMLoginRequest payload = new MMLoginRequest(mmId, mmPassword);
        ResponseEntity<String> response = this.mattermostUtil.callMattermostApi(
                "/users/login", HttpMethod.POST, payload, String.class, null);
        try{
            return Objects.requireNonNull(response.getHeaders().get("Token")).get(0);
        } catch(Exception e){
            throw new InvalidValueException(ErrorCode.INVALID_MM_LOGIN, "mmID", mmId);
        }
    }
}
