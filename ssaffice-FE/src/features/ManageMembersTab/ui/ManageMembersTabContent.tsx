import { AddIcon, CheckedRoundedSquare } from '@/assets/svg'
import type { MattermostChannel } from '@/features/ManageMembersTab'
import { useManageMembersTabContent } from '../model/useManageMembersTabContent'

type ManageMembersTabContentProps = {
  channel: MattermostChannel
}
export const ManageMembersTabContent = ({ channel }: ManageMembersTabContentProps) => {
  // 컴포넌트 로드 시 교육생 리스트를 API로 가져올 수 있도록 해주세요.
  // channelId 보내면 교육생 리스트가 와야 합니다.
  const { userInChannelList, selectedUserInChannelList, handleSelectedUserInChannelList } =
    useManageMembersTabContent(channel)

  type TableHeaderProps = {
    width: string
    name: string
  }

  const headerSize: TableHeaderProps[] = [
    {
      width: '80px',
      name: '기수',
    },
    {
      width: '100px',
      name: '지역',
    },
    {
      width: '130px',
      name: '트랙',
    },
    {
      width: '59px',
      name: '반',
    },
    {
      width: '90px',
      name: '역할',
    },
    {
      width: '325px',
      name: '이메일',
    },
    {
      width: '180px',
      name: '',
    },
  ]
  return (
    <div
      className='
      flex flex-col
      w-full h-full
    '
    >
      {/* 상단 버튼 영역 */}
      <div
        className='
        flex justify-end
        py-spacing-20 px-spacing-24
      '
      >
        <button
          className='
          flex gap-spacing-2 items-center
          py-spacing-8 px-spacing-16
          bg-color-bg-interactive-primary rounded-radius-4
          hover:bg-color-bg-interactive-primary-hover
          active:bg-color-bg-interactive-primary-press
        '
        >
          <div className='w-spacing-12 h-spacing-12'>
            <AddIcon color='#FFFFFF' />
          </div>
          <div
            className='
            text-color-text-interactive-inverse body-md-medium
          '
          >
            관리자 추가
          </div>
        </button>
      </div>

      {/* 채널 참여자 리스트 */}
      <div
        className='
        flex flex-col
        w-full
      '
      >
        {/* th 영역 */}
        <div
          className='
          flex items-center
          h-[44px] 
          bg-color-bg-tertiary
          border-b border-spacing-1 border-color-border-tertiary
        '
        >
          <div
            className='
            flex items-center gap-spacing-12 
            w-[400px] py-spacing-12 px-spacing-24
            '
          >
            <div
              className='
              w-spacing-20 h-spacing-20
              bg-color-bg-primary 
              border border-spacing-1 border-color-border-disabled 
              rounded-radius-6
              '
            >
              {/* roundedSquareCheckbox */}
            </div>
            <div
              className='
                text-color-text-disabled body-xs-medium'
            >
              이름
            </div>
          </div>

          {headerSize.map((each) => (
            <div
              className={`
                flex items-center
                w-[${each.width}] py-spacing-12 px-spacing-24
                text-color-text-disabled body-xs-medium
              `}
              key={each.width}
            >
              {each.name}
            </div>
          ))}
        </div>

        {/* 교육생 출력 영역*/}
        <div
          className='
            flex flex-col 
            border border-spacing-1 border-color-border-tertiary
          '
        >
          {userInChannelList.map((each) => (
            <label
              key={each.userId}
              className={`
              flex items-center
              w-full h-[72px]
              ${selectedUserInChannelList.includes(each) ? 'bg-color-bg-info-subtle' : ''}
              hover:bg-color-bg-info-subtle
              `}
              htmlFor={`user-${each.userId}`}
            >
              <input
                className='opacity-0 w-0 h-0'
                id={`user-${each.userId}`}
                type='checkbox'
                value={each.userId}
                checked={selectedUserInChannelList.includes(each)}
                onChange={(e) => handleSelectedUserInChannelList(each, e.target.checked)}
              />

              <div
                className='
                    flex items-center gap-spacing-12
                    w-[400px] py-spacing-16 px-spacing-24
                  '
              >
                {selectedUserInChannelList.includes(each) ? (
                  <div className='w-spacing-20 h-spacing-20'>
                    <CheckedRoundedSquare />
                  </div>
                ) : (
                  <div
                    className='
                    w-spacing-20 h-spacing-20
                    bg-color-bg-primary 
                    border border-spacing-1 border-color-border-disabled 
                    rounded-radius-6
                    '
                  />
                )}

                {each.profileImage ? (
                  <img
                    src={each.profileImage}
                    alt='프로필 이미지'
                    className='w-spacing-40 h-spacing-40 rounded-full aspect-square'
                  />
                ) : (
                  <div
                    className='
                      w-spacing-40 h-spacing-40 
                      bg-color-bg-disabled
                      rounded-full aspect-square'
                  />
                )}
              </div>
            </label>
          ))}
        </div>
      </div>
    </div>
  )
}
