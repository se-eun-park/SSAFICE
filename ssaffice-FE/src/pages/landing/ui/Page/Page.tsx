import { Link } from 'react-router-dom'
import useLandingPageModel from '../../model/useLandingPageModel'

//MARK: 공통 CSS
/*
  className 순서는 이렇게 작성되었습니다. 
  1) flex 관련 속성(flex, flex-col 등의 방향 설정, gap 설정, justify / items / self 등의 정렬 설정 순서로) 
  2) 크기 관련 속성(width, height, margin, padding, z-index 순서로)
  3) 글자 관련 속성(글자 색, heading, 줄바꿈(whiteSpace) 순서로)
  4) 배경 관련 속성(배경 색, border 순서로) 
  5) 기타 속성(radius, 기타 속성 순서로)
*/
const btnClasses: string = ` 
  px-spacing-28 py-spacing-10
  text-color-text-interactive-secondary heading-desktop-lg 
  bg-color-bg-interactive-selected 
  rounded-radius-32 
` // 화면 하단 버튼 요소에 공통 적용됩니다.

const selectedBtnClasses: string = `
  ${btnClasses}
  text-color-text-interactive-secondary-press
  bg-color-bg-interactive-selected-press
  border border-color-border-focus-ring border-2
` // 선택된 탭 버튼에 적용됩니다.

export const LandingPage = () => {
  const {
    tabLabels,
    selectedIndex,
    selectedTitle,
    selectedContent,
    selectedImage,
    handleSelectedIndex,
  } = useLandingPageModel()

  return (
    <div className='flex flex-col'>
      {/* MARK: 화면 상단
       */}
      <div
        className='
        flex flex-col gap-spacing-32 items-center justify-start
        h-[400px] py-spacing-40
        '
      >
        {/* 헤더 텍스트 영역 */}
        <div className='flex flex-col gap-spacing-16 items-center'>
          <div className='text-color-text-primary heading-desktop-5xl'>SSAFY 일정관리</div>
          <div className='text-color-text-primary heading-desktop-4xl'>SSAFICE와 함께 시작하기</div>
          <div className='text-color-text-primary heading-desktop-lg'>
            SSAFICE는 SSAFY 구성원에게 최적의 일정 관리 서비스를 제공합니다.
          </div>
        </div>
        <Link
          to='/login'
          className='
            flex
            px-spacing-28 py-spacing-10
            text-white heading-desktop-lg 
            bg-color-bg-interactive-primary 
            rounded-radius-32 
          '
        >
          SSAFICE 바로가기
        </Link>
      </div>

      {/* MARK: 화면 하단
       */}
      <div
        className='
        flex flex-col gap-spacing-40 
        py-spacing-64 
        border border-t-color-border-tertiary
      '
      >
        {/* 텍스트, 탭 5개 영역 */}
        <div className='flex flex-col gap-spacing-20 items-center'>
          <div className='text-color-text-primary heading-desktop-xl'>
            교육생과 프로 모두에게 최적의 경험을 제공합니다.
          </div>
          {/* button tabs */}
          <div className='flex flex-row gap-spacing-4'>
            {tabLabels.map((each, index) => (
              <button
                type='button'
                className={index === selectedIndex ? selectedBtnClasses : btnClasses}
                onClick={() => handleSelectedIndex(index)}
              >
                {each}
              </button>
            ))}
          </div>
        </div>

        {/* 탭 이미지, 상세설명 영역 */}
        <div
          className='
        flex justify-center 
        w-320 h-100 px-spacing-80
        '
        >
          {/* 탭 설명 영역 */}
          <div
            className='
            flex flex-col gap-spacing-32 items-start justify-center
            w-full 
          '
          >
            <div className='flex flex-col gap-spacing-16 '>
              <div className='text-color-text-primary heading-desktop-4xl'>{selectedTitle}</div>
              <div className='text-color-text-primary heading-desktop-lg whitespace-pre-wrap'>
                {selectedContent}
              </div>
            </div>
            <div className='flex gap-spacing-12 justify-start'>
              <Link to='/login' className='text-color-text-info heading-desktop-lg'>
                SSAFICE 바로가기
              </Link>
              <Link to='/login' className='text-color-text-info heading-desktop-lg'>
                -&gt;
              </Link>
              {/* SVG 영역 */}
            </div>
          </div>

          {/* 이미지 영역 */}
          <div>
            <img src={selectedImage} />
          </div>
        </div>
      </div>
    </div>
  )
}
