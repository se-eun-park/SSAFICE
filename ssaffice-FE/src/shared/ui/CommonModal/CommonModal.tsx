import { ReactNode } from 'react'
import ReactModal from 'react-modal'

type CommonModalParams = {
  width: string
  height: string // 32px, 2rem 등 단위를 붙여서 써 주세요.
  tsx: ReactNode // 모달 안쪽을 그려낼 tsx를 넣어 주세요
  opened: boolean // 모달의 열림 상태
  closeRequest: () => void // 모달을 닫을 수 있는 함수
  hasShadow?: boolean // 그림자 효과를 포함하는지
}

export const CommonModal = ({
  width,
  height,
  tsx,
  opened,
  closeRequest,
  hasShadow,
}: CommonModalParams) => {
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
      width: width,
      height: height,
      padding: 'none',

      // appearance setting
      background: '#fff', // color-bg-primary
      borderRadius: '0px',
      border: 'none',
      boxShadow: hasShadow ? '4px 4px 4px rgba(0, 0, 0, 0.25)' : undefined, // 그림자 적용

      // content setting
      overflow: 'auto',
    },
  }

  return opened ? (
    <ReactModal isOpen={opened} onRequestClose={closeRequest} style={commonModalStyle}>
      <div className='flex w-full h-full'>{tsx}</div>
    </ReactModal>
  ) : null
}
