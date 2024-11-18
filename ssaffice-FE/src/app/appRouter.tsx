import { createBrowserRouter } from 'react-router-dom'
import { NotFoundPage } from '@/pages/notFound'
import { LoginPage } from '@/pages/login'
import { LandingPage } from '@/pages/landing'
import { MainPage } from '@/pages/main'
import { baseLayout } from './layouts/baseLayout'
import { ProPage } from '@/pages/pro'

export const appRouter = () => {
  return createBrowserRouter([
    {
      path: '/',
      element: baseLayout,
      errorElement: <NotFoundPage />,
      children: [
        {
          index: true,
          element: <LandingPage />,
        },
        {
          path: 'login',
          element: <LoginPage />,
        },
        {
          path: 'main',
          element: <MainPage />,
        },
        {
          path: 'pro',
          element: <ProPage />,
        },
      ],
    },
  ])
}
