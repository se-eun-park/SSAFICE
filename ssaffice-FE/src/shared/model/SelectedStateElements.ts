import { useMemo } from 'react'

type SelectedStateElementsProps = {
  selectedState: string
  isOpen: boolean

  actionType?: 'narrow plain' | 'with label'
  // 차례대로 좁은 상태 편집 드롭다운(개별 할일 상태), 할일 간편 등록
}

export const SelectedStateElements = ({
  selectedState,
  isOpen,
  actionType,
}: SelectedStateElementsProps) =>
  useMemo(() => {
    switch (selectedState) {
      case 'default':
        return {
          label: '상태',
          bgClass: `w-fit px-spacing-8 py-spacing-4 rounded-radius-8 ${isOpen ? 'bg-color-bg-interactive-secondary-press' : 'bg-color-bg-interactive-secondary hover:bg-color-bg-interactive-secondary-hover'}`,
          labelClass: 'body-sm-medium text-color-text-primary min-w-fit',
          contents: [
            {
              label: '해야 할 일',
              type: 'TODO',
              isDefaultHover: true,
              classname:
                'min-w-fit body-xs-medium text-color-text-primary bg-color-bg-disabled px-spacing-4 py-spacing-2 rounded-radius-4',
            },
            {
              label: '진행 중',
              type: 'IN_PROGRESS',
              isDefaultHover: false,
              classname:
                'min-w-fit body-xs-medium text-color-text-interactive-inverse bg-color-bg-interactive-primary-hover px-spacing-4 py-spacing-2 rounded-radius-4',
            },
            {
              label: '완료',
              type: 'DONE',
              isDefaultHover: false,
              classname:
                'min-w-fit body-xs-medium text-color-text-disabled-soft bg-color-bg-interactive-success-hover px-spacing-4 py-spacing-2 rounded-radius-4',
            },
          ],
        }
      case 'TODO':
        return {
          label: '해야 할 일',
          bgClass: `w-fit  ${actionType === 'narrow plain' ? 'px-spacing-6 py-spacing-2 h-[20px]' : 'px-spacing-8 py-spacing-4'}  rounded-radius-8 ${isOpen ? 'bg-color-bg-interactive-disabled-press' : 'bg-color-bg-interactive-disabled hover:bg-color-bg-interactive-disabled-hover'}`,
          labelClass: `${actionType === 'narrow plain' ? 'body-xs-medium' : 'body-sm-medium'} text-color-text-interactive-inverse min-w-fit`,
          contents: [
            {
              label: '진행 중',
              type: 'IN_PROGRESS',
              isDefaultHover: true,
              classname:
                'body-xs-medium text-color-text-interactive-inverse bg-color-bg-interactive-primary-hover px-spacing-4 py-spacing-2 rounded-radius-4',
            },
            {
              label: '완료',
              type: 'DONE',
              isDefaultHover: false,
              classname:
                'body-xs-medium text-color-text-disabled-soft bg-color-bg-interactive-success-hover px-spacing-4 py-spacing-2 rounded-radius-4',
            },
          ],
        }
      case 'IN_PROGRESS':
        return {
          label: '진행 중',
          bgClass: `w-fit  ${actionType === 'narrow plain' ? 'px-spacing-6 py-spacing-2 h-[20px]' : 'px-spacing-8 py-spacing-4'}  rounded-radius-8 min-w-fit ${isOpen ? 'bg-color-bg-interactive-primary-press' : 'bg-color-bg-interactive-primary hover:bg-color-bg-interactive-primary-hover'}`,
          labelClass: `${actionType === 'narrow plain' ? 'body-xs-medium' : 'body-sm-medium'} text-color-text-interactive-inverse min-w-fit`,
          contents: [
            {
              label: '해야 할 일',
              type: 'TODO',
              isDefaultHover: true,
              classname:
                'body-xs-medium text-color-text-primary bg-color-bg-disabled px-spacing-4 py-spacing-2 rounded-radius-4',
            },
            {
              label: '완료',
              type: 'DONE',
              isDefaultHover: false,
              classname:
                'body-xs-medium text-color-text-disabled-soft bg-color-bg-interactive-success-hover px-spacing-4 py-spacing-2 rounded-radius-4',
            },
          ],
        }
      case 'DONE':
        return {
          label: '완료',
          bgClass: `w-fit  ${actionType === 'narrow plain' ? 'px-spacing-6 py-spacing-2 h-[20px]' : 'px-spacing-8 py-spacing-4'}  rounded-radius-8 ${isOpen ? 'bg-color-bg-interactive-success-press' : 'bg-color-bg-interactive-success hover:bg-color-bg-interactive-success-hover'}`,
          labelClass: `${actionType === 'narrow plain' ? 'body-xs-medium' : 'body-sm-medium'} text-color-text-interactive-inverse min-w-fit`,
          contents: [
            {
              label: '해야 할 일',
              type: 'TODO',
              isDefaultHover: true,
              classname:
                'body-xs-medium text-color-text-primary bg-color-bg-disabled px-spacing-4 py-spacing-2 rounded-radius-4',
            },
            {
              label: '진행 중',
              type: 'IN_PROGRESS',
              isDefaultHover: false,
              classname:
                'body-xs-medium text-color-text-interactive-inverse bg-color-bg-interactive-primary-hover px-spacing-4 py-spacing-2 rounded-radius-4',
            },
          ],
        }
    }
  }, [selectedState, isOpen])
