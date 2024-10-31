import { useEffect, useState } from 'react'

//MARK: DATAS
const tabLabels: string[] = ['mm연동', '대시보드', '캘린더', '할 일 등록', '리마인드']

const tabValues: string[] = [
  `0 - Lorem ipsum dolor sit amet consectetur. 
Lacinia volutpat non mollis parturient commodo. 
Lorem ipsum dolor sit amet consectetur. 
Lacinia volutpat non mollis parturient commodo.`,
  `1 - Lorem ipsum dolor sit amet consectetur. 
Lacinia volutpat non mollis parturient commodo. 
Lorem ipsum dolor sit amet consectetur. 
Lacinia volutpat non mollis parturient commodo.`,
  `2 - Lorem ipsum dolor sit amet consectetur. 
Lacinia volutpat non mollis parturient commodo. 
Lorem ipsum dolor sit amet consectetur. 
Lacinia volutpat non mollis parturient commodo.`,
  `3 - Lorem ipsum dolor sit amet consectetur. 
Lacinia volutpat non mollis parturient commodo. 
Lorem ipsum dolor sit amet consectetur. 
Lacinia volutpat non mollis parturient commodo.`,
  `4 - Lorem ipsum dolor sit amet consectetur. 
Lacinia volutpat non mollis parturient commodo. 
Lorem ipsum dolor sit amet consectetur. 
Lacinia volutpat non mollis parturient commodo.`,
]

const tabImages: string[] = [
  `https://picsum.photos/id/10/600/400`,
  `https://picsum.photos/id/24/600/400`,
  `https://picsum.photos/id/37/600/400`,
  `https://picsum.photos/id/38/600/400`,
  `https://picsum.photos/id/40/600/400`,
]

type SelectedData = {
  selectedTitle: string
  selectedContent: string
  selectedImage: string
}

//MARK: LOGIC
const useLandingPageModel = () => {
  const [selectedIndex, setSelectedIndex] = useState<number>(0)
  // 0번 인덱스를 기본으로 보여줍니다.

  const [selectedData, setSelectedData] = useState<SelectedData>({
    selectedTitle: tabLabels[selectedIndex],
    selectedContent: tabValues[selectedIndex],
    selectedImage: tabImages[selectedIndex],
  })

  const handleSelectedIndex = (index: number) => {
    setSelectedIndex(index)
  }

  useEffect(() => {
    setSelectedData({
      selectedTitle: tabLabels[selectedIndex],
      selectedContent: tabValues[selectedIndex],
      selectedImage: tabImages[selectedIndex],
    })
  }, [selectedIndex])

  return {
    tabLabels,
    selectedIndex,
    selectedTitle: selectedData.selectedTitle,
    selectedContent: selectedData.selectedContent,
    selectedImage: selectedData.selectedImage,
    handleSelectedIndex,
  }
}

export default useLandingPageModel
