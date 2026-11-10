# 01 - Drivers priorizados

## 1. Restricciones (se cumplen, no se priorizan)

| Tipo | Restricción | Origen |
|---|---|---|
| Legal | Ley 1581 de 2012 (habeas data). Se manejan datos de menores de edad. | Jurídica |
| Negocio | Equipo de 3 personas, plazo de ~2 semanas. | Curso |
| Técnica | El equipo domina Java / Spring Boot. | Equipo |
| Operativa | Varias instituciones, cada una con su propia escala de notas (min/max). | Negocio |

Por qué la Ley 1581 es restricción y no hoja del árbol: no se negocia ni se mide con un umbral; se cumple o no. Lo que sí es hoja es la parte técnica que ayuda a cumplirla (trazabilidad y confidencialidad), porque ahí hay un valor medible y un costo de diseño.

## 2. Funciones primarias

1. Registrar / modificar una nota.
2. Cierre de periodo: todos los profesores ingresan notas a la vez.

## 3. Árbol de utilidad

```
Utilidad
├── Seguridad
│   ├── Todo cambio de nota queda registrado (quién, cuándo, valor anterior y nuevo)
│   │   y el registro no se puede editar: 100 %                                                  (A, A)
│   ├── Un acudiente solo ve las notas de su hijo; un profesor solo las de sus materias:
│   │   0 accesos cruzados en pruebas                                                (A, M)
│   └── Toda comunicación viaja cifrada (HTTPS)                                            (A, B)
├── Disponibilidad
│   └── El último día de cierre, si falla el envío de notificaciones,
│       el ingreso de notas sigue funcionando                                              (A, A)
├── Rendimiento
│   ├── En el cierre, con 300 profesores concurrentes, guardar una nota responde
│   │   en p95 <= 2 s                                                         (A, A)
│   └── El boletín PDF del periodo se genera en <= 30 s                                                                (B, M)
├── Modificabilidad
│   └── Agregar la escala de notas de una institución nueva sin tocar el código
│       de las demás                                                                       (A, M)
└── Usabilidad
    ├── Un profesor sin capacitación registra notas en <= 3 min                                                          (M, B)
    └── Chat en tiempo real entre acudiente y profesor                                     (B, A) -> negociar para quitarlo
```

### Cómo leer las marcas

Cada hoja lleva `(importancia para el negocio, dificultad para el equipo)`, con A = alta, M = media, B = baja. La primera letra la pone el negocio; la segunda, el equipo.

| Marca | Lectura | Qué se hace |
|---|---|---|
| (A, A) | Importa mucho y es difícil | Decide la arquitectura. Es un driver. |
| (A, B) / (A, M) | Importa pero es manejable | Se programa; no cambia la estructura. |
| (B, A) | Importa poco y es difícil | Se negocia para quitarlo. |
| (M, B), (B, M) | Poco riesgo | Se atiende al final, si hay tiempo. |

El árbol discrimina: solo 3 de 9 hojas son (A, A). El chat en tiempo real es (B, A): costaría WebSockets, estado de sesión y escalado de conexiones, y aporta poco al negocio; por eso queda fuera del esqueleto.

## 4. Los 3 drivers

| # | Hoja (A, A) | Origen: quién lo pide y por qué | Cómo se mide |
|---|---|---|---|
| 1 | Seguridad / trazabilidad | Coordinación académica y rectoría: "¿qué pasa si una nota cambia y nadie sabe quién fue?" | Porcentaje de cambios de nota con registro de auditoría completo (autor, fecha, valor anterior y nuevo): 100 %. Intentos de modificar o borrar un registro: 0 caminos en la API. |
| 2 | Disponibilidad del ingreso de notas independiente de las notificaciones | Secretaría académica: el último día de cierre no se puede parar por un fallo de correo. | Con la notificación forzada a fallar, el ingreso de notas sigue respondiendo con éxito: 100 % de solicitudes exitosas. |
| 3 | Rendimiento en el cierre de periodo | Operación. Pregunta P de FURPS+: ¿cuántos usuarios a la vez y en cuánto tiempo? | p95 de guardar una nota con 300 profesores concurrentes <= 2 s. Se asume una institución con unos 300 profesores que cierran notas el mismo día. |

### Por qué auditoría y confidencialidad son ambas "Seguridad"

Las dos protegen la integridad y el control sobre la nota: la auditoría responde "quién cambió qué" (no repudio, integridad) y la confidencialidad responde "quién puede ver qué". Se separan en hojas porque se miden distinto y solo la auditoría es (A, A): la confidencialidad por rol se resuelve con reglas de acceso al programar (A, M), y está fuera del esqueleto (sin login ni JWT).

### Fuera del esqueleto

Login/OAuth2/JWT (seguridad), foros, chat, horarios, reportes, boletines PDF, carga masiva, multitenancy real, SendGrid y Redis. Existían como stubs en el andamiaje inicial (etiqueta git `andamiaje-original`).
