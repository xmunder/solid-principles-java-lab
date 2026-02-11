# SOLID Principles Lab - Java

## Integrantes del Grupo

- **Cristian Camilo Gómez Fernández**

---

## Descripción del Proyecto

Este proyecto implementa los cinco principios SOLID de diseño de software en Java con ejemplos prácticos y pruebas unitarias usando **JUnit 5** y **Maven**.

---

## Principios SOLID Implementados

### 1. **SRP - Single Responsibility Principle**

**Concepto:** Cada clase debe tener una única responsabilidad.

**Implementación:**
```
- Invoice: almacenar datos de facturas
- InvoicePrinter: imprimir facturas
- InvoiceDatabaseSaver: guardar facturas en BD
```

**Patrones de Diseño Relacionados:**
| Patrón | Descripción | Aplicación |
|--------|-------------|-----------|
| **Facade** | Simplifica interfaces complejas | Agrupar Invoice, Printer y Saver bajo una sola interfaz |
| **Decorator** | Añade responsabilidades dinámicamente | Decoradores para formateo adicional de facturas |
| **Strategy** | Diferentes estrategias de impresión | PrinterStrategy con PDF, JSON, XML |

---

### 2. **OCP - Open/Closed Principle**

**Concepto:** Abierto para extensión, cerrado para modificación.

**Implementación:**
```
- DiscountStrategy (interfaz)
- RegularCustomerDiscount (10%)
- VipCustomerDiscount (20%)
- DiscountCalculator (usa cualquier estrategia)
```

**Patrones de Diseño Relacionados:**
| Patrón | Descripción | Aplicación |
|--------|-------------|-----------|
| **Strategy** | Encapsula algoritmos intercambiables | Diferentes estrategias de descuento |
| **Template Method** | Define estructura, deja detalles a subclases | Base para cálculo de descuentos |
| **Abstract Factory** | Crear familias de objetos relacionados | Factory para crear diferentes tipos de descuentos |
| **Decorator** | Añade funcionalidad sin modificar original | Descuentos adicionales (envío, impuestos) |

---

### 3. **LSP - Liskov Substitution Principle**

**Concepto:** Los subtipos deben sustituir a su clase base sin romper el funcionamiento.

**Implementación:**
```
- Drivable (interfaz)
  - Car: conducible y reabastecible
  - ElectricCar: solo conducible (respeta LSP)
```

**Patrones de Diseño Relacionados:**
| Patrón | Descripción | Aplicación |
|--------|-------------|-----------|
| **Adapter** | Adapta una interfaz a otra esperada | Adaptar vehículos antiguos a Drivable |
| **Bridge** | Desacopla abstracción de implementación | Separar conducción de tipo de combustible |
| **Proxy** | Control de acceso a objetos | Validar si un vehículo puede circular |

---

### 4. **ISP - Interface Segregation Principle**

**Concepto:** No obligar a implementar interfaces que no se necesitan.

**Implementación:**
```
- Workable (interfaz)
- Eatable (interfaz)
- Bot: solo implementa Workable
- Developer: implementa Workable y Eatable
```

**Patrones de Diseño Relacionados:**
| Patrón | Descripción | Aplicación |
|--------|-------------|-----------|
| **Role-Based Interface** | Interfaces específicas por rol | Diferentes interfaces para diferentes roles |
| **Adapter** | Adapta interfaz existente a la requerida | Adaptar clases anteriores a nuevas interfaces segregadas |
| **Mixin/Trait** | Combina múltiples interfaces | Comportamientos componibles en clases |

---

### 5. **DIP - Dependency Inversion Principle**

**Concepto:** Depender de abstracciones, no de implementaciones concretas.

**Implementación:**
```
- Database (interfaz)
- MySQLDatabase (implementación concreta)
- OrderProcessor: depende de Database, no de MySQLDatabase
```

**Patrones de Diseño Relacionados:**
| Patrón | Descripción | Aplicación |
|--------|-------------|-----------|
| **Dependency Injection** | Inyectar dependencias externamente | Constructor injection en OrderProcessor |
| **Abstract Factory** | Crear objetos sin especificar clases concretas | Factory para crear diferentes BD |
| **Repository** | Abstrae acceso a datos | Patrón usado en ordenamiento con BD |
| **Service Locator** | Localiza servicios dinámicamente | Registrar diferentes implementaciones de Database |

---

## Tests Unitarios

El proyecto incluye tests completos para cada principio SOLID:

### Ejecutar todos los tests:
```bash
mvn clean test
```

### Ejecutar tests específicos:
```bash
mvn test -Dtest=SRPTest      # Single Responsibility
mvn test -Dtest=OCPTest      # Open/Closed
mvn test -Dtest=LSPTest      # Liskov Substitution
mvn test -Dtest=ISPTest      # Interface Segregation
mvn test -Dtest=DIPTest      # Dependency Inversion
```

---

## Ejecutar la Aplicación

### Ejecutar un principio específico:

1. **Editar el pom.xml** para indicar qué principio ejecutar:

```xml
<configuration>
    <mainClass>com.example.solid.PRINCIPIO.GoodExample</mainClass>
</configuration>
```

Reemplaza `PRINCIPIO` con las siglas en minúsculas del principio que deseas ejecutar:
- `srp` - Single Responsibility Principle
- `ocp` - Open/Closed Principle
- `lsp` - Liskov Substitution Principle
- `isp` - Interface Segregation Principle
- `dip` - Dependency Inversion Principle

**Ejemplo para SRP:**
```xml
<mainClass>com.example.solid.srp.GoodExample</mainClass>
```

2. **Ejecutar el comando:**

```bash
# Compilar
mvn clean compile

# Ejecutar el programa principal
mvn exec:java

# Compilar sin ejecutar tests
mvn clean compile -DskipTests
```
---