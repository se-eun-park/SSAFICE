import { useEffect, useState } from 'react'
import { dummySsafyUsers, MattermostChannel, SsafyUser } from './types'

export const useManageMembersTabContent = (channel: MattermostChannel) => {
  const [userInChannelList, setUserInChannelList] = useState<SsafyUser[]>([])
  const [selectedUserInChannelList, setSelectedUserInChannelList] = useState<SsafyUser[]>([])

  // 1초 후에 데이터를 반환하는 예시 API 함수
  const fetchAPI = async (channelId: number): Promise<SsafyUser[]> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        // 여기서는 더미 데이터를 반환

        resolve(dummySsafyUsers)
      }, 1000)
    })
  }

  useEffect(() => {
    const fetchUserInChannelList = async () => {
      const res = await fetchAPI(channel.channelId)
      setUserInChannelList(res)
    }

    fetchUserInChannelList()
  }, [])

  useEffect(() => {
    console.log(selectedUserInChannelList)
  }, [selectedUserInChannelList])

  const handleSelectedUserInChannelList = (user: SsafyUser, checked: boolean) => {
    checked
      ? setSelectedUserInChannelList((prev) => [...prev, user])
      : setSelectedUserInChannelList(
          selectedUserInChannelList.filter((each) => each.userId !== user.userId),
        )
  }

  return { userInChannelList, selectedUserInChannelList, handleSelectedUserInChannelList }
}
