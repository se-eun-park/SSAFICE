package com.jetty.ssafficebe.channel.service;

import com.jetty.ssafficebe.channel.converter.ChannelConverter;
import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.channel.entity.UserChannel;
import com.jetty.ssafficebe.channel.payload.ChannelSummary;
import com.jetty.ssafficebe.channel.respository.ChannelRepository;
import com.jetty.ssafficebe.channel.respository.UserChannelRepository;
import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.mattermost.payload.MMChannelSummary;
import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelConverter channelConverter;
    private final ChannelRepository channelRepository;

    private final UserChannelRepository userChannelRepository;

    private final UserRepository userRepository;

    @Override
    public List<ChannelSummary> getChannelListByUserId(Long userId) {
        List<UserChannel> userChannels = userChannelRepository.findDistinctByUserId(userId);

        List<Channel> channels = userChannels.stream()
                                             .map(UserChannel::getChannel)
                                             .toList();

        return channelConverter.toChannelSummaryList(channels);
    }

    public List<String> getChannelIdsByUserId(Long userId) {
        return userChannelRepository.findChannelIdsByUserId(userId);
    }

    @Override
    public List<Long> getUserIdListByChannelId(String channelId) {
        List<UserChannel> byChannelId = userChannelRepository.findByChannelId(channelId);
        if (byChannelId != null) {
            return byChannelId.stream()
                              .map(UserChannel::getUserId)
                              .toList();
        }
        return List.of();
    }

    /** TODO. 주석 수정
     * 메인 메서드임
     * 채널 목록을 데이터베이스에 저장하는 메서드입니다.
     *
     * @param channelSummaries 저장할 채널 목록입니다.
     */
    @Override
    public int saveNotExistingChannelList(List<MMChannelSummary> channelSummaries) {
        if (channelSummaries == null || channelSummaries.isEmpty()) {
            return 0;
        }

        // 1. 중복되지 않은 채널 목록을 추출합니다.
        List<Channel> channelList = this.getDistinctChannelList(channelSummaries);

        channelRepository.saveAll(channelList);
        return channelList.size();
    }


    /** TODO. 주석 수정
     * 메인 메서드임
     * 주어진 사용자 ID와 채널 목록을 이용해 UserChannel 테이블에 저장하는 메서드입니다.
     *
     * @param userId               사용자 ID 입니다.
     * @param mmChannelSummaryList 저장할 채널 목록입니다.
     * @throws ResourceNotFoundException 사용자 ID에 해당하는 사용자를 찾을 수 없을 때 발생합니다.
     * @throws IllegalArgumentException  사용자 ID 또는 채널 요약 목록이 null 일 경우 발생합니다.
     */
    @Override
    public void saveChannelListToUserChannelByUserId(Long userId, List<MMChannelSummary> mmChannelSummaryList) {
        if (userId == null) {
            throw new IllegalArgumentException("사용자 ID가 null 입니다.");
        }
        if (mmChannelSummaryList == null || mmChannelSummaryList.isEmpty()) {
            throw new IllegalArgumentException("저장할 채널 요약 목록이 null 이거나 비어 있습니다.");
        }

        // 1. userID로 사용자 정보를 조회합니다.
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "userId", userId));


        List<UserChannel> notExistingUserChannels = this.getDistinctUserChannelList(userId, mmChannelSummaryList);
        this.saveUserChannelList(user, notExistingUserChannels);
    }

    private List<UserChannel> getDistinctUserChannelList(Long userId, List<MMChannelSummary> mmChannelSummaryList) {
        List<String> channelIds = mmChannelSummaryList.stream().map(MMChannelSummary::getId).toList();
        return userChannelRepository.findAllByUserIdAndChannelIdNotIn(userId, channelIds);
    }

    private void saveUserChannelList(User user, List<UserChannel> userChannelList) {
        // 1. 사용자와 채널 목록을 이용해 UserChannel 엔티티 목록을 생성합니다.
        List<UserChannel> userChannelListToSave =
                userChannelList.stream().map(channel -> new UserChannel(user.getUserId(), channel.getChannelId())).toList();
        userChannelRepository.saveAll(userChannelListToSave);

        // 2. 사용자 정보(RecentMMChannelSyncTIme 필드)를 갱신하고 업데이트합니다.
        user.setRecentMmChannelSyncTime(LocalDateTime.now());
        userRepository.save(user);

    }

    private List<Channel> getDistinctChannelList(List<MMChannelSummary> channelSummaries) {

        // 1. 채널 요약 목록에서 채널 ID 들을 추출합니다.
        List<String> channelIds = channelSummaries.stream().map(MMChannelSummary::getId).toList();
        // 2. 이미 DB에 존재하는 채널 ID 목록을 데이터베이스에서 조회합니다.
        List<String> existingChannelIds = this.channelRepository.findByChannelIdIn(channelIds)
                                                                .stream().map(Channel::getChannelId)
                                                                .toList();
        // 3. DB에 존재하지 않는 새로운 채널만 추출하여 반환합니다.
        List<MMChannelSummary> newChannelSummaries = channelSummaries.stream()
                                                                     .filter(channelSummary -> !existingChannelIds.contains(
                                                                             channelSummary.getId()))
                                                                     .toList();
        // 4. 새로운 채널 목록을 Channel 엔티티로 변환하여 반환합니다.
        return this.channelConverter.toChannelList(newChannelSummaries);
    }

}
