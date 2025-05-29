# 🛠️ Guía Maestro del Flujo de Trabajo

Esta página recoge todas las convenciones y pasos de nuestro flujo de trabajo colaborativo en GitHub: nombrado de ramas, commits, gestión de **Issues** y **Kanban**, Pull Requests, y el flujo completo desde la idea hasta el merge.

## 📖 Introducción

En este documento encontrarás nuestro **Git Flow simplificado** (sin `git flow start`/`finish`) y las pautas para que el equipo trabaje de forma ordenada y coherente.

--- 

## 🌿 Nomenclatura de ramas

**Formato general**
```
tipo/short-description
```
- Minúsculas
- `kebab-case` (guiones)
- Descripción breve y clara

| Tipo        | Uso                                                   |
|-------------|-------------------------------------------------------|
| `feature/`  | Nuevas funcionalidades                                |
| `bugfix/`   | Corrección de errores                                 |
| `release/`  | Preparación de versiones estables                     |
| `hotfix/`   | Parches urgentes en `main`                            |
| `refactor/` | Reestructuración de código sin cambiar funcionalidad  |
| `docs/`     | Cambios en documentación                              |
| `chore/`    | Tareas de mantenimiento (dependencias, scripts, etc.) |

**Ejemplos**
```
feature/user-authentication
bugfix/fix-nullpointer-login
release/1.4.0
hotfix/password-reset-urgent
refactor/order-service-cleanup
docs/update-workflow_guide
chore/update-dependencies
```

---

## ✍️ Convenciones de commits

**Formato general**
```
<tipo>: <mensaje corto en presente>
```

| Tipo     | Prefijo     | ¿Cuándo usarlo?                                                           | Ejemplo de mensaje                        |
|----------|-------------|---------------------------------------------------------------------------|-------------------------------------------|
| Feature  | `feat:`     | Para añadir una nueva funcionalidad al proyecto                           | `feat: add user authentication`           |
| Bugfix   | `fix:`      | Para corregir errores o comportamientos inesperados                       | `fix: handle null email in registration`  |
| Refactor | `refactor:` | Para modificar el código sin cambiar su comportamiento (ni funcionalidad) | `refactor: simplify UserService logic`    |
| Docs     | `docs:`     | Para cambios en la documentación (README, Wiki, comentarios...)           | `docs: update API usage in README`        |
| Test     | `test:`     | Para añadir o modificar tests (unitarios, de integración...)              | `test: add unit tests for AuthController` |
| Chore    | `chore:`    | Para tareas de mantenimiento que no afectan al código de producción       | `chore: upgrade spring-boot to 2.7.5`     |

Si necesitas más detalle, añade un cuerpo tras una línea en blanco:
```
feat: add registration endpoint

- Validate input with Bean Validation
- Persist User entity
- TODO: add integration tests
```

---

## 🎯 Gestión de Issues y Kanban

1. **Crear un Issue** en la pestaña **Issues** con:
    - **Título**: Ejemplos: `[Feature] Short description` o `[Bug] Short description`
    - **Descripción**: qué, por qué y criterios de aceptación. Ejemplo:
2. **Añadir Issue a la columna Backlog del Proyecto** (Kanban).
3. **Cuando esté listo para empezar, mover la tarjeta a Ready.**
4. **Etiquetas (labels)** para filtrar: `feature`, `bug`, `in progress`, `review`, `done`, etc.
5. **Asignar** la tarjeta/issue al responsable.


---

## 🔀 Pull Requests

- Crear PR **desde** la rama de trabajo **hacia** `dev`.
- **Título** del PR:
  ```
  feat: add user-authentication (#12)
  ```
- **Descripción**:
    - Breve resumen de cambios.
    - Referencia al Issue: `Closes #12`.
- Asignar revisores y añadir comentarios según convención.

---

## 🚀 Flujo de trabajo completo

1. **Crear Issue**
2. **Mover Issue** a “backlog” en Kanban
3. **Asignarse** la tarjeta
4. **Crear rama** desde `dev`:
   ```bash
   git checkout dev
   git checkout -b feature/short-description
   ```
5. **Desarrollar** y **commitear** según convención.
6. **Push** al remoto:
   ```bash
   git push -u origin feature/short-description
   ```
7. **Crear Pull Request** hacia `dev`.
8. **Revisión** por el equipo.
9. **Merge** manual cuando esté aprobado.
10. **Cerrar Issue** (automático con `Closes #n`).
11. **Eliminar rama** local y remota:
    ```bash
    git branch -d feature/short-description
    git push origin --delete feature/short-description
    ```

---

## ✅ Checklist paso a paso

- [ ] Issue creado y documentado
- [ ] Issue añadido al Kanban
- [ ] Rama creada (`feature/...`)
- [ ] Commits con mensajes según convención
- [ ] Push de la rama al remoto
- [ ] PR abierto con descripción y `Closes #n`
- [ ] Revisores asignados
- [ ] Merge tras aprobación
- [ ] Issue cerrado y rama borrada  

