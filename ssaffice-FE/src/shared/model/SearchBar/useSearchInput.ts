import { useEffect, useState } from 'react'

export const useSearchInput = () => {
  const [value, setValue] = useState('')

  const searchApi = (keyword: string) => {
    console.log(`검색 수행: ${keyword}`)
    // 추후 API 로직을 여기에 넣어 주세요.
  }

  useEffect(() => {
    const timer = setTimeout(() => {
      if (value) {
        searchApi(value)
      }
    }, 500)

    return () => clearTimeout(timer)
  }, [value])

  const handleValue = (keyword: string) => {
    setValue(keyword)
  }

  return { value, handleValue }
}
