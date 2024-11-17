import { CheckedCircle, DownArrowIcon, ExitButton, SpreadRight } from '@/assets/svg'
import { useClickedToggle, useClickOutsideToggle } from '@/shared/model'
import { DropDown } from '@/shared/ui'
import { useRef } from 'react'
import { dummyMattermostTeams } from '../model/types'
import { useTeamSelectDropdown } from '../model/useTeamSelectDropdown'

export const TeamSelectDropdown = () => {
  const { isClicked, setIsClicked, handleIsClicked } = useClickedToggle()
  const dropDownRef = useRef<HTMLDivElement | null>(null)

  useClickOutsideToggle(dropDownRef, setIsClicked)

  const {
    handleSelectedIndex,
    selectedIndex,
    channelList,
    saveSelectedChannels, // '적용' 버튼에 들어갈 onClick 로직
    handleSelectChannel, // 채널 선택 시
    selectedChannel, // 선택된 채널
  } = useTeamSelectDropdown(dummyMattermostTeams)

  return (
    <div ref={dropDownRef} className='relative'>
      <button
        className='
      flex justify-center items-center gap-spacing-8
      w-fit p-spacing-20
      '
        onClick={handleIsClicked}
      >
        <div
          className='
        text-color-text-primary heading-desktop-lg
        '
        >
          팀 선택
        </div>
        <div
          className='
        flex justify-center items-center
        w-spacing-24 h-spacing-24
        border border-color-border-primary
        rounded-radius-4
        '
        >
          <DownArrowIcon className='w-13' />
        </div>
      </button>

      {/* Dropdown 컴포넌트 */}
      <DropDown isOpen={isClicked} width='w-[640px]' position='top-10 left-5'>
        <div
          className='
          flex flex-col gap-spacing-12
          h-[505px] px-[39px] py-spacing-32
        '
        >
          <div className='flex justify-between h-[29px]'>
            <div className='text-color-text-primary heading-desktop-lg'>팀 선택</div>
            <button className='h-[14px] w-[14px]' onClick={handleIsClicked}>
              <ExitButton />
            </button>
          </div>

          <div className='flex w-full h-full'>
            <div
              className='
              flex flex-col gap-spacing-4 
              w-1/2 h-full
              border-r border-spacing-1 border-color-border-secondary
              overflow-y-scroll
              '
            >
              {dummyMattermostTeams.map((each, index) => (
                <button
                  className={`
                    flex justify-between gap-spacing-10 
                    h-[51px] p-spacing-16
                    ${index === selectedIndex && 'bg-color-bg-interactive-secondary-press'}
                    hover:bg-color-bg-interactive-secondary-hover
                    active:bg-color-bg-interactive-secondary-press
                    
                    `}
                  onClick={() => handleSelectedIndex(index)}
                  key={each.teamId}
                >
                  <div className='text-color-text-primary body-md-medium'>{each.name}</div>
                  <div
                    className='
                    flex justify-center items-center
                    w-spacing-12 h-spacing-12
                  '
                  >
                    <div
                      className='
                      w-spacing-6 h-spacing-10
                    '
                    >
                      <SpreadRight />
                    </div>
                  </div>
                </button>
              ))}
            </div>
            <div className='flex flex-col w-1/2 h-full'>
              {/* hover하는 팀명에 따라 렌더링되는 채널 리스트 달라야 합니다 */}
              {channelList === undefined && (
                <div
                  className='
                    flex justify-center items-center 
                    w-full h-full
                    text-color-text-disabled body-md-medium
                    text-center whitespace-pre-line
                  '
                >
                  {`팀을 선택하면
                  채널을 확인할 수 있습니다.`}
                </div>
              )}
              {channelList?.map((each) => (
                <div key={each.channelId} className='hover:bg-color-bg-interactive-secondary-hover'>
                  <label
                    htmlFor={`radioChannel-${each.channelId}`}
                    className='flex justify-between gap-spacing-10 h-[51px] p-spacing-16'
                  >
                    <div className='text-color-text-primary body-md-medium'>{each.name}</div>
                    <input
                      className='opacity-0 w-0 h-0'
                      id={`radioChannel-${each.channelId}`}
                      type='radio'
                      name='checkChannel'
                      value={each.channelId}
                      checked={selectedChannel === each.channelId}
                      onChange={(e) => handleSelectChannel(each.channelId, e.target.checked)}
                    />
                    {selectedChannel === each.channelId ? (
                      <CheckedCircle />
                    ) : (
                      <div
                        className='
                        flex 
                        w-spacing-20 h-spacing-20 
                        border border-spacing-1 border-color-border-secondary
                        rounded-full aspect-square
                        
                      '
                      ></div>
                    )}
                  </label>
                </div>
              ))}
            </div>
          </div>
          {selectedChannel && (
            <button
              className='
                flex self-end justify-center items-center
                w-[60px] h-[35px] py-spacing-8 px-spacing-16
                text-color-text-interactive-inverse body-md-medium
                bg-color-bg-interactive-primary
                rounded-radius-4
                hover:bg-color-bg-interactive-primary-hover
                active:bg-color-bg-interactive-primary-press
                '
              onClick={() => saveSelectedChannels(handleIsClicked)}
            >
              적용
            </button>
          )}
        </div>
      </DropDown>
    </div>
  )
}
