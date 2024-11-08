import React, { Suspense } from 'react'
import { ModalName, ModalElement } from './types'

const LoginErrorModal = React.lazy(() =>
  import('@/features/session/login').then((module) => ({
    default: (props: { errorType: 'EmailValidFalse' | 'LoginFail'; closeRequest: () => void }) => (
      <module.LoginErrorModal {...props} />
    ),
  })),
)

export const findModalByName = (
  name: ModalName,
  closeRequest: () => void,
): ModalElement | undefined => {
  // 모달 tsx를 별도의 파일로 작성한 후에 여기에서 리스트를 만들어 주세요
  const ModalList: Record<ModalName, ModalElement> = {
    EmailValidFalse: {
      modal: (
        <Suspense fallback={<div>Loading...</div>}>
          <LoginErrorModal errorType='EmailValidFalse' closeRequest={closeRequest} />
        </Suspense>
      ),
      width: '482px',
      height: '207px',
      hasShadow: true,
      hasCloseButton: true,
    },
    LoginFail: {
      modal: (
        <Suspense fallback={<div>Loading...</div>}>
          <LoginErrorModal errorType='LoginFail' closeRequest={closeRequest} />
        </Suspense>
      ),
      width: '443px',
      height: '207px',
      hasShadow: true,
      hasCloseButton: true,
    },
  }

  return ModalList[name]
}
