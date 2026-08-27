import { useState } from 'react'

const DEFAULT_API_URL = 'http://localhost:8080'

export default function LambdaPage() {
  const [apiUrl, setApiUrl] = useState(DEFAULT_API_URL)

  // ── students ───────────────────────────────────────
  const [studentsJson, setStudentsJson] = useState('')
  const [studentsStatus, setStudentsStatus] = useState('')

  // ── stream ─────────────────────────────────────────
  const [streamFrom, setStreamFrom] = useState('')
  const [streamTo, setStreamTo] = useState('')
  const [streamJson, setStreamJson] = useState('')
  const [streamStatus, setStreamStatus] = useState('')

  const base = apiUrl.replace(/\/$/, '')

  const fetchStudents = async () => {
    setStudentsStatus('요청 중…')
    setStudentsJson('')
    try {
      const res = await fetch(`${base}/api/lambda/students`)
      const text = await res.text()
      // JSON이면 pretty-print, 아니면 원문 그대로
      try { setStudentsJson(JSON.stringify(JSON.parse(text), null, 2)) }
      catch { setStudentsJson(text) }
      setStudentsStatus(`성공 · HTTP ${res.status}`)
    } catch (e) {
      setStudentsStatus(`실패 · ${e instanceof Error ? e.message : String(e)}`)
    }
  }

  const fetchStream = async () => {
    setStreamStatus('요청 중…')
    setStreamJson('')
    try {
      const res = await fetch(`${base}/api/lambda/stream?from=${streamFrom}&to=${streamTo}`)
      const text = await res.text()
      try { setStreamJson(JSON.stringify(JSON.parse(text), null, 2)) }
      catch { setStreamJson(text) }
      setStreamStatus(`성공 · HTTP ${res.status}`)
    } catch (e) {
      setStreamStatus(`실패 · ${e instanceof Error ? e.message : String(e)}`)
    }
  }

  return (
    <main className="member-app">
      <header>
        <p className="eyebrow">LAMBDA REST CLIENT</p>
        <h1>Lambda <span style={{ fontWeight: 400, fontSize: '1rem', color: '#888' }}>IntStream 람다 API</span></h1>
        <p className="description">람다와 스트림을 활용한 학생 목록 정렬 및 짝수 필터링 API입니다.</p>
      </header>

      <section className="api-settings">
        <label htmlFor="lambda-url">API 기본 주소</label>
        <input id="lambda-url" value={apiUrl} onChange={e => setApiUrl(e.target.value)} />
        <small>기본값: http://localhost:8080</small>
      </section>

      {/* ── /api/lambda/students ─────────────────────── */}
      <section className="member-form-section">
        <div className="section-heading">
          <div>
            <p className="eyebrow">GET</p>
            <h2>/api/lambda/students <span className="eyebrow" style={{ marginLeft: 6 }}>학생 목록 정렬</span></h2>
          </div>
          <button type="button" className="post" onClick={fetchStudents}>GET 요청</button>
        </div>

        {studentsStatus && (
          <p className={`status ${studentsStatus.includes('실패') ? 'error' : ''}`}>{studentsStatus}</p>
        )}

        {studentsJson && (
          <pre className="json-output">{studentsJson}</pre>
        )}
      </section>

      {/* ── /api/lambda/stream ──────────────────────── */}
      <section className="read-section">
        <div className="section-heading">
          <div>
            <p className="eyebrow">GET</p>
            <h2>/api/lambda/stream <span className="eyebrow" style={{ marginLeft: 6 }}>짝수 필터링</span></h2>
          </div>
        </div>
        <p className="description" style={{ marginBottom: '1rem' }}>from ~ to 범위에서 짝수만 반환합니다.</p>

        <div style={{ display: 'flex', gap: '0.75rem', alignItems: 'flex-end', flexWrap: 'wrap', marginBottom: '1rem' }}>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '0.25rem' }}>
            <label htmlFor="s-from">from</label>
            <input id="s-from" inputMode="numeric" placeholder="예: 1" value={streamFrom}
              onChange={e => setStreamFrom(e.target.value)} style={{ width: '6rem' }} />
          </div>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '0.25rem' }}>
            <label htmlFor="s-to">to</label>
            <input id="s-to" inputMode="numeric" placeholder="예: 20" value={streamTo}
              onChange={e => setStreamTo(e.target.value)} style={{ width: '6rem' }} />
          </div>
          <button type="button" className="post" onClick={fetchStream}>GET 요청</button>
        </div>

        {streamStatus && (
          <p className={`status ${streamStatus.includes('실패') ? 'error' : ''}`}>{streamStatus}</p>
        )}

        {streamJson && (
          <pre className="json-output">{streamJson}</pre>
        )}
      </section>
    </main>
  )
}
