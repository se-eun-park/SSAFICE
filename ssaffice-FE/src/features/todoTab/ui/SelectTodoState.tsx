import { useEffect, useRef, useState } from 'react'
import { useClickOutsideToggle, useHover } from '@/shared/model'
import { DropDown } from '@/shared/ui'
import { DownArrowIcon, TodoFlag } from '@/assets/svg'
import { SelectedStateElements } from '../model/SelectedStateElements'

type SelectTodoStateProps = {
  state?: string
  actionType: string // filter(필터), modify(개별 할일 상태), create(할일 간편 등록)
}

export const SelectTodoState = ({ state = 'default', actionType }: SelectTodoStateProps) => {
  // state
  const [isOpen, setIsOpen] = useState(false)
  const [selectedState, setSelectedState] = useState(state)
  const [isDefaultHover, setIsDefaultHover] = useState(true)
  const dropDownRef = useRef<HTMLDivElement | null>(null)

  // hook
  useClickOutsideToggle(dropDownRef, setIsOpen)
  const [hoverRef, isHover] = useHover<HTMLDivElement>()

  // model
  const selectedStateElements =
    actionType === 'filter'
      ? SelectedStateElements({ selectedState, isOpen })
      : actionType === 'modify'
        ? SelectedStateElements({ selectedState, isOpen, actionType: 'narrow plain' })
        : SelectedStateElements({ selectedState, isOpen, actionType: 'with label' })

  // effect
  // hover 이벤트 발생 시, 기본 hover 상태를 해제
  useEffect(() => {
    if (!isDefaultHover) return

    if (isHover) {
      setIsDefaultHover(false)
    }
  }, [isHover])

  // dropDown이 닫힐 때, 기본 hover 상태로 변경
  useEffect(() => {
    if (!isOpen) {
      setIsDefaultHover(true)
    }
  }, [isOpen])

  // event
  const handleOnClickOpen = () => {
    setIsOpen(!isOpen)
  }

  const handleOnClickContent = (type: string, actionType: string) => {
    setSelectedState(type)
    setIsDefaultHover(true)

    if (actionType === 'filter') {
      console.log('추후에 필터링 api 연결')
    } else {
      console.log('추후에 수정 api 연결')
    }
  }

  return (
    <div ref={dropDownRef} className='relative w-fit'>
      {actionType === 'create' ? (
        <button
          onClick={handleOnClickOpen}
          className='
          flex gap-spacing-4 items-center
          rounded-radius-8
          hover:bg-color-bg-interactive-secondary-hover
          active:bg-color-bg-interactive-secondary-press
          '
        >
          <div
            className='
            flex justify-center items-center
            w-spacing-24 h-spacing-24 
            '
          >
            <div className='flex w-[14px] h-[18px]'>
              <TodoFlag
                type={
                  selectedState === 'progress'
                    ? 'IN_PROGRESS'
                    : selectedState === 'done'
                      ? 'DONE'
                      : 'TODO'
                }
              />
            </div>
          </div>

          <div
            className='
            flex justify-center items-center
            w-spacing-16 h-spacing-16
          '
          >
            <div className='flex items-center'>
              <DownArrowIcon className='w-4' />
            </div>
          </div>
        </button>
      ) : (
        <button onClick={handleOnClickOpen} className={selectedStateElements?.bgClass}>
          <div
            className={`flex items-center ${selectedState === 'default' ? 'gap-x-spacing-10' : ''}`}
          >
            <p className={selectedStateElements?.labelClass}>{selectedStateElements?.label}</p>
            <DownArrowIcon
              className={`w-4 ${selectedState !== 'default' ? 'stroke-color-border-inverse' : ''}`}
            />
          </div>
        </button>
      )}

      <div ref={hoverRef}>
        <DropDown
          isOpen={isOpen}
          position={`top-8 ${actionType === 'modify' && 'right-0'}`}
          width='w-[12.5rem]'
          isPaddingY={!(actionType === 'modify')}
        >
          {selectedStateElements?.contents.map((content, index) => (
            <DropDown.Content
              key={index}
              onClickEvent={() => handleOnClickContent(content.type, actionType)}
              isHover={true}
              isHoverHighLight={true}
              isDefaultHover={isDefaultHover && content.isDefaultHover}
            >
              <p className={content.classname}>{content.label}</p>
            </DropDown.Content>
          ))}
        </DropDown>
      </div>
    </div>
  )
}
