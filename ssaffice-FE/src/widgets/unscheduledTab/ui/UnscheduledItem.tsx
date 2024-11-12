import { FoldUp, SpreadDown } from '@/assets/svg'
import { UnscheduledItemDisplayType } from '@/features/unscheduledTab'
import {
  useCursorHovered,
  useClickedToggle,
  useCustomEmojiRemover,
  useDateFormatter,
} from '@/shared/model'
import Markdown from 'react-markdown'

type UnscheduledItemParam = {
  unscheduledItem: UnscheduledItemDisplayType
}
export const UnscheduledItem = ({ unscheduledItem }: UnscheduledItemParam) => {
  const { isClicked, handleIsClicked } = useClickedToggle()
  const { isHovered, mouseEntered, mouseLeft } = useCursorHovered()

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
        p-spacing-16 h-[76px] ${isClicked && 'pb-0'}`}
        onClick={() => handleIsClicked()}
        onMouseEnter={mouseEntered}
        onMouseLeave={mouseLeft}
      >
        <div
          className='
          w-spacing-40 h-spacing-40 
          bg-color-bg-disabled
          aspect-square rounded-full
        '
        >
          {/* 프로필 이미지 넣어 주세요 */}
          {unscheduledItem.announcement?.user?.profileImageUrl && (
            <img
              src={unscheduledItem.announcement?.user?.profileImageUrl}
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
              {unscheduledItem.announcement.user.name}
            </div>
            {/* 시각 */}
            <div
              className='
              flex self-end
              text-color-text-disabled body-xs-medium
            '
            >{`${useDateFormatter('PM/AM HH:MM', unscheduledItem.announcement.createdAt)}`}</div>
          </div>
          <div className='text-color-text-primary body-sm-medium'>
            {/* 공지 제목 */}
            {unscheduledItem.announcement.title}
          </div>
        </div>

        <div
          className='
          flex flex-col items-end 
          '
        >
          <div
            className={`
                flex gap-spacing-10 self-start items-center justify-around
                w-[88px] h-[24px] px-spacing-8 py-spacing-2
                text-color-text-interactive-inverse body-xs-medium
                bg-color-bg-interactive-primary hover:bg-color-bg-interactive-primary-hover focus:bg-color-bg-interactive-primary-press
                rounded-radius-8 ${isHovered || 'invisible'}
              `}
          >
            <div>등록하기</div>
            {/* '등록하기' 클릭 이벤트 시 propagation stop 꼭 넣어주세요! 지금은 이벤트 연동 전이라 탭 열리고 닫힙니다 */}

            <div>{/* svg */} -&gt;</div>
          </div>

          {/* 드롭다운/업 SVG */}
          <div
            className='
            flex self-end 
            w-spacing-16 h-spacing-16
            '
          >
            {isClicked ? <FoldUp /> : <SpreadDown />}
          </div>
        </div>
      </div>

      {/* 공지 상세보기 영역 */}
      {isClicked && (
        <div
          className='
          flex flex-col 
          pl-spacing-48 pr-spacing-24 pb-spacing-16
          text-color-text-primary body-sm-medium
          '
        >
          {/* markdown */}
          <Markdown>{useCustomEmojiRemover(unscheduledItem.announcement.content)}</Markdown>
        </div>
      )}
    </div>
  )
}
