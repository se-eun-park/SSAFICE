import { instance } from '@/shared/api'
import { useEffect, useState } from 'react'

type UserCounts = {
  noticeCounts: {
    total: number
    essential: number
    enrolled: number
  }

  scheduleCounts: {
    todoCount: number
    inProgressCount: number
    doneCount: number
  }
}
export const useGetUserCounts = () => {
  const [userCounts, setUserCounts] = useState<UserCounts | null>(null)

  useEffect(() => {
    instance.get('/api/users/counts').then((res) => {
      setUserCounts(res.data)
    })
  }, [])

  return {
    noticeCounts: userCounts?.noticeCounts,
    scheduleCounts: userCounts?.scheduleCounts,
  }
}
