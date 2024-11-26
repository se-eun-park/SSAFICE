package com.jetty.ssafficebe.mattermost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.jetty.ssafficebe.channel.service.ChannelService;
import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class MattermostServiceImplTest {

    @Mock
    private MattermostUtil mattermostUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ChannelService channelService;

    @InjectMocks
    private MattermostServiceImpl mattermostService;

    private MattermostServiceImpl spyMattermostService;

    @BeforeEach
    public void setup() {
        // 모의 객체들을 주입하여 서비스 객체를 생성하고 스파이로 감쌉니다.
        mattermostService = Mockito.spy(new MattermostServiceImpl(mattermostUtil, userRepository, scheduleRepository, channelService));
    }

    @Test
    public void testGetPost_Success() {
        // Arrange
        String postId = "123";
        PostSummary postSummary = new PostSummary();
        ResponseEntity<PostSummary> responseEntity = new ResponseEntity<>(postSummary, HttpStatus.OK);

        when(mattermostUtil.callApi(eq("posts/" + postId), isNull(), eq(HttpMethod.GET), isNull(), eq(PostSummary.class)))
                .thenReturn(responseEntity);

        // Act
        PostSummary result = mattermostService.getPost(postId);

        // Assert
        assertNotNull(result);
        assertEquals(postSummary, result);
        verify(mattermostUtil, times(1)).callApi(eq("posts/" + postId), isNull(), eq(HttpMethod.GET), isNull(), eq(PostSummary.class));
    }

    @Test
    public void testCreatePost_Success() {
        // Arrange
        PostRequest request = new PostRequest();
        PostSummary postSummary = new PostSummary();
        ResponseEntity<PostSummary> responseEntity = new ResponseEntity<>(postSummary, HttpStatus.CREATED);

        when(mattermostUtil.callApi(eq("posts"), isNull(), eq(HttpMethod.POST), eq(request), eq(PostSummary.class)))
                .thenReturn(responseEntity);

        // Act
        ApiResponse apiResponse = mattermostService.createPost(request);

        // Assert
        assertTrue(apiResponse.isSuccess());
        assertEquals(HttpStatus.CREATED, apiResponse.getStatus());
        assertEquals(postSummary, apiResponse.getData());
        verify(mattermostUtil, times(1)).callApi(eq("posts"), isNull(), eq(HttpMethod.POST), eq(request), eq(PostSummary.class));
    }

    @Test
    public void testCreatePost_Failure() {
        // Arrange
        PostRequest request = new PostRequest();
        PostSummary postSummary = new PostSummary();
        ResponseEntity<PostSummary> responseEntity = new ResponseEntity<>(postSummary, HttpStatus.BAD_REQUEST);

        when(mattermostUtil.callApi(eq("posts"), isNull(), eq(HttpMethod.POST), eq(request), eq(PostSummary.class)))
                .thenReturn(responseEntity);

        // Act
        ApiResponse apiResponse = mattermostService.createPost(request);

        // Assert
        assertFalse(apiResponse.isSuccess());
        assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatus());
        assertEquals(postSummary, apiResponse.getData());
        verify(mattermostUtil, times(1)).callApi(eq("posts"), isNull(), eq(HttpMethod.POST), eq(request), eq(PostSummary.class));
    }

    @Test
    public void testPutPost_Success() {
        // Arrange
        PostUpdateRequest request = new PostUpdateRequest();
        request.setId("123");
        PostSummary postSummary = new PostSummary();
        ResponseEntity<PostSummary> responseEntity = new ResponseEntity<>(postSummary, HttpStatus.OK);

        when(mattermostUtil.callApi(eq("posts/" + request.getId()), isNull(), eq(HttpMethod.PUT), eq(request), eq(PostSummary.class)))
                .thenReturn(responseEntity);

        // Act
        ApiResponse apiResponse = mattermostService.putPost(request);

        // Assert
        assertTrue(apiResponse.isSuccess());
        assertEquals(HttpStatus.OK, apiResponse.getStatus());
        assertEquals(postSummary, apiResponse.getData());
        verify(mattermostUtil, times(1)).callApi(eq("posts/" + request.getId()), isNull(), eq(HttpMethod.PUT), eq(request), eq(PostSummary.class));
    }

    @Test
    public void testPutPost_Failure() {
        // Arrange
        PostUpdateRequest request = new PostUpdateRequest();
        request.setId("123");
        PostSummary postSummary = new PostSummary();
        ResponseEntity<PostSummary> responseEntity = new ResponseEntity<>(postSummary, HttpStatus.BAD_REQUEST);

        when(mattermostUtil.callApi(eq("posts/" + request.getId()), isNull(), eq(HttpMethod.PUT), eq(request), eq(PostSummary.class)))
                .thenReturn(responseEntity);

        // Act
        ApiResponse apiResponse = mattermostService.putPost(request);

        // Assert
        assertFalse(apiResponse.isSuccess());
        assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatus());
        assertEquals(postSummary, apiResponse.getData());
        verify(mattermostUtil, times(1)).callApi(eq("posts/" + request.getId()), isNull(), eq(HttpMethod.PUT), eq(request), eq(PostSummary.class));
    }

    @Test
    public void testDeletePost_Success() {
        // Arrange
        String postId = "123";
        DeleteSummary deleteSummary = new DeleteSummary();
        ResponseEntity<DeleteSummary> responseEntity = new ResponseEntity<>(deleteSummary, HttpStatus.OK);

        when(mattermostUtil.callApi(eq("posts/" + postId), isNull(), eq(HttpMethod.DELETE), isNull(), eq(DeleteSummary.class)))
                .thenReturn(responseEntity);

        // Act
        ApiResponse apiResponse = mattermostService.deletePost(postId);

        // Assert
        assertTrue(apiResponse.isSuccess());
        assertEquals(HttpStatus.OK, apiResponse.getStatus());
        assertEquals(deleteSummary, apiResponse.getData());
        verify(mattermostUtil, times(1)).callApi(eq("posts/" + postId), isNull(), eq(HttpMethod.DELETE), isNull(), eq(DeleteSummary.class));
    }

    @Test
    public void testDeletePost_Failure() {
        // Arrange
        String postId = "123";
        DeleteSummary deleteSummary = new DeleteSummary();
        ResponseEntity<DeleteSummary> responseEntity = new ResponseEntity<>(deleteSummary, HttpStatus.NOT_FOUND);

        when(mattermostUtil.callApi(eq("posts/" + postId), isNull(), eq(HttpMethod.DELETE), isNull(), eq(DeleteSummary.class)))
                .thenReturn(responseEntity);

        // Act
        ApiResponse apiResponse = mattermostService.deletePost(postId);

        // Assert
        assertFalse(apiResponse.isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, apiResponse.getStatus());
        assertEquals(deleteSummary, apiResponse.getData());
        verify(mattermostUtil, times(1)).callApi(eq("posts/" + postId), isNull(), eq(HttpMethod.DELETE), isNull(), eq(DeleteSummary.class));
    }

    @Test
    public void testSaveChannelsByUserIdOnRefresh_Success() {
        // Arrange
        Long userId = 1L;

        // getChannelsByUserIdFromMM 메서드 모의
        List<MMChannelSummary> mmChannelSummaryList = new ArrayList<>();
        MMChannelSummary channel1 = new MMChannelSummary();
        channel1.setDisplayName("공지사항 채널1");
        MMChannelSummary channel2 = new MMChannelSummary();
        channel2.setDisplayName("일반 채널");
        MMChannelSummary channel3 = new MMChannelSummary();
        channel3.setDisplayName("공지사항 채널2");
        mmChannelSummaryList.add(channel1);
        mmChannelSummaryList.add(channel2);
        mmChannelSummaryList.add(channel3);

        // 스파이 객체의 메서드를 모의
        Mockito.doReturn(mmChannelSummaryList).when(mattermostService).getChannelsByUserIdFromMM(userId);

        // saveNotExistingChannelList 메서드 모의
        int savedCount = 2;
        when(channelService.saveNotExistingChannelList(anyList())).thenReturn(savedCount);

        // Act
        ApiResponse apiResponse = mattermostService.saveChannelsByUserIdOnRefresh(userId);

        // Assert
        assertTrue(apiResponse.isSuccess());
        assertEquals(HttpStatus.OK, apiResponse.getStatus());
        assertEquals("Channel saved successful", apiResponse.getMessage());
        assertEquals(savedCount, apiResponse.getData());

        verify(mattermostService, times(1)).getChannelsByUserIdFromMM(userId);
        verify(channelService, times(1)).saveNotExistingChannelList(anyList());
        verify(channelService, times(1)).saveChannelListToUserChannelByUserId(eq(userId), anyList());
    }

    @Test
    public void testGetChannelsByUserIdFromMM_Success() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setMattermostUserId("mmUserId");
        user.setMattermostToken("mmToken");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        MMChannelSummary[] channelsArray = new MMChannelSummary[2];
        channelsArray[0] = new MMChannelSummary();
        channelsArray[0].setDisplayName("Channel 1");
        channelsArray[1] = new MMChannelSummary();
        channelsArray[1].setDisplayName("Channel 2");

        ResponseEntity<MMChannelSummary[]> responseEntity = new ResponseEntity<>(channelsArray, HttpStatus.OK);

        when(mattermostUtil.callMattermostApi(eq("/users/" + user.getMattermostUserId() + "/channels"), eq(HttpMethod.GET), isNull(), eq(MMChannelSummary[].class), eq(user.getMattermostToken())))
                .thenReturn(responseEntity);

        // Act
        List<MMChannelSummary> result = mattermostService.getChannelsByUserIdFromMM(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findById(userId);
        verify(mattermostUtil, times(1)).callMattermostApi(eq("/users/" + user.getMattermostUserId() + "/channels"), eq(HttpMethod.GET), isNull(), eq(MMChannelSummary[].class), eq(user.getMattermostToken()));
    }

    @Test
    public void testGetChannelsByUserIdFromMM_UserNotFound() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            mattermostService.getChannelsByUserIdFromMM(userId);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(mattermostUtil);
    }
}
