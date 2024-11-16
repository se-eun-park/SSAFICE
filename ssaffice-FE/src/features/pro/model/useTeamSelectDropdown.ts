import { useEffect, useState } from 'react'
import { MattermostChannel, MattermostTeam } from './types'

export const useTeamSelectDropdown = (datas: MattermostTeam[]) => {
  const [selectedIndex, setSelectedIndex] = useState<number>()
  const [channelList, setChannelList] = useState<MattermostChannel[]>()
  // const [selectedChannelList, setSelectedChannelList] = useState<Set<number>>()
  const selectedChannelList = new Set<number>()

  const handleSelectedIndex = (index: number) => {
    setSelectedIndex(index)
  }

  const handleSelectChannel = (channelId: number, checked: boolean): number => {
    console.log(channelId)
    checked ? selectedChannelList.add(channelId) : selectedChannelList.delete(channelId)
    return selectedChannelList.size
  }

  const saveSelectedChannels = () => {
    // selectedChannelList를 적용하는 로직.
    console.log(handleSelectedIndex)
  }

  useEffect(() => {
    if (selectedIndex !== undefined) setChannelList(datas[selectedIndex].channels)
  }, [selectedIndex])

  useEffect(() => {
    console.log(selectedChannelList)
  }, [selectedChannelList])

  return {
    handleSelectedIndex,
    selectedIndex,
    channelList,
    saveSelectedChannels,
    handleSelectChannel,
    selectedChannelList,
  }
}
