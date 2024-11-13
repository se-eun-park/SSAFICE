package com.jetty.ssafficebe.mattermost.service;


import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.mattermost.payload.PostRequest;
import com.jetty.ssafficebe.mattermost.payload.PostSummary;
import com.jetty.ssafficebe.mattermost.payload.PostUpdateRequest;
import com.jetty.ssafficebe.mattermost.payload.UserAutocompleteSummary;
import java.util.List;

public interface MattermostService {

	PostSummary getPost(String postId);

	ApiResponse createPost(PostRequest request);

	ApiResponse putPost(PostUpdateRequest request);

	ApiResponse deletePost(String postId);

	List<UserAutocompleteSummary> getUserAutocomplete(String name);

	String getTeams();



}
