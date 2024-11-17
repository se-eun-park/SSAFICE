import { useEffect, useState } from 'react'
import { dummySsafyUsers, MattermostChannel, SsafyUser, SsafyUserApiResponse } from './types'
import { Pagenation } from '@/shared/model'

export const useManageMembersTabContent = (channel: MattermostChannel, pageNumber?: number) => {
  const [userInChannelList, setUserInChannelList] = useState<SsafyUser[]>([])
  const [selectedUserInChannelList, setSelectedUserInChannelList] = useState<SsafyUser[]>([])
  const [selectedAll, setSelectedAll] = useState(false)
  // const [pageInfo, setPageInfo] = useState<{
  //   pageable: Object
  //   totalPages: number
  //   totalElements: number
  // } | null>(null)
  const [pageInfo, setPageInfo] = useState<Pagenation | null>(null)

  // 1초 후에 데이터를 반환하는 예시 API 함수
  const fetchAPI = async (
    channelId: number,
    pageNumber?: number,
  ): Promise<SsafyUserApiResponse> => {
    return new Promise((resolve) => {
      setTimeout(() => {
        // 여기서는 더미 데이터를 반환
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
