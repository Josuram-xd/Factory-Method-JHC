const BASE_URL = '/api/documents'

async function handle(response) {
  const data = await response.json().catch(() => null)
  if (!response.ok) {
    const message = data?.message || `Error HTTP ${response.status}`
    throw new Error(message)
  }
  return data
}

function toMetadata(doc) {
  return {
    fileName: doc.file.name,
    country: doc.country,
    documentType: doc.documentType,
    format: doc.format,
    fields: doc.fields,
  }
}

function jsonPart(value) {
  return new Blob([JSON.stringify(value)], { type: 'application/json' })
}

export async function processBatch(queue) {
  const formData = new FormData()
  queue.forEach((doc) => formData.append('files', doc.file, doc.file.name))
  formData.append('metadataList', jsonPart(queue.map(toMetadata)))

  const response = await fetch(`${BASE_URL}/batch`, {
    method: 'POST',
    body: formData,
  })
  return handle(response)
}
