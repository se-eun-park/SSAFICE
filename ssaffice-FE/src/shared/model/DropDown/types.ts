import { ReactNode } from 'react'

export type DropDownImageProps = {
  children?: ReactNode
}

export type DropDownTitleProps = {
  children?: ReactNode
  color?: string
}

export type DropDownSubTitleProps = {
  children?: ReactNode
  color?: string
}

export type DropDownContentProps = {
  children?: ReactNode
  onClickEvent?: () => void
  isHover?: boolean
}

export type DropDownMainProps = {
  children?: ReactNode
  isOpen: boolean
  isShadow?: boolean
  position?: string
}
