import { announcementDataEmojiSelectExpression } from '@/entities/announcementTab'

export const useCustomEmojiRemover = (message: string): string => {
  return message.replace(announcementDataEmojiSelectExpression, '')
}
