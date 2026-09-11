import { COUNTRY_LABEL, DOCUMENT_TYPE_LABEL } from '../config/documentConfig'

export default function QueueTable({ queue, onRemove, onProcess, onClear, processing }) {
  return (
    <div className="card">
      <div className="card-header">
        <h2>Lote pendiente ({queue.length})</h2>
        <div className="form-actions">
          <button type="button" className="ghost" onClick={onClear} disabled={!queue.length}>
            Limpiar
          </button>
          <button type="button" className="primary" onClick={onProcess} disabled={!queue.length || processing}>
            {processing ? 'Procesando…' : 'Procesar lote'}
          </button>
        </div>
      </div>

      {queue.length === 0 ? (
        <p className="empty-hint">Agrega documentos con el formulario para armar un lote de procesamiento.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Archivo</th>
              <th>País</th>
              <th>Tipo</th>
              <th>Formato</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {queue.map((doc, idx) => (
              <tr key={idx}>
                <td>{doc.fileName}</td>
                <td>
                  {COUNTRY_LABEL[doc.country]?.flag} {COUNTRY_LABEL[doc.country]?.label}
                </td>
                <td>{DOCUMENT_TYPE_LABEL[doc.documentType]}</td>
                <td>.{doc.format.toLowerCase()}</td>
                <td>
                  <button type="button" className="link-danger" onClick={() => onRemove(idx)}>
                    Quitar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  )
}
