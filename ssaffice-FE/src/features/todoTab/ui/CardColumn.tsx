import { ReactNode } from 'react'

type CardColumnProps = {
  children: ReactNode
  label: string
  columnLength: number
}

export const CardColumn = ({ children, label, columnLength }: CardColumnProps) => {
  return (
    <section className='flex flex-col w-full h-full'>
      <div className='sticky top-0 flex items-center gap-x-spacing-4 bg-color-bg-tertiary px-spacing-16 pt-spacing-12 pb-spacing-8 rounded-t-radius-6'>
        <h2 className='body-sm-semibold text-color-text-disabled'>{label}</h2>
        <p className='body-sm-regular text-color-text-disabled'>{columnLength}</p>
      </div>
      <div className='flex flex-col gap-y-spacing-8 grow px-spacing-8 pb-spacing-40 bg-color-bg-tertiary rounded-b-radius-6'>
        {children}
      </div>
    </section>
  )
}
