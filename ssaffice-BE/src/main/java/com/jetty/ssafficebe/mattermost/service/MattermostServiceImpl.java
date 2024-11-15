package com.jetty.ssafficebe.mattermost.service;

import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.channel.respository.ChannelRepository;
import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidTokenException;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.mattermost.payload.ChannelSummary;
import com.jetty.ssafficebe.mattermost.payload.DeleteSummary;
import com.jetty.ssafficebe.mattermost.payload.PostRequest;
import com.jetty.ssafficebe.mattermost.payload.PostSummary;
import com.jetty.ssafficebe.mattermost.payload.PostUpdateRequest;
import com.jetty.ssafficebe.mattermost.payload.UserAutocompleteResponse;
import com.jetty.ssafficebe.mattermost.payload.UserAutocompleteSummary;
import com.jetty.ssafficebe.mattermost.util.MattermostUtil;
import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.repository.UserRepository;
import java.util.List;
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

    // userId를 조회해서 해당 User 가 속한 전체 채널을 MM API 를 통해 가져옴
    @Override
    public ChannelSummary[] getChannelsByUserIdFromMM(Long userId) {
        // 1. userId로 user 조회하여 mattermostId와 Token 가져오기
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        String mmUserId = user.getMattermostUserId();
        String token = user.getMattermostToken();

        // 2. token 과 mmId로 ChannelSummary 를 배열로 가져오기
        try {
            ResponseEntity<ChannelSummary[]> response = this.mattermostUtil.callMattermostApi(
                    "/users/" + mmUserId + "/channels", HttpMethod.GET, null, ChannelSummary[].class, token);

            ChannelSummary[] channelSummaries = response.getBody();
            // 3. ChannelId가 기존의 DB에 있는지 확인하고 없으면 DB에 저장
            // MM 에서 가져온 ChannelSummary 에서 id만 추출해서 해당 id로 db의 내용과 중복체크
            return channelSummaries;
        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(ErrorCode.TOKEN_NOT_FOUND, "mattermostToken", token);
        }
    }

    // db에 없는 채널리스트 가져오기
    @Override
    public List<Channel> getNonDuplicateChannels(List<ChannelSummary> channelSummaries) {
        List<String> channelIds = channelSummaries.stream().map(ChannelSummary::getId).toList();
        List<String> existingChannelIds = channelRepository.findByChannelIdIn(channelIds).stream()
                                                           .map(Channel::getChannelId).toList();
        return channelSummaries.stream().filter(channelSummary -> !existingChannelIds.contains(channelSummary.getId()))
                               .map(channelSummary -> {
                                   Channel channel = new Channel();
                                   channel.setChannelId(channelSummary.getId());
                                   channel.setChannelName(channelSummary.getDisplayName());
                                   return channel;
                               }).toList();
    }

    @Override
    public ApiResponse saveAllChannelsByMMChannelList(List<Channel> channelList) {
        channelRepository.saveAll(channelList);
        return new ApiResponse(true, HttpStatus.OK, "Channel saved successfully",
                               channelList.stream().map(Channel::getChannelId).toList());
    }
}
