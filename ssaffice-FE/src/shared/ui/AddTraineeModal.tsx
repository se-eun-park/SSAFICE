import { useMemo, useState } from 'react'
import { SearchBar } from './SearchBar'
import { XIcon } from '@/assets/svg'

type AddTraineeModalProps = {
  setIsOpen: (isOpen: boolean) => void
  userIds: string[]
  setUserIds: (userIds: string[]) => void
  userNameList: string[]
  setUserNameList: (userNameList: string[]) => void
  manageType: 'TEAM' | 'PERSONAL' | undefined
}

export const AddTraineeModal = ({
  setIsOpen,
  userIds,
  setUserIds,
  userNameList,
  setUserNameList,
  manageType,
}: AddTraineeModalProps) => {
  // 나중엔 이 상태값이 검색결과 리스트
  const [searchValue, setSearchValue] = useState('')
  const [idList, setIdList] = useState<string[]>(userIds)
  const [nameList, setNameList] = useState<string[]>(userNameList)

  const handleOnclickUser = (userId: string, userName: string) => {
    setIdList((prev) => Array.from(new Set([userId, ...prev])))
    setNameList((prev) => Array.from(new Set([userName, ...prev])))
  }

  const handleOnclickX = (idx: number) => {
    const newNameList = nameList.filter((_, index) => index !== idx)
    setNameList(newNameList)

    const newIdsList = idList.filter((_, index) => index !== idx)
    setIdList(newIdsList)
  }

  const handleOnClickSubmit = () => {
    setUserIds(idList)
    setUserNameList(nameList)
    setIsOpen(false)
  }

  const dummyData = useMemo(() => {
    switch (searchValue) {
      case '':
        return (
          <div className='w-full h-[416px] border flex items-center justify-center border-color-border-primary rounded-radius-4'>
            <p className='body-lg-medium text-color-text-disabled'>검색어를 입력해주세요.</p>
          </div>
        )
      default:
        const userList = [
          {
            userId: '1',
            name: '곽성재',
            profileUrl: 'https://i.pinimg.com/564x/4d/b2/42/4db2422c74f12f70391ec386bf95e4db.jpg',
          },
          {
            userId: '2',
            name: '박세은',
            profileUrl: 'https://i.pinimg.com/564x/4d/b2/42/4db2422c74f12f70391ec386bf95e4db.jpg',
          },
          {
            userId: '3',
            name: '천세경',
            profileUrl: 'https://i.pinimg.com/564x/4d/b2/42/4db2422c74f12f70391ec386bf95e4db.jpg',
          },
          {
            userId: '4',
            name: '이주영',
            profileUrl: 'https://i.pinimg.com/564x/4d/b2/42/4db2422c74f12f70391ec386bf95e4db.jpg',
          },
        ]
        return (
          <div className='w-full h-[416px] border flex divide-y divide-color-border-secondary flex-col border-color-border-primary rounded-radius-4'>
            {userList.map((user) => (
              <button
                key={user.userId}
                onClick={() => handleOnclickUser(user.userId, user.name)}
                className='flex items-center overflow-y-auto gap-x-spacing-12 px-spacing-16 py-spacing-10 scrollbar-hide hover:bg-color-bg-interactive-secondary-hover'
              >
                <img
                  src={user.profileUrl}
                  alt='프로필 이미지'
                  className='w-8 h-8 rounded-radius-circle'
                />
                <p className='max-w-[320px] truncate body-md-medium text-color-text-primary'>
                  {user.name}
                </p>
              </button>
            ))}
          </div>
        )
    }
  }, [searchValue])

  return (
    <div className='z-50 absolute right-2.5 top-12 flex flex-col gap-y-spacing-24 border w-[480px] h-fit bg-color-bg-primary rounded-radius-8 px-spacing-32 py-spacing-16 border-color-border-tertiary'>
      <h1 className='w-full text-center heading-desktop-sm text-color-text-primary'>
        {manageType === 'PERSONAL' ? '교육생 검색' : '팀 검색'}
      </h1>
      <SearchBar setSearchValue={setSearchValue} />

      {dummyData}

      <div className='flex flex-col gap-y-spacing-8'>
        {nameList.map((userName, index) => (
          <div key={index} className='flex items-center gap-x-spacing-4'>
            <button onClick={() => handleOnclickX(index)}>
              <XIcon className='w-4' />
            </button>
            <p className='body-md-medium text-color-text-primary'>{userName}</p>
          </div>
        ))}
      </div>

      <button
        onClick={handleOnClickSubmit}
        className='w-full h-fit bg-color-bg-interactive-primary py-spacing-12 rounded-radius-8 body-lg-medium text-color-text-interactive-inverse'
      >
        추가하기
      </button>
    </div>
  )
}
