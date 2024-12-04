import { useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { CohortSelector, RegionSelector, TrackSelector, ClassSelector } from './UserInfoSelector'
import { useUserIdStore } from '@/entities/session'
import { postUserSignup } from '@/shared/api/User'

export const SignupForm = () => {
  const navigate = useNavigate()

  const userId = useUserIdStore()

  const [isDisabled, setIsDisabled] = useState(true)

  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [nickNameValue, setNickNameValue] = useState('')
  const [cohort, setCohort] = useState(0)
  const [region, setRegion] = useState('')
  const [track, setTrack] = useState('')
  const [class_, setClass] = useState(0)

  const handleNickNameOncChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickNameValue(e.target.value)
  }

  useEffect(() => {
    if (!email || !password || !nickNameValue || !cohort || !region || !track || !class_) {
      setIsDisabled(true)
      return
    }

    setIsDisabled(false)
  }, [email, password, nickNameValue, cohort, region, track, class_])

  const handleOnClickSignup = () => {
    postUserSignup(userId, {
      email: email,
      password: password,
      name: nickNameValue,
      roleIds: ['ROLE_USER'],
      cohortNum: cohort,
      regionCd: region,
      trackCd: track,
      classNum: class_,
    })

    navigate('/login')
  }

  return (
    <div className='flex flex-col w-full h-full gap-y-spacing-64'>
      <div className='flex flex-col w-full h-full gap-y-spacing-24'>
        <div className='flex items-center justify-between'>
          <p className='body-md-semibold text-color-text-tertiary min-w-max'>이메일</p>
          <input
            type='email'
            placeholder='EMAIL'
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className='w-[348px] border border-color-border-secondary rounded-radius-8 body-sm-medium text-color-text-primary px-spacing-16 py-spacing-8 focus:outline-none placeholder:text-color-text-disabled'
          />
        </div>
        <div className='flex items-center justify-between'>
          <p className='body-md-semibold text-color-text-tertiary min-w-max'>비밀번호</p>
          <input
            type='password'
            placeholder='PASSWORD'
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className='w-[348px] border border-color-border-secondary rounded-radius-8 body-sm-medium text-color-text-primary px-spacing-16 py-spacing-8 focus:outline-none placeholder:text-color-text-disabled'
          />
        </div>
        <div className='flex items-center justify-between'>
          <p className='body-md-semibold text-color-text-tertiary min-w-max'>닉네임</p>
          <input
            type='text'
            placeholder='이름[지역_반]'
            value={nickNameValue}
            onChange={handleNickNameOncChange}
            className='w-[348px] border border-color-border-secondary rounded-radius-8 body-sm-medium text-color-text-primary px-spacing-16 py-spacing-8 focus:outline-none placeholder:text-color-text-disabled'
          />
        </div>
        <div className='flex items-center justify-between'>
          <p className='body-md-semibold text-color-text-tertiary min-w-max'>기수</p>
          <CohortSelector value={cohort} setValue={setCohort} />
        </div>
        <div className='flex items-center justify-between'>
          <p className='body-md-semibold text-color-text-tertiary min-w-max'>지역</p>
          <RegionSelector value={region} setValue={setRegion} />
        </div>
        <div className='flex items-center justify-between'>
          <p className='body-md-semibold text-color-text-tertiary min-w-max'>트랙</p>
          <TrackSelector value={track} setValue={setTrack} />
        </div>
        <div className='flex items-center justify-between'>
          <p className='body-md-semibold text-color-text-tertiary min-w-max'>반</p>
          <ClassSelector value={class_} setValue={setClass} />
        </div>
      </div>
      <button
        onClick={handleOnClickSignup}
        disabled={isDisabled}
        className='w-full h-fit py-spacing-16 body-lg-medium text-color-icon-interactive-inverse bg-color-bg-interactive-primary rounded-radius-8 disabled:bg-color-bg-disabled'
      >
        회원가입
      </button>
    </div>
  )
}
