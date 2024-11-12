import { CardSourceTypeElements } from '../model/CardSourceTypeElements'
import { CardEndDateElements } from '../model/CardEndDateElements'

type CardProps = {
  title: string
  endDateTime: string
  scheduleSourceType: string
  createUser: {
    userId: string
    name: string
    profileImgUrl: string
  }
}

export const Card = ({ title, endDateTime, scheduleSourceType, createUser }: CardProps) => {
  const cardSourceTag = CardSourceTypeElements({ scheduleSourceType })
  const cardEndDate = CardEndDateElements({ endDateTime })

  return (
    <div className='flex flex-col w-full effect-card-shadow p-spacing-16 h-fit gap-y-spacing-12 bg-color-bg-primary rounded-radius-6'>
      <h1 className='w-full break-words body-md-medium text-color-text-primary line-clamp-2 text-ellipsis'>
        {title}
      </h1>

      <div className={cardSourceTag?.classname}>{cardSourceTag?.description}</div>

      <div className='flex items-center justify-between w-full gap-x-spacing-8'>
        <p className={`body-xs-medium min-w-max ${cardEndDate.color}`}>{cardEndDate.endDate}</p>

        <div className='flex items-center justify-end w-4/5 gap-x-spacing-8'>
          <p className='truncate body-sm-regular text-color-text-disabled'>{createUser.name}</p>
          <img
            src={createUser.profileImgUrl}
            alt='create user image'
            className='w-6 h-6 rounded-radius-circle'
          />
        </div>
      </div>
    </div>
  )
}
