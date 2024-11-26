import { useEffect, useState } from 'react'
import { dummySsafyUsers, MattermostChannel, SsafyUser, SsafyUserApiResponse } from './types'
import { Pagenation } from '@/shared/model'
import { useQuery } from '@tanstack/react-query'
import { instance } from '@/shared/api'

export const useManageMembersTabContent = (channel: MattermostChannel) => {
  const [userInChannelList, setUserInChannelList] = useState<SsafyUser[]>([])
  const [selectedUserInChannelList, setSelectedUserInChannelList] = useState<SsafyUser[]>([])
  const [selectedAll, setSelectedAll] = useState(false)
  const [pageInfo, setPageInfo] = useState<Pagenation | null>(null)

  // ====================== 여기 API 다는 걸로 수정해야 함 =====================
  const fetchAPI = async (
    channelId: string,
    pageNumber?: number,
  ): Promise<SsafyUserApiResponse> => {
    const response = await instance.get(
      `api/users/admin?channelId=${channelId}&page=${pageNumber === undefined ? 0 : pageNumber}&size=10&sort=name,asc`,
    )
    return response.data
  }

  const fetchUserInChannelList = async (pageNumber?: number) => {
    await fetchAPI(channel.channelId, pageNumber).then((res) => {
      // await instance.get(
      //   `/admin/12345?page=${pageNumber}&size=10&sort=name,asc`
      // ).then((res) => {
      console.log(res)
      console.log(res.content)
      setUserInChannelList(res.content)
      if (res.pageable && res.totalPages !== undefined && res.totalElements !== undefined)
        setPageInfo({
          pageNumber: res?.pageable?.pageNumber,
          pageSize: res?.pageable?.pageSize,
          totalPages: res?.totalPages,
          totalElements: res?.totalElements,
        })
    })
  }
  // ===========================================

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
