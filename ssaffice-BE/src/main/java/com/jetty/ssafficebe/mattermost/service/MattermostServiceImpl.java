package com.jetty.ssafficebe.mattermost.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidTokenException;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
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

    @Override
    public String getTeams() {
        String Token = this.mattermostUtil.makeMMAccessToken("rlawogus258@naver.com", "Srlawogus258@naver.com11");
        if (Token != null) {
            ResponseEntity<String> response = this.mattermostUtil.callMattermostApi("/users/me/teams", HttpMethod.GET,
                                                                                    Token);
            System.out.println(response.getBody());
            return response.getBody();
        } else {
            System.out.println("로그인 실패");
        }
        return null;
    }

    @Override
    public String getChannelsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));
        String token = user.getMattermostToken();
        if (token == null || token.isEmpty()) {
            throw new InvalidTokenException(ErrorCode.TOKEN_NOT_FOUND, "mattermostToken", null);
        }
        ResponseEntity<String> response = this.mattermostUtil.callMattermostApi("/users/" + userId + "/channels",
                                                                                HttpMethod.GET, token);
        System.out.println(response.getBody());
        return response.getBody();
    }


}
