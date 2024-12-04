import { useMemo } from 'react'

type SelectedStateElementsProps = {
  selectedState: string
  isOpen: boolean

  actionType?: 'narrow plain' | 'with label' | 'with all'
  // 차례대로 좁은 상태 편집 드롭다운(개별 할일 상태), 할일 간편 등록, 교육생 할일 필터('전체' 옵션 포함)
}

export const SelectedStateElements = ({
  selectedState,
  isOpen,
  actionType,
}: SelectedStateElementsProps) =>
  useMemo(() => {
    const withAllSelectOption = {
      // actionType === 'with all'일 때만 추가되는 옵션
      label: '전체',
      type: 'ALL',
      isDefaultHover: false,
      classname:
        'body-xs-medium text-color-text-primary bg-color-bg-interactive-secondary px-spacing-4 py-spacing-2 rounded-radius-4',
    }

    const defaultOption = {
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

    const todoOption = {
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

    const inProgressOption = {
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

    const doneOption = {
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

    const allOption = {
      label: '전체',
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

    switch (selectedState) {
      case 'default':
        return defaultOption
      case 'TODO':
        if (actionType === 'with all') {
          todoOption.contents.push(withAllSelectOption)
          return todoOption
        }
        return todoOption
      case 'IN_PROGRESS':
        if (actionType === 'with all') {
          inProgressOption.contents.push(withAllSelectOption)
          return inProgressOption
        }
        return inProgressOption
      case 'DONE':
        if (actionType === 'with all') {
          doneOption.contents.push(withAllSelectOption)
          return doneOption
        }
        return doneOption
      case 'ALL':
        return allOption
    }
  }, [selectedState, isOpen])
