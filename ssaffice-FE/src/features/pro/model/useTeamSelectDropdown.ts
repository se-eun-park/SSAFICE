import { useEffect, useState } from 'react'
import { MattermostChannel, MattermostTeam } from './types'

export const useTeamSelectDropdown = (datas: MattermostTeam[]) => {
  const [selectedIndex, setSelectedIndex] = useState<number>()
  const [selectedTeam, setSelectedTeam] = useState<MattermostTeam | null>(null)
  const [channelList, setChannelList] = useState<MattermostChannel[]>()
  const [selectedChannel, setSelectedChannel] = useState<MattermostChannel | null>(null)

  const [tabName, setTabName] = useState<string | null>(null)

  const handleSelectedIndex = (index: number) => {
    setSelectedIndex(index)
  }

  const handleSelectChannel = (channel: MattermostChannel, checked: boolean) => {
    checked ? setSelectedChannel(channel) : setSelectedChannel(null)
  }

  const saveSelectedChannels = (closeRequest: () => void) => {
    // selectedChannelList를 적용하는 로직.
    // 선택한 채널의 더미 데이터를 가져오는 API 로직을 수행 + 드롭다운 닫아야 함(void 함수 형태의 파라메터로 전달받습니다.)
    console.log(selectedChannel)
    if (selectedTeam && selectedChannel)
      setTabName(`${selectedTeam.name} • ${selectedChannel.name}`)
    closeRequest()
  }

  useEffect(() => {
    if (selectedIndex !== undefined) {
      setChannelList(datas[selectedIndex].channels)
      setSelectedTeam(datas[selectedIndex])
    }
  }, [selectedIndex])

  return {
    handleSelectedIndex,
    selectedIndex,
    channelList,
    saveSelectedChannels,
    handleSelectChannel,
    selectedChannel,
    selectedTeam,
    tabName,
  }
}
