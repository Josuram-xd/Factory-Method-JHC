import { COUNTRY_LABEL } from '../config/documentConfig'

export default function StatsBar({ batch }) {
  if (!batch) return null

  const successRate = batch.total ? Math.round((batch.successful / batch.total) * 100) : 0

  return (
    <div className="card stats-card">
      <h2>Resumen del lote</h2>
      <div className="stats-grid">
        <div className="stat">
          <span className="stat-value">{batch.total}</span>
          <span className="stat-label">Total</span>
        </div>
        <div className="stat success">
          <span className="stat-value">{batch.successful}</span>
          <span className="stat-label">Exitosos</span>
        </div>
        <div className="stat error">
          <span className="stat-value">{batch.failed}</span>
          <span className="stat-label">Con error</span>
        </div>
        <div className="stat">
          <span className="stat-value">{successRate}%</span>
          <span className="stat-label">Tasa de éxito</span>
        </div>
      </div>

      <div className="breakdown">
        <h3>Por país</h3>
        <div className="bars">
          {Object.entries(batch.byCountry).map(([country, count]) => (
            <div className="bar-row" key={country}>
              <span className="bar-label">
                {COUNTRY_LABEL[country]?.flag} {COUNTRY_LABEL[country]?.label}
              </span>
              <div className="bar-track">
                <div className="bar-fill" style={{ width: `${(count / batch.total) * 100}%` }} />
              </div>
              <span className="bar-count">{count}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
