import React, { Suspense } from 'react'
import ReactModal from 'react-modal'
import { findModalByName, ModalName } from '@/shared/model'

type CommonModalParams = {
  name: ModalName
  opened: boolean // 모달의 열림 상태
  closeRequest: () => void // 모달을 닫을 수 있는 함수
}

export const CommonModal = ({ name, opened, closeRequest }: CommonModalParams) => {
  const modal = findModalByName(name) // 모달을 이름으로 찾아 매치합니다.
  if (!modal) {
    throw new Error(`Modal with name '${name}' not found.`) // name이 유효하지 않으면 에러를 던짐
  }

  // if (modal?.hasCloseButton) {
  //   // const close = document.getElementsByTagName()
  // }

  const commonModalStyle: ReactModal.Styles = {
    overlay: {
      position: 'fixed',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: 'rgba(209, 213, 219, 0.5)', // color-bg-disabled 50%
      zIndex: 1000,
    },
    content: {
      // position setting
      position: 'absolute',
      top: '50%',
      left: '50%',
      transform: 'translate(-50%, -50%)',

      // size setting
      width: modal?.width,
      height: modal?.height,
      padding: 'none',

      // appearance setting
      background: '#fff', // color-bg-primary
      borderRadius: '0px',
      border: 'none',
      boxShadow: modal?.hasShadow ? '4px 4px 4px rgba(0, 0, 0, 0.25)' : 'none', // 그림자 적용

      // content setting
      overflow: 'auto',
    },
  }

  return opened ? (
    <ReactModal isOpen={opened} onRequestClose={closeRequest} style={commonModalStyle}>
      <Suspense fallback={<div>Loading...</div>}>
        <div className='flex w-full h-full'>{modal?.modal}</div>
      </Suspense>
    </ReactModal>
  ) : null
}
