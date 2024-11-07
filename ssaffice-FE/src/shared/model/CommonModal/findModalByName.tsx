// import React, { ReactNode } from 'react'
// // import { LoginErrorModal } from '@/features/session/login'

// import { ModalName, ModalElement } from './types'

import React, { Suspense } from 'react'
import { ModalName, ModalElement } from './types'

const LoginErrorModal = React.lazy(() =>
  import('@/features/session/login').then((module) => ({
    default: (props: { errorType: 'EmailValidFalse' | 'LoginFail' }) => (
      <module.LoginErrorModal {...props} />
    ),
  })),
)

const ModalList: Record<ModalName, ModalElement> = {
  EmailValidFalse: {
    modal: (
      <Suspense fallback={<div>Loading...</div>}>
        <LoginErrorModal errorType='EmailValidFalse' />
      </Suspense>
    ),
    width: '482px',
    height: '207px',
    hasShadow: true,
  },
  LoginFail: {
    modal: (
      <Suspense fallback={<div>Loading...</div>}>
        <LoginErrorModal errorType='LoginFail' />
      </Suspense>
    ),
    width: '443px',
    height: '207px',
    hasShadow: true,
  },
}

// 모달 이름에 맞는 ModalElement 반환
export const findModalByName = (name: ModalName): ModalElement | undefined => {
  return ModalList[name]
}

// ===================================

// const LoginErrorModal = React.lazy(() =>
//      import('@/features/session/login').then((module) => {
//        return () => <module.LoginErrorModal errorType="EmailValidFalse" />;
//   }))

// const ModalList: Record<ModalName, ModalElement> = {
//   EmailValidFalse: {
//     // modal: LoginErrorModal({ errorType: 'EmailValidFalse' }), // JSX로 평가된 컴포넌트 값
//     //  modal: React.lazy(() => import('@/features/session/login').then((module) => module.LoginErrorModal({ errorType: 'EmailValidFalse' }))),
//     //  modal: React.lazy(() =>
//     //    import('@/features/session/login').then((module) => {
//     //      return () => <module.LoginErrorModal errorType="EmailValidFalse" />;
//     //    })
//     //  ),
//     modal: <LoginErrorModal errorType='EmailValidFalse' />, // JSX로 평가된 컴포넌트 값

//     //modal: <div>값이 들어가야 한다고??</div>,
//     width: '482px',
//     height: '207px',
//     hasShadow: true,
//   },
//   LoginFail: {
//     // modal: LoginErrorModal({ errorType: 'LoginFail' }), // JSX로 평가된 컴포넌트 값
//     // modal: React.lazy(() => import('@/features/session/login').then((module) => module.LoginErrorModal({ errorType: 'LoginFail' }))),
//     //  modal: React.lazy(() =>
//     //   import('@/features/session/login').then((module) => {
//     //     return () => <module.LoginErrorModal errorType="LoginFail" />;
//     //   })
//     // ),
//     modal: <LoginErrorModal errorType='LoginFail' />,
//     width: '443px',
//     height: '207px',
//     hasShadow: true,
//   },
// }

// // 모달 이름에 맞는 ModalElement 반환
// export const findModalByName = (name: ModalName): ModalElement | undefined => {
//   return ModalList[name]
// }

// ===============================

// import React, { ReactNode, Suspense } from 'react';

// // `LoginErrorModal`을 lazy하게 로드
// const LazyLoginErrorModal = React.lazy(() => import('@/features/session/login'));

// export type ModalName = 'EmailValidFalse' | 'LoginFail';

// export type ModalElement = {
//   modal: ReactNode; // JSX.Element 대신 ReactNode로 처리
//   width: string;
//   height: string;
//   hasShadow?: boolean;
// };

// const ModalList: Record<ModalName, ModalElement> = {
//   EmailValidFalse: {
//     modal: (
//       <Suspense fallback={<div>Loading...</div>}>
//         <LazyLoginErrorModal errorType="EmailValidFalse" />
//       </Suspense>
//     ),
//     width: '482px',
//     height: '207px',
//     hasShadow: true,
//   },
//   LoginFail: {
//     modal: (
//       <Suspense fallback={<div>Loading...</div>}>
//         <LazyLoginErrorModal errorType="LoginFail" />
//       </Suspense>
//     ),
//     width: '443px',
//     height: '207px',
//     hasShadow: true,
//   },
// };

// // 모달 이름에 맞는 ModalElement 반환
// export const findModalByName = (name: ModalName): ModalElement | undefined => {
//   return ModalList[name];
// };
