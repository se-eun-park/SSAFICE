export const ManagerTodoFirstElements = (modaltype: 'CREATE' | 'VIEW' | 'EDIT') => {
  // 각 api로 불러온 초기값 명시 (지금은 그냥 더미데이터)
  switch (modaltype) {
    case 'CREATE':
      return {
        title: '',
        description: '',
        selectedState: 'todo',
        user: { name: '', profileImgUrl: '' },

        createUser: {
          name: '용상윤[서울_5, 6반]실습코치',
          profileImgUrl: 'https://i.pinimg.com/236x/a5/73/59/a5735920142505068fd1e5ebd0ce86f1.jpg',
        },
        endDate: '',
        remindRequests: [],
        isEssentialYn: 'Y',
      }
    case 'VIEW':
      return {
        title: '조회하거라',
        description: '조회하거라',
        selectedState: 'progress',
        user: {
          name: '곽성재(교육생)',
          profileImgUrl: 'https://i.pinimg.com/564x/4d/b2/42/4db2422c74f12f70391ec386bf95e4db.jpg',
        },
        createUser: {
          name: '용상윤[서울_5, 6반]실습코치',
          profileImgUrl: 'https://i.pinimg.com/236x/a5/73/59/a5735920142505068fd1e5ebd0ce86f1.jpg',
        },
        endDate: '2024-11-19',
        remindRequests: [{ remindtype: 'DAILY', remindDateTime: '1999-09-22T11:00:00' }],
        isEssentialYn: 'N',
      }
    case 'EDIT':
      return {
        title: '수정하거라',
        description: '수정하거라',
        selectedState: 'progress',
        user: {
          name: '곽성재(교육생)',
          profileImgUrl: 'https://i.pinimg.com/564x/4d/b2/42/4db2422c74f12f70391ec386bf95e4db.jpg',
        },
        createUser: {
          name: '용상윤[서울_5, 6반]실습코치',
          profileImgUrl: 'https://i.pinimg.com/236x/a5/73/59/a5735920142505068fd1e5ebd0ce86f1.jpg',
        },
        endDate: '2024-11-19',
        remindRequests: [{ remindtype: 'DAILY', remindDateTime: '1999-09-22T11:00:00' }],
        isEssentialYn: 'N',
      }
  }
}
