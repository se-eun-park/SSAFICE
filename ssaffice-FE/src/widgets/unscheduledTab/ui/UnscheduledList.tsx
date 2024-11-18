import { useSortingUnscheduled } from '@/features/unscheduledTab'
import { UnscheduledDateGroup } from './UnscheduledDateGroup'
import type { UnscheduledListDisplay } from '@/features/unscheduledTab'
import { instance } from '@/shared/model/index.ts'
import { useQuery } from '@tanstack/react-query'
import { useState } from 'react'

export const UnscheduledList = ({ page }: { page: number }) => {
  const [resultList, setResultList] = useState<UnscheduledListDisplay | null>(null)
  const {} = useQuery({
    queryKey: ['unscheduled', page],
    queryFn: async () => {
      const response = await instance.get(`/api/schedules/unregistered?page=${page}&size=20`)
      setResultList((prevList) => ({
        ...prevList,
        content: [...(prevList?.content || []), ...response.data.content],
      }))
      return response.data
    },
  })

  const datas: UnscheduledListDisplay = useSortingUnscheduled(
    resultList?.content || [],
    'by registration',
  )
  return (
    <div className='w-full h-full '>
      <div className='relative flex flex-col w-full h-full '>
        {Object.entries(datas).map(([date, dailyUnschedules], index) => (
          <UnscheduledDateGroup
            key={date}
            date={date}
            dailyUnschedules={dailyUnschedules}
            isLast={index === Object.entries(datas).length - 1}
          />
        ))}
      </div>
    </div>
  )
}
