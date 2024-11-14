import { useMemo } from 'react'

type SelectedSortConditionElementsProps = {
  selectedSortCondition: string
  isOpen: boolean
}

export const SelectedSortConditionElements = ({
  selectedSortCondition,
  isOpen,
}: SelectedSortConditionElementsProps) =>
  useMemo(() => {
    switch (selectedSortCondition) {
      case 'by deadline':
        return {
          label: '마감 임박 순',
          bgClass: `w-fit px-spacing-8 py-spacing-4 rounded-radius-8 ${isOpen ? 'bg-color-bg-interactive-secondary-press' : 'bg-color-bg-interactive-secondary hover:bg-color-bg-interactive-secondary-hover'}`,
          labelClass: 'text-color-text-primary body-sm-medium',
          contents: [
            {
              label: '등록 순',
              type: 'by registration',
              isDefaultHover: true,
              classname: 'bg-color-black',
            },
            {
              label: '미완료 일정 순',
              type: 'by unfinished',
              isDefaultHover: false,
              classname: 'bg-color-blue',
            },
          ],
        }

      case 'by registration':
        return {
          label: '등록 순',
          bgClass: `w-fit px-spacing-8 py-spacing-4 rounded-radius-8 ${isOpen ? 'bg-color-bg-interactive-secondary-press' : 'bg-color-bg-interactive-secondary hover:bg-color-bg-interactive-secondary-hover'}`,
          labelClass: 'text-color-text-primary body-sm-medium',
          contents: [
            {
              label: '마감 임박 순',
              type: 'by deadline',
              isDefaultHover: true,
              classname: 'bg-color-black',
            },
            {
              label: '미완료 일정 순',
              type: 'by unfinished',
              isDefaultHover: false,
              classname: 'bg-color-blue',
            },
          ],
        }

      case 'by unfinished':
        return {
          label: '미완료 일정 순',
          bgClass: `w-fit px-spacing-8 py-spacing-4 rounded-radius-8 ${isOpen ? 'bg-color-bg-interactive-secondary-press' : 'bg-color-bg-interactive-secondary hover:bg-color-bg-interactive-secondary-hover'}`,
          labelClass: 'text-color-text-primary body-sm-medium',
          contents: [
            {
              label: '마감 임박 순',
              type: 'by deadline',
              isDefaultHover: true,
              classname: 'bg-color-blue',
            },
            {
              label: '등록 순',
              type: 'by registration',
              isDefaultHover: false,
              classname: 'bg-color-black',
            },
          ],
        }
    }
  }, [selectedSortCondition, isOpen])
