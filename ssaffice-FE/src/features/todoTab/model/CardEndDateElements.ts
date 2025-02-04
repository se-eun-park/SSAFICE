import { useMemo } from 'react'
import { useDateFormatter } from '@/shared/model'

type CardEndDateElementsProps = {
  endDateTime: string
  scheduleStatusTypeCd: string
}

export const CardEndDateElements = ({
  endDateTime,
  scheduleStatusTypeCd,
}: CardEndDateElementsProps) => {
  const endDate = useDateFormatter('D-?', new Date(endDateTime)) as string
  const endDateFormatted = parseInt(endDate.split('-')[1])

  const endDateInfo = useMemo(() => {
    if (endDateTime === null) return { endDate: '', color: '' }
    if (scheduleStatusTypeCd === 'DONE') return { endDate: '', color: '' }

    if (endDateFormatted >= 6) {
      return { endDate: `마감 ${endDate}`, color: 'text-color-text-info-bold' }
    } else if (endDateFormatted > 3 && endDateFormatted < 6) {
      return { endDate: `마감 ${endDate}`, color: 'text-color-text-warning-bold' }
    } else if (endDateFormatted <= 3 && endDateFormatted) {
      return { endDate: `마감 ${endDate}`, color: 'text-color-text-danger-bold' }
    } else if (!endDateFormatted && !endDate.includes('D+')) {
      return { endDate: `마감 ${endDate}`, color: 'text-color-text-danger-bold' }
    } else {
      return { endDate: `마감됨`, color: 'text-color-text-danger-bold' }
    }
  }, [endDateTime])

  return endDateInfo
}
