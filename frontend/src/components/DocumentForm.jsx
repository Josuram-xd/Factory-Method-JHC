import { useMemo, useRef, useState } from 'react'
import { ALLOWED_FORMATS, COUNTRIES, COUNTRY_FIELDS, DOCUMENT_TYPES } from '../config/documentConfig'

const emptyFields = (country, docType) => {
  const defs = COUNTRY_FIELDS[country]?.[docType] || []
  return Object.fromEntries(defs.map((f) => [f.key, '']))
}

function extensionOf(fileName) {
  const dot = fileName.lastIndexOf('.')
  return dot >= 0 ? fileName.slice(dot + 1).toUpperCase() : null
}

export default function DocumentForm({ onAdd }) {
  const [country, setCountry] = useState(COUNTRIES[0].value)
  const [documentType, setDocumentType] = useState(DOCUMENT_TYPES[0].value)
  const [format, setFormat] = useState(ALLOWED_FORMATS[DOCUMENT_TYPES[0].value][0])
  const [file, setFile] = useState(null)
  const [fields, setFields] = useState(emptyFields(COUNTRIES[0].value, DOCUMENT_TYPES[0].value))
  const fileInputRef = useRef(null)

  const fieldDefs = useMemo(() => COUNTRY_FIELDS[country]?.[documentType] || [], [country, documentType])
  const availableFormats = ALLOWED_FORMATS[documentType]
  const extensionMismatch = file && extensionOf(file.name) !== format

  function handleDocumentType(nextType) {
    setDocumentType(nextType)
    setFormat(ALLOWED_FORMATS[nextType][0])
    setFields(emptyFields(country, nextType))
  }

  function handleCountry(nextCountry) {
    setCountry(nextCountry)
    setFields(emptyFields(nextCountry, documentType))
  }

  function handleFileChange(e) {
    const selected = e.target.files?.[0] || null
    setFile(selected)
    if (selected) {
      const ext = extensionOf(selected.name)
      if (ext && availableFormats.includes(ext)) {
        setFormat(ext)
      }
    }
  }

  function handleSubmit(e) {
    e.preventDefault()
    if (!file) return
    onAdd({ file, country, documentType, format, fields })
    setFile(null)
    if (fileInputRef.current) fileInputRef.current.value = ''
    setFields(emptyFields(country, documentType))
  }

  function fillExampleFields() {
    const defs = COUNTRY_FIELDS[country]?.[documentType] || []
    setFields(Object.fromEntries(defs.map((f) => [f.key, f.placeholder])))
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
          Formato declarado
          <select value={format} onChange={(e) => setFormat(e.target.value)}>
            {availableFormats.map((f) => (
              <option key={f} value={f}>
                .{f.toLowerCase()}
              </option>
            ))}
          </select>
        </label>

        <label>
          Archivo ({availableFormats.map((f) => `.${f.toLowerCase()}`).join(', ')})
          <input
            ref={fileInputRef}
            type="file"
            accept={availableFormats.map((f) => `.${f.toLowerCase()}`).join(',')}
            onChange={handleFileChange}
            required
          />
        </label>
      </div>

      {extensionMismatch && (
        <p className="warning-hint">
          La extensión del archivo ({extensionOf(file.name)}) no coincide con el formato declarado ({format}) — el
          servidor rechazará el documento por esta inconsistencia.
        </p>
      )}

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

      <div className="form-actions">
        <button type="button" className="ghost" onClick={fillExampleFields}>
          Rellenar campos de ejemplo
        </button>
        <button type="submit" className="primary" disabled={!file}>
          Agregar a la cola
        </button>
      </div>
    </form>
  )
}
