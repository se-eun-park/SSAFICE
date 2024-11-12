import { useState } from 'react'

export const useCursorHovered = () => {
  // 컴포넌트 요소에 사용자가 hover를 하고 있는지 / 아닌지 여부를 관리할 때 사용합니다.
  // e.target 태그의 onMouseEnter, onMouseLeave 이벤트와 함께 사용해야 합니다.
  /*
    e.g.
        <div
            onMouseEnter={mouseEntered}
            onMouseLeave={mouseLeft}
        />
    */

  const [isHovered, setIsHovered] = useState(false)

  const mouseEntered = () => {
    setIsHovered(true)
  }

  const mouseLeft = () => {
    setIsHovered(false)
  }

  return { isHovered, mouseEntered, mouseLeft }
}
