#!/usr/bin/env bash
# Demo del flujo: solicitud de cambio -> aprobación -> evento -> auditoría + notificación.
BASE="${BASE:-http://localhost:8080}"

if command -v jq >/dev/null 2>&1; then fmt() { jq .; }; else fmt() { cat; echo; }; fi

# llamar METODO RUTA [CUERPO]: imprime el código HTTP y el cuerpo, y deja el cuerpo en $CUERPO
llamar() {
  local salida
  if [ -n "$3" ]; then
    salida=$(printf '%s' "$3" | curl -s -w '\n%{http_code}' -X "$1" "$BASE$2" -H 'Content-Type: application/json; charset=utf-8' --data-binary @-)
  else
    salida=$(curl -s -w '\n%{http_code}' -X "$1" "$BASE$2")
  fi
  CUERPO=$(echo "$salida" | sed '$d')
  echo "HTTP $(echo "$salida" | tail -n 1)"
  echo "$CUERPO" | fmt
}

echo "=== Paso 1: registrar una nota válida en el periodo abierto P2 (espera 201) ==="
llamar POST /api/notas '{"estudianteId":"EST-002","materia":"Física","periodoId":"P2","valor":4.2,"profesor":"prof.ramirez"}'

echo; echo "=== Paso 2: nota fuera de escala, valor 7 (espera 400) ==="
llamar POST /api/notas '{"estudianteId":"EST-002","materia":"Física","periodoId":"P2","valor":7,"profesor":"prof.ramirez"}'

echo; echo "=== Paso 3: nota en el periodo cerrado P1 (espera 400, periodo cerrado) ==="
llamar POST /api/notas '{"estudianteId":"EST-002","materia":"Física","periodoId":"P1","valor":4,"profesor":"prof.ramirez"}'

echo; echo "=== Paso 4: solicitud de cambio sobre la nota N1 del periodo cerrado (espera 201, PENDIENTE) ==="
llamar POST /api/notas/N1/solicitudes '{"valorPropuesto":4.0,"justificacion":"Error de digitación en el acta","profesor":"prof.ramirez"}'
ID=$(echo "$CUERPO" | sed -n 's/.*"id" *: *"\([^"]*\)".*/\1/p' | head -n 1)

echo; echo "=== Paso 5: el administrador aprueba la solicitud $ID (la consola de la app muestra [NOTIFICACION]) ==="
llamar POST "/api/solicitudes/$ID/aprobar" '{"administrador":"admin.lopez"}'
echo "--- Nota N1 después de la aprobación ---"
llamar GET /api/notas/N1

echo; echo "=== Paso 6: auditoría de la nota N1 (valorAnterior 2.8, valorNuevo 4.0) ==="
llamar GET "/api/auditoria?notaId=N1"
