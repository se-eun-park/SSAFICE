import { useMemo } from 'react'

type CardSourceTypeElementProps = {
  scheduleSourceType: string
}

export const CardSourceTypeElements = ({ scheduleSourceType }: CardSourceTypeElementProps) =>
  useMemo(() => {
    switch (scheduleSourceType) {
      case 'all':
        return {
          description: '전체 공지에서 등록',
          classname:
            'bg-color-bg-info w-fit h-fit px-spacing-4 py-spacing-2 rounded-radius-4 text-color-text-interactive-inverse body-xs-semibold',
        }
      case 'team':
        return {
          description: '팀 공지에서 등록',
          classname:
            'bg-color-bg-warning w-fit h-fit px-spacing-4 py-spacing-2 rounded-radius-4 text-color-text-interactive-inverse body-xs-semibold',
        }
      case 'my':
        return {
          description: '내가 등록',
          classname:
            'bg-color-bg-success w-fit h-fit px-spacing-4 py-spacing-2 rounded-radius-4 text-color-text-interactive-inverse body-xs-semibold',
        }
    }
  }, [scheduleSourceType])
