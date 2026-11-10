# Demo del flujo: solicitud de cambio -> aprobación -> evento -> auditoría + notificación.
$Base = if ($env:BASE) { $env:BASE } else { "http://localhost:8080" }

function Llamar($Metodo, $Ruta, $Cuerpo = $null) {
    try {
        $params = @{ Method = $Metodo; Uri = "$Base$Ruta"; ContentType = "application/json; charset=utf-8" }
        if ($Cuerpo) { $params.Body = [System.Text.Encoding]::UTF8.GetBytes($Cuerpo) }
        $r = Invoke-WebRequest @params -UseBasicParsing
        Write-Host "HTTP $($r.StatusCode)"
        $texto = [System.Text.Encoding]::UTF8.GetString($r.RawContentStream.ToArray())
    } catch {
        $resp = $_.Exception.Response
        if (-not $resp) { throw }
        Write-Host "HTTP $([int]$resp.StatusCode)"
        $texto = (New-Object System.IO.StreamReader($resp.GetResponseStream(), [System.Text.Encoding]::UTF8)).ReadToEnd()
    }
    Write-Host ($texto | ConvertFrom-Json | ConvertTo-Json -Depth 5)
    return ($texto | ConvertFrom-Json)
}

Write-Host "=== Paso 1: registrar una nota válida en el periodo abierto P2 (espera 201) ==="
$null = Llamar POST "/api/notas" '{"estudianteId":"EST-002","materia":"Física","periodoId":"P2","valor":4.2,"profesor":"prof.ramirez"}'

Write-Host "`n=== Paso 2: nota fuera de escala, valor 7 (espera 400) ==="
$null = Llamar POST "/api/notas" '{"estudianteId":"EST-002","materia":"Física","periodoId":"P2","valor":7,"profesor":"prof.ramirez"}'

Write-Host "`n=== Paso 3: nota en el periodo cerrado P1 (espera 400, periodo cerrado) ==="
$null = Llamar POST "/api/notas" '{"estudianteId":"EST-002","materia":"Física","periodoId":"P1","valor":4,"profesor":"prof.ramirez"}'

Write-Host "`n=== Paso 4: solicitud de cambio sobre la nota N1 del periodo cerrado (espera 201, PENDIENTE) ==="
$sol = Llamar POST "/api/notas/N1/solicitudes" '{"valorPropuesto":4.0,"justificacion":"Error de digitación en el acta","profesor":"prof.ramirez"}'

Write-Host "`n=== Paso 5: el administrador aprueba la solicitud $($sol.id) (la consola de la app muestra [NOTIFICACION]) ==="
$null = Llamar POST "/api/solicitudes/$($sol.id)/aprobar" '{"administrador":"admin.lopez"}'
Write-Host "--- Nota N1 después de la aprobación ---"
$null = Llamar GET "/api/notas/N1"

Write-Host "`n=== Paso 6: auditoría de la nota N1 (valorAnterior 2.8, valorNuevo 4.0) ==="
$null = Llamar GET "/api/auditoria?notaId=N1"
