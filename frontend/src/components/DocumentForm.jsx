import { useMemo, useState } from 'react'
import { ALLOWED_FORMATS, COUNTRIES, COUNTRY_FIELDS, DOCUMENT_TYPES } from '../config/documentConfig'

const emptyFields = (country, docType) => {
  const defs = COUNTRY_FIELDS[country]?.[docType] || []
  return Object.fromEntries(defs.map((f) => [f.key, '']))
}

export default function DocumentForm({ onAdd }) {
  const [country, setCountry] = useState(COUNTRIES[0].value)
  const [documentType, setDocumentType] = useState(DOCUMENT_TYPES[0].value)
  const [format, setFormat] = useState(ALLOWED_FORMATS[DOCUMENT_TYPES[0].value][0])
  const [fileName, setFileName] = useState('')
  const [content, setContent] = useState('')
  const [fields, setFields] = useState(emptyFields(COUNTRIES[0].value, DOCUMENT_TYPES[0].value))

  const fieldDefs = useMemo(() => COUNTRY_FIELDS[country]?.[documentType] || [], [country, documentType])
  const availableFormats = ALLOWED_FORMATS[documentType]

  function handleDocumentType(nextType) {
    setDocumentType(nextType)
    setFormat(ALLOWED_FORMATS[nextType][0])
    setFields(emptyFields(country, nextType))
  }

  function handleCountry(nextCountry) {
    setCountry(nextCountry)
    setFields(emptyFields(nextCountry, documentType))
  }

  function handleSubmit(e) {
    e.preventDefault()
    if (!fileName.trim()) return
    onAdd({
      fileName: fileName.trim(),
      country,
      documentType,
      format,
      content,
      fields,
    })
    setFileName('')
    setContent('')
    setFields(emptyFields(country, documentType))
  }

  function fillExample() {
    const defs = COUNTRY_FIELDS[country]?.[documentType] || []
    setFields(Object.fromEntries(defs.map((f) => [f.key, f.placeholder])))
    if (!fileName.trim()) {
      setFileName(`${documentType.toLowerCase()}-ejemplo.${format.toLowerCase()}`)
    }
  }

  return (
    <form className="card" onSubmit={handleSubmit}>
      <h2>Nuevo documento</h2>

      <div className="field-row">
        <label>
          País
          <select value={country} onChange={(e) => handleCountry(e.target.value)}>
            {COUNTRIES.map((c) => (
              <option key={c.value} value={c.value}>
                {c.flag} {c.label}
              </option>
            ))}
          </select>
        </label>

        <label>
          Tipo de documento
          <select value={documentType} onChange={(e) => handleDocumentType(e.target.value)}>
            {DOCUMENT_TYPES.map((t) => (
              <option key={t.value} value={t.value}>
                {t.label}
              </option>
            ))}
          </select>
        </label>
      </div>

      <div className="field-row">
        <label>
          Formato
          <select value={format} onChange={(e) => setFormat(e.target.value)}>
            {availableFormats.map((f) => (
              <option key={f} value={f}>
                .{f.toLowerCase()}
              </option>
            ))}
          </select>
        </label>

        <label>
          Nombre de archivo
          <input
            type="text"
            placeholder={`documento.${format.toLowerCase()}`}
            value={fileName}
            onChange={(e) => setFileName(e.target.value)}
            required
          />
        </label>
      </div>

      {fieldDefs.length > 0 && (
        <fieldset className="regulatory-fields">
          <legend>Campos regulatorios ({country})</legend>
          {fieldDefs.map((f) => (
            <label key={f.key}>
              {f.label}
              <input
                type="text"
                placeholder={f.placeholder}
                value={fields[f.key] || ''}
                onChange={(e) => setFields((prev) => ({ ...prev, [f.key]: e.target.value }))}
              />
            </label>
          ))}
        </fieldset>
      )}

      <label>
        Contenido / notas (opcional — escribe "ERROR" para simular un fallo de procesamiento)
        <textarea rows={2} value={content} onChange={(e) => setContent(e.target.value)} />
      </label>

      <div className="form-actions">
        <button type="button" className="ghost" onClick={fillExample}>
          Rellenar ejemplo válido
        </button>
        <button type="submit" className="primary">
          Agregar a la cola
        </button>
      </div>
    </form>
  )
}
