import { useState } from 'react'

export const useArticleClicked = () => {
  const [isClicked, setIsClicked] = useState(false)

  const handleIsClicked = () => {
    setIsClicked(!isClicked)
  }

  return { isClicked, handleIsClicked }
}
