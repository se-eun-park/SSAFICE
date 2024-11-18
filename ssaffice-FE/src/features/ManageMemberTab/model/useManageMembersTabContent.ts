import { useEffect, useState } from 'react'
import { dummySsafyUsers, MattermostChannel, SsafyUser, SsafyUserApiResponse } from './types'
import { Pagenation } from '@/shared/model'

export const useManageMembersTabContent = (channel: MattermostChannel) => {
  const [userInChannelList, setUserInChannelList] = useState<SsafyUser[]>([])
  const [selectedUserInChannelList, setSelectedUserInChannelList] = useState<SsafyUser[]>([])
  const [selectedAll, setSelectedAll] = useState(false)
  const [pageInfo, setPageInfo] = useState<Pagenation | null>(null)

  const fetchAPI = async (
    channelId: number,
    pageNumber?: number,
  ): Promise<SsafyUserApiResponse> => {
    String(channelId) // API 연동 후 삭제(오류 방지용 로직)
    return new Promise((resolve) => {
      setTimeout(() => {
        if (pageNumber === undefined) pageNumber = 0
        resolve(dummySsafyUsers[pageNumber])
      }, 1000)
    })
  }

  const fetchUserInChannelList = async (pageNumber?: number) => {
    await fetchAPI(channel.channelId, pageNumber).then((res) => {
      setUserInChannelList(res.users)
      if (res.pageable && res.totalPages !== undefined && res.totalElements !== undefined)
        setPageInfo({
          pageNumber: res?.pageable?.pageNumber,
          pageSize: res?.pageable?.pageSize,
          totalPages: res?.totalPages,
          totalElements: res?.totalElements,
        })
    })
  }

  useEffect(() => {
    fetchUserInChannelList()
  }, [])

  const handleSelectedUserInChannelList = (user: SsafyUser, checked: boolean) => {
    checked
      ? setSelectedUserInChannelList((prev) => [...prev, user])
      : setSelectedUserInChannelList(
          selectedUserInChannelList.filter((each) => each.userId !== user.userId),
        )
  }

  const handleSelectedAllUserInChannelList = (checked: boolean) => {
    if (checked) {
      setSelectedAll(true)
      setSelectedUserInChannelList(userInChannelList)
    } else {
      setSelectedAll(false)
      setSelectedUserInChannelList([])
    }
  }

  return {
    userInChannelList,
    selectedUserInChannelList,
    handleSelectedUserInChannelList,
    handleSelectedAllUserInChannelList,
    selectedAll,
    pageInfo,
    fetchUserInChannelList,
  }
}
