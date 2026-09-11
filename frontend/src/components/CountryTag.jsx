import { COUNTRY_LABEL } from '../config/documentConfig'

export default function CountryTag({ country }) {
  const info = COUNTRY_LABEL[country]
  if (!info) return null
  return (
    <span className="country-tag">
      <span className="country-dot" style={{ background: info.color }}>
        {info.code}
      </span>
      {info.label}
    </span>
  )
}
