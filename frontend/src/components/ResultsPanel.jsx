import { DOCUMENT_TYPE_LABEL } from '../config/documentConfig'
import CountryTag from './CountryTag'

const STATUS_META = {
  SUCCESS: { label: 'Éxito', className: 'badge success' },
  VALIDATION_ERROR: { label: 'Error de validación', className: 'badge warn' },
  PROCESSING_ERROR: { label: 'Error de procesamiento', className: 'badge error' },
}

export default function ResultsPanel({ results, error }) {
  if (error) {
    return (
      <div className="card">
        <h2>Resultados</h2>
        <p className="error-banner">{error}</p>
      </div>
    )
  }

  if (!results) return null

  return (
    <div className="card">
      <h2>Resultados del procesamiento</h2>
      <table>
        <thead>
          <tr>
            <th>Archivo</th>
            <th>País</th>
            <th>Tipo</th>
            <th>Estado</th>
            <th>Mensaje</th>
          </tr>
        </thead>
        <tbody>
          {results.map((r) => {
            const meta = STATUS_META[r.status] || { label: r.status, className: 'badge' }
            return (
              <tr key={r.documentId}>
                <td>{r.fileName}</td>
                <td>
                  <CountryTag country={r.country} />
                </td>
                <td>{DOCUMENT_TYPE_LABEL[r.documentType]}</td>
                <td>
                  <span className={meta.className}>{meta.label}</span>
                </td>
                <td className="message-cell">{r.message}</td>
              </tr>
            )
          })}
        </tbody>
      </table>
    </div>
  )
}
