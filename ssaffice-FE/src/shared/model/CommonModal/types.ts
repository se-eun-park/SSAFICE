import { LazyExoticComponent, ReactNode } from 'react'

export type ModalName = 'EmailValidFalse' | 'LoginFail'

export type ModalElement = {
  modal: // LazyExoticComponent<React.ComponentType<any>>
  ReactNode
  width: string
  height: string
  hasShadow?: boolean
  hasCloseButton?: boolean
}
