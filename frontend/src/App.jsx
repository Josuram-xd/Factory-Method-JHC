import { useState } from 'react'
import DocumentForm from './components/DocumentForm'
import QueueTable from './components/QueueTable'
import ResultsPanel from './components/ResultsPanel'
import StatsBar from './components/StatsBar'
import { processBatch } from './api'
import './App.css'

export default function App() {
  const [queue, setQueue] = useState([])
  const [batch, setBatch] = useState(null)
  const [error, setError] = useState(null)
  const [processing, setProcessing] = useState(false)

  function addToQueue(doc) {
    setQueue((prev) => [...prev, doc])
  }

  function removeFromQueue(index) {
    setQueue((prev) => prev.filter((_, i) => i !== index))
  }

  async function handleProcess() {
    setProcessing(true)
    setError(null)
    try {
      const result = await processBatch(queue)
      setBatch(result)
    } catch (err) {
      setError(err.message)
      setBatch(null)
    } finally {
      setProcessing(false)
    }
  }

  return (
    <div className="app-shell">
      <header className="app-header">
        <div>
          <h1>GlobalDocs Solutions</h1>
          <p>Procesamiento de documentos empresariales multinacional · Colombia · México · Argentina · Chile</p>
        </div>
        <div className="header-badge">Patrón Factory Method</div>
      </header>

      <main className="app-grid">
        <section className="column">
          <DocumentForm onAdd={addToQueue} />
        </section>

        <section className="column">
          <QueueTable
            queue={queue}
            onRemove={removeFromQueue}
            onProcess={handleProcess}
            onClear={() => setQueue([])}
            processing={processing}
          />
          <StatsBar batch={batch} />
          <ResultsPanel results={batch?.results} error={error} />
        </section>
      </main>
    </div>
  )
}
