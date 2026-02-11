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