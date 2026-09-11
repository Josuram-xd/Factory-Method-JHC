const BASE_URL = '/api/documents'

async function handle(response) {
  const data = await response.json().catch(() => null)
  if (!response.ok) {
    const message = data?.message || `Error HTTP ${response.status}`
    throw new Error(message)
  }
  return data
}

export async function processBatch(documents) {
  const response = await fetch(`${BASE_URL}/batch`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(documents),
  })
  return handle(response)
}
