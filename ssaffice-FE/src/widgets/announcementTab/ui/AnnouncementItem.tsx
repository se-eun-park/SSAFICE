import { FoldUp, SpreadDown } from '@/assets/svg'
import { AnnouncementItemDisplayType } from '@/features/announcementTab'
import { useClickedToggle } from '@/shared/model'
import { useCustomEmojiRemover, useDateFormatter } from '@/shared/model'
import Markdown from 'react-markdown'

type AnnouncementItemParam = {
  announcementItem: AnnouncementItemDisplayType
}
export const AnnouncementItem = ({ announcementItem }: AnnouncementItemParam) => {
  const { isClicked, handleIsClicked } = useClickedToggle()

  return (
    <div
      className='
      flex flex-col gap-spacing-4
      bg-color-bg-primary
      border border-color-border-tertiary
      rounded-radius-8 
      hover:bg-color-bg-interactive-secondary-hover
      '
    >
      <div
        className={`
        flex gap-spacing-12
        p-spacing-16
        h-[76px]
        ${isClicked && 'pb-0'}`}
        onClick={() => handleIsClicked()}
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
              className='
              w-full h-full 
              aspect-square rounded-full
              '
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
        <div
          className='
          flex justify-end self-end
          w-spacing-16 h-spacing-16
          '
        >
          {/* 드롭다운/업 SVG */}
          {isClicked ? <FoldUp /> : <SpreadDown />}
        </div>
      </div>

      {/* 공지 상세보기 영역 */}
      {isClicked && (
        <div
          className='
          flex flex-col
          pl-spacing-48 pr-spacing-24 pb-spacing-16
          text-color-text-primary body-sm-medium'
        >
          {/* markdown */}
          <Markdown>{useCustomEmojiRemover(announcementItem.content)}</Markdown>
        </div>
      )}
    </div>
  )
}
