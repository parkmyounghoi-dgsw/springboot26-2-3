import {type FormEvent, useState } from 'react'
import './App.css'

type Member = { id: number; name: string }
type HttpMethod = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE'
const DEFAULT_API_URL = 'http://localhost:8080/api/members'

function App() {
  const [apiUrl, setApiUrl] = useState(DEFAULT_API_URL)
  const [members, setMembers] = useState<Member[]>([])
  const [memberId, setMemberId] = useState('')
  const [name, setName] = useState('')
  const [status, setStatus] = useState('API 요청을 실행해 보세요.')
  const [lastMethod, setLastMethod] = useState<HttpMethod | null>(null)
  const [loading, setLoading] = useState(false)

  const request = async <T,>(method: HttpMethod, path = '', body?: object) => {
    const url = `${apiUrl.replace(/\/$/, '')}${path}`
    setLoading(true); setLastMethod(method); setStatus(`${method} 요청 중: ${url}`)
    try {
      const response = await fetch(url, { method, headers: body ? { 'Content-Type': 'application/json' } : undefined, body: body ? JSON.stringify(body) : undefined })
      const text = await response.text()
      const data = text ? (JSON.parse(text) as T) : null
      if (!response.ok) throw new Error(typeof data === 'object' && data && 'message' in data ? String(data.message) : text || `HTTP ${response.status}`)
      setStatus(`${method} 성공 · HTTP ${response.status}`)
      return data
    } catch (error) {
      setStatus(`${method} 실패 · ${error instanceof Error ? error.message : '알 수 없는 오류'}`)
      return undefined
    } finally { setLoading(false) }
  }

  const getAllMembers = async () => { const data = await request<Member[]>('GET'); if (Array.isArray(data)) setMembers(data) }
  const getMember = async () => {
    if (!memberId) return setStatus('조회할 회원 ID를 입력하세요.')
    const data = await request<Member>('GET', `/${memberId}`)
    if (data) { setMembers([data]); setName(data.name) }
  }
  const createMember = async (event: FormEvent) => {
    event.preventDefault()
    if (!name.trim()) return setStatus('회원 이름을 입력하세요.')
    const data = await request<Member>('POST', '', { name: name.trim() })
    if (data) { setMembers((current) => [...current, data]); setMemberId(String(data.id)); setName('') }
  }
  const updateMember = async (method: 'PUT' | 'PATCH') => {
    if (!memberId || !name.trim()) return setStatus('회원 ID와 이름을 모두 입력하세요.')
    const data = await request<Member>(method, `/${memberId}`, { name: name.trim() })
    if (data) setMembers((current) => current.map((member) => member.id === data.id ? data : member))
  }
  const deleteMember = async () => {
    if (!memberId) return setStatus('삭제할 회원 ID를 입력하세요.')
    const data = await request<Member>('DELETE', `/${memberId}`)
    if (data !== undefined) setMembers((current) => current.filter((member) => member.id !== (data?.id ?? Number(memberId))))
  }

  return (
    <main className="member-app">
      <header>
        <p className="eyebrow">MEMBER REST CLIENT</p>
        <h1>회원 관리 API</h1>
        <p className="description">Member 테이블의 CRUD 요청을 이 화면에서 바로 실행합니다.</p>
      </header>
      <section className="api-settings">
        <label htmlFor="api-url">API 기본 주소</label>
        <input id="api-url" value={apiUrl}
               onChange={(event) =>
                   setApiUrl(event.target.value)}
        />
        <small>기본값: http://localhost:8080/api/members</small>
      </section>
      <section className="member-form-section">
        <div className="section-heading">
          <div>
            <p className="eyebrow">WRITE</p>
            <h2>회원 정보</h2>
          </div>
          <span className="method-badge">{lastMethod ?? 'READY'}</span>
        </div>
        <form onSubmit={createMember}>
          <label htmlFor="member-id">회원 ID</label>
          <input id="member-id" inputMode="numeric" placeholder="예: 1" value={memberId} onChange={(event) => setMemberId(event.target.value)} />
          <label htmlFor="member-name">이름</label>
          <input id="member-name" placeholder="회원 이름" value={name} onChange={(event) => setName(event.target.value)} />
          <div className="button-grid">
            <button type="submit" className="post" disabled={loading}>POST 생성</button>
            <button type="button" className="put" onClick={() => updateMember('PUT')} disabled={loading}>PUT 전체 수정</button>
            <button type="button" className="patch" onClick={() => updateMember('PATCH')} disabled={loading}>PATCH 부분 수정</button>
            <button type="button" className="delete" onClick={deleteMember} disabled={loading}>DELETE 삭제</button></div>
        </form>
      </section>
      <section className="read-section">
        <div className="section-heading">
          <div>
            <p className="eyebrow">READ</p>
            <h2>회원 조회</h2></div>
          <div className="read-actions">
            <button type="button" onClick={getAllMembers} disabled={loading}>GET 전체</button>
            <button type="button" onClick={getMember} disabled={loading}>GET ID 조회</button>
          </div>
        </div>
        <p className={`status ${status.includes('실패') ? 'error' : ''}`} role="status">
          {loading ? '요청을 처리하고 있습니다…' : status}
        </p>
        <div className="member-list">
          {members.length ? members.map((member) =>
              <button key={member.id} type="button" className="member-row" onClick={() => { setMemberId(String(member.id)); setName(member.name) }}>
                <span>#{member.id}</span>
                <strong>{member.name}</strong>
                <em>선택</em></button>) :
              <p className="empty">조회된 회원이 없습니다.</p>
          }
        </div>
      </section>
    </main>
  )
}

export default App
