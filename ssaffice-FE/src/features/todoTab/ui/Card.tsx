import { useState } from 'react'
import { useDraggable } from '@dnd-kit/core'

import type { GetTodoResponse } from '@/entities/todoTab'
import { CommonModal } from '@/shared/ui'
import { CardSourceTypeElements } from '../model/CardSourceTypeElements'
import { CardEndDateElements } from '../model/CardEndDateElements'

export const Card = ({
  scheduleId,
  title,
  endDateTime,
  scheduleStatusTypeCd,
  scheduleSourceTypeCd,
  createUser,
}: GetTodoResponse) => {
  const cardSourceTag = CardSourceTypeElements({ scheduleSourceTypeCd })
  const cardEndDate = CardEndDateElements({ endDateTime, scheduleStatusTypeCd })

  // state
  const [isModalOpen, setIsModalOpen] = useState(false)
  const close = () => setIsModalOpen(false)

  // event
  const { attributes, listeners, setNodeRef, transform } = useDraggable({
    id: scheduleId,
    data: {
      status: scheduleStatusTypeCd,
    },
  })

  const style = transform
    ? {
        transform: `translate(${transform.x}px, ${transform.y}px)`,
        maskImage:
          'linear-gradient(to bottom right, rgba(255, 255, 255, 1) 0%, rgba(255, 255, 255, 0) 100%)',
        zIndex: 10,
      }
    : undefined

  const handleOnClickCard = () => {
    setIsModalOpen(true)
  }

  return (
    <>
      <button
        ref={setNodeRef}
        {...listeners}
        {...attributes}
        onClick={handleOnClickCard}
        className='flex flex-col w-full effect-card-shadow p-spacing-16 h-fit gap-y-spacing-12 bg-color-bg-primary rounded-radius-6'
        style={style}
      >
        <h1 className='w-full text-left break-words body-md-medium text-color-text-primary line-clamp-2 text-ellipsis'>
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
      </button>
      <CommonModal
        name='TraineeTodo'
        modaltype='VIEW'
        opened={isModalOpen}
        closeRequest={close}
        isBackdropCloseRequest={false}
      />
    </>
  )
}
