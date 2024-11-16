import { useEffect, useState } from 'react'
import { MattermostChannel, MattermostTeam } from './types'

export const useTeamSelectDropdown = (datas: MattermostTeam[]) => {
  const [selectedIndex, setSelectedIndex] = useState<number>()
  const [channelList, setChannelList] = useState<MattermostChannel[]>()
  const [selectedChannelList, setSelectedChannelList] = useState<number[]>([])

  const handleSelectedIndex = (index: number) => {
    setSelectedIndex(index)
  }

  const handleSelectChannel = (channelId: number, checked: boolean) => {
    checked
      ? setSelectedChannelList((prev) => [...prev, channelId])
      : setSelectedChannelList((prev) => prev.filter((each) => each !== channelId))
  }

  const saveSelectedChannels = () => {
    // selectedChannelList를 적용하는 로직.
    console.log(selectedChannelList)
  }

  useEffect(() => {
    if (selectedIndex !== undefined) setChannelList(datas[selectedIndex].channels)
  }, [selectedIndex])

  return {
    handleSelectedIndex,
    selectedIndex,
    channelList,
    saveSelectedChannels,
    handleSelectChannel,
    selectedChannelList,
  }
}
