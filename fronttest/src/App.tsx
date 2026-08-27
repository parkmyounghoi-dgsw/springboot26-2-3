import { BrowserRouter, NavLink, Route, Routes } from 'react-router-dom'
import MembersPage from './pages/MembersPage'
import LambdaPage from './pages/LambdaPage'
import './App.css'

export default function App() {
  return (
    <BrowserRouter>
      <nav className="app-nav">
        <NavLink to="/" end className={({ isActive }) => isActive ? 'active' : ''}>
          Members
        </NavLink>
        <NavLink to="/lambda" className={({ isActive }) => isActive ? 'active' : ''}>
          Lambda
        </NavLink>
      </nav>
      <Routes>
        <Route path="/" element={<MembersPage />} />
        <Route path="/lambda" element={<LambdaPage />} />
      </Routes>
    </BrowserRouter>
  )
}
