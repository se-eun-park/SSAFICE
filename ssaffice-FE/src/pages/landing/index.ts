export { Link } from 'react-router-dom'
export { LandingPage } from './ui/Page/Page'

// 다른 레이어에서 정의한 요소는 index로 import
export { useLandingPageModel } from '@/features/landing/index'
export { tabLabels, tabImages, tabValues } from '@/shared/data/index'
