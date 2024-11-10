type useDateFormatterParam =
  | 'YYYY-MM-DD(string)'
  | 'YYYY-MM-DD(date)'
  | 'PM/AM HH:MM'
  | 'MM월 DD일 ?요일'
  | 'YYYY-MM-DD(?)'
  | 'D-?'
// D-Day의 경우 현재 날짜를 기준으로 리턴하면 되므로 date를 전달하지 않아도 됩니다.

export const useDateFormatter = (type: useDateFormatterParam, date?: Date): string | Date => {
  if (!date) date = new Date() // date 파라메터가 전달되지 않은 경우 현재 시각 기준으로 리턴합니다.

  const yyyy: string = String(date.getFullYear())
  const mm: string = String(date.getMonth() + 1).padStart(2, '0')
  const dd: string = String(date.getDate()).padStart(2, '0')
  const weekdays = ['일', '월', '화', '수', '목', '금', '토']
  const dayOfWeek: string = weekdays[date.getDay()]
  const hours24: number = date.getHours()
  const hours12: number = hours24 % 12 === 0 ? 12 : hours24 % 12
  const ampm: string = hours24 >= 12 ? 'PM' : 'AM'
  const minutes: string = String(date.getMinutes()).padStart(2, '0')
  const seconds: string = String(date.getSeconds()).padStart(2, '0')

  switch (type) {
    case 'YYYY-MM-DD(string)':
      return `${yyyy}-${mm}-${dd}`
    case 'YYYY-MM-DD(date)':
      return new Date(`${yyyy}-${mm}-${dd}`)
    case 'PM/AM HH:MM':
      return `${ampm} ${String(hours12).padStart(2, '0')}:${minutes}`
    case 'MM월 DD일 ?요일':
      return `${mm}월 ${dd}일 ${dayOfWeek}요일`
    case 'YYYY-MM-DD(?)':
      return `${yyyy}-${mm}-${dd}(${dayOfWeek})`
    case 'D-?':
      return `D-${(Number(new Date()) - Number(date)) / 24 == 0 ? 'day' : (Number(new Date()) - Number(date)) / 24}`

    default:
      console.log(
        'DateFormatter가 정상 작동되지 않습니다. useDateFormatter에 전달된 파라메터 값(type)을 확인해 주세요.',
      )
      return ''
  }
}
