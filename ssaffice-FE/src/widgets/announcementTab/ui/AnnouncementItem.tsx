import { AnnouncementItemDisplayType } from '@/features/announcementTab'
import { useDateFormatter } from '@/shared/model'

type AnnouncementItemParam = {
  announcementItem: AnnouncementItemDisplayType
}
export const AnnouncementItem = ({ announcementItem }: AnnouncementItemParam) => {
  return (
    <div
      className='
        flex gap-spacing-12
        p-spacing-16 h-[76px]
        bg-color-bg-primary
        border border-color-border-tertiary
        rounded-radius-8 
    '
    >
      <div
        className='
        w-spacing-40 h-spacing-40 
        bg-color-bg-disabled
        aspect-square rounded-full
      '
      >
        {/* 프로필 이미지 넣어 주세요 */}
        {announcementItem?.user?.profileImageUrl && (
          <img
            src={announcementItem?.user?.profileImageUrl}
            alt='사진 없음'
            className='w-full h-full'
          />
        )}
      </div>
      <div
        className='
        flex flex-col gap-spacing-8
        w-full h-full 
      '
      >
        <div className='flex gap-spacing-8'>
          {/* 프로 이름 */}
          <div
            className='
            flex 
            text-color-text-primary body-md-medium
          '
          >
            {announcementItem.user.name}
          </div>
          {/* 시각 */}
          <div
            className='
            flex self-end
            text-color-text-disabled body-xs-medium
          '
          >{`${useDateFormatter('PM/AM HH:MM', announcementItem.createdAt)}`}</div>
        </div>
        <div className='text-color-text-primary body-sm-medium'>
          {/* 공지 제목 */}
          {announcementItem.title}
        </div>
      </div>
      <div className='flex justify-end items-end'>{/* 드롭다운 SVG */} ▼</div>
    </div>
  )
}
