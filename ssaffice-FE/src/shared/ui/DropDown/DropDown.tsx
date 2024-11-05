import { Children, isValidElement, ReactNode } from 'react'
import {
  DropDownImageProps,
  DropDownTitleProps,
  DropDownSubTitleProps,
  DropDownContentProps,
  DropDownMainProps,
} from '@/shared/model'

// 서브 컴포넌트

function DropDownImage({ children }: DropDownImageProps) {
  return children
}

function DropDownTitle({ children, color = 'text-color-text-primary' }: DropDownTitleProps) {
  return <h1 className={`body-sm-medium ${color}`}>{children}</h1>
}

function DropDownSubTitle({ children, color = 'text-color-text-disabled' }: DropDownSubTitleProps) {
  return <h2 className={`body-xs-medium ${color}`}>{children}</h2>
}

// 서브 컴포넌트 레이아웃

function getDropDownImage(children: ReactNode) {
  const DropDownImageType = (<DropDownImage />).type
  const childrenArray = Children.toArray(children)

  return childrenArray.filter((child) => isValidElement(child) && child.type === DropDownImageType)
}

function getDropDownContent(children: ReactNode) {
  const DropDownImageType = (<DropDownImage />).type
  const childrenArray = Children.toArray(children)

  return childrenArray.filter((child) => isValidElement(child) && child.type !== DropDownImageType)
}

function DropDownContent({ children, onClickEvent, isHover }: DropDownContentProps) {
  const Wrapper = onClickEvent ? 'button' : 'div'
  const dropDownImage = getDropDownImage(children)
  const dropDownContent = getDropDownContent(children)

  return (
    <Wrapper
      onClick={onClickEvent}
      className={`flex items-center px-spacing-16 py-spacing-10 ${isHover ? 'hover:bg-color-bg-interactive-secondary-hover' : ''}`}
    >
      {dropDownImage.length > 0 && <div className='mr-spacing-12'>{dropDownImage}</div>}
      <div className='flex flex-col'>{dropDownContent}</div>
    </Wrapper>
  )
}

// 메인 컴포넌트

function DropDownMain({ children, isOpen, isShadow, position }: DropDownMainProps) {
  if (!isOpen) {
    return null
  }

  return (
    <div
      className={`absolute z-20 flex flex-col border divide-y-2 bg-color-bg-primary border-color-border-tertiary rounded-radius-8 min-w-60 ${position} ${isShadow ? 'effect-shadow' : ''}`}
    >
      {children}
    </div>
  )
}

export const DropDown = Object.assign(DropDownMain, {
  Content: DropDownContent,
  Image: DropDownImage,
  Title: DropDownTitle,
  SubTitle: DropDownSubTitle,
})
