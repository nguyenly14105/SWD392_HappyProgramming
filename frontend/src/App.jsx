import { BrowserRouter, Routes, Route, useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { AuthProvider, useAuth } from './contexts/AuthContext'
import HomePage from './pages/guest/HomePage'
import MentorManagement from './pages/admin/MentorManagement'
import SearchMentorPage from './pages/guest/SearchMentorPage'
import MentorProfilePage from './pages/guest/MentorProfilePage'
import AdminDashboardPage from './pages/admin/DashboardPage'
import MentorDashboardPage from './pages/mentor/DashboardPage'
import MentorCVPage from './pages/mentor/CVPage'
import InvitedRequestsPage from './pages/mentor/InvitedRequestsPage'
import MenteeDashboardPage from './pages/mentee/DashboardPage'
import MenteeProfilePage from './pages/mentee/ProfilePage'
import RequestListPage from './pages/mentee/RequestListPage'
import AuthModal from './components/shared/AuthModal'
import AiChatbot from './components/shared/AiChatbot'
import ProtectedRoute from './components/shared/ProtectedRoute'

function AppContent() {
  const { user, login, logout } = useAuth()
  const navigate = useNavigate()
  const [authModal, setAuthModal] = useState({
    show: false,
    tab: 'login',
    notice: '',
    redirectAfterLogin: null
  })

  function openAuth(tab = 'login', notice = '', redirect = null) {
    setAuthModal({
      show: true,
      tab,
      notice,
      redirectAfterLogin: redirect
    })
  }

  function handleLoginSuccess(u) {
    login(u)
    if (authModal.redirectAfterLogin) {
      navigate(authModal.redirectAfterLogin)
    } else {
      // Default redirects based on role if needed
      if (u.role === 'ROLE_ADMIN') navigate('/admin')
      else if (u.role === 'ROLE_MENTOR') navigate('/mentor')
      else navigate('/mentee')
    }
  }

  return (
    <>
      <Routes>
        {/* Guest Routes */}
        <Route path="/" element={<HomePage currentUser={user} onOpenAuth={openAuth} onLogout={logout} />} />
        <Route path="/mentors/search" element={<SearchMentorPage />} />
        <Route path="/mentors/:id" element={<MentorProfilePage />} />

        {/* Admin Routes */}
        <Route element={<ProtectedRoute requiredRole="ROLE_ADMIN" />}>
          <Route path="/admin" element={<AdminDashboardPage />} />
          <Route path="/admin/mentors" element={<MentorManagement currentUser={user} onLogout={logout} onRequireLogin={() => openAuth('login', 'Vui lòng đăng nhập lại tài khoản Admin')} />} />
        </Route>

        {/* Mentor Routes */}
        <Route element={<ProtectedRoute requiredRole="ROLE_MENTOR" />}>
          <Route path="/mentor" element={<MentorDashboardPage />} />
          <Route path="/mentor/cv" element={<MentorCVPage />} />
          <Route path="/mentor/requests" element={<InvitedRequestsPage />} />
        </Route>

        {/* Mentee (User) Routes */}
        <Route element={<ProtectedRoute requiredRole="ROLE_USER" />}>
          <Route path="/mentee" element={<MenteeDashboardPage />} />
          <Route path="/mentee/profile" element={<MenteeProfilePage />} />
          <Route path="/mentee/requests" element={<RequestListPage />} />
        </Route>
      </Routes>

      <AuthModal
        show={authModal.show}
        initialTab={authModal.tab}
        customNotice={authModal.notice}
        onHide={() => setAuthModal(prev => ({ ...prev, show: false }))}
        onSuccess={handleLoginSuccess}
      />
      <AiChatbot currentUser={user} />
    </>
  )
}

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <AppContent />
      </AuthProvider>
    </BrowserRouter>
  )
}
